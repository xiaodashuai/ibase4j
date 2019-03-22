package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.SecurityUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.SysUserMapper;
import org.ibase4j.mapper.SysUserThirdpartyMapper;
import org.ibase4j.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * SysUser服务实现类
 * 
 * @author ShenHuaJie
 * @version 2016-08-27 22:39:42
 */
@CacheConfig(cacheNames = "SysUser")
@Service(interfaceClass = ISysUserProvider.class)
public class SysUserProviderImpl extends BaseProviderImpl<SysUser> implements ISysUserProvider {
	private static final Logger log = LogManager.getLogger(SysUserProviderImpl.class);

	@Autowired
	private SysUserThirdpartyMapper thirdpartyMapper;
	@Autowired
	private ISysDeptProvider sysDeptProvider;
	@Autowired
	private ISysRoleProvider sysRoleProvider;
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public Page<SysUser> getList(Map<String, Object> params) {
        log.debug("开始分页查询用户信息...");
		Page<SysUser> pageInfo = query(params);
		log.debug("查询完成！");
		if (pageInfo != null) {
            log.debug("开始给用户附加部门名称...");
            log.debug("-----调试信息------cur:-" + pageInfo.getCurrent() + "------total:-" + pageInfo.getPages());
			List<SysUser> result = pageInfo.getRecords();
			for (SysUser user : result) {
				String deptCode = user.getDeptCode();
				if (StringUtils.isNotBlank(deptCode)) {
					SysDept sysDept = sysDeptProvider.queryDeptByCode(deptCode);
					if (sysDept != null) {
						user.setDeptName(sysDept.getDeptName());
					}
				}
			}
            log.debug("查询完成！");
		}
		return pageInfo;
	}

	/** 查询第三方帐号用户Id */
	@Override
	@Cacheable
	public Long queryUserIdByThirdParty(String openId, String provider) {
		return thirdpartyMapper.queryUserIdByThirdParty(provider, openId);
	}

	// 加密
	@Override
	public String encryptPassword(String password,String username) {
		log.info("开始md5加密密码...");
		String algorithmName = "md5";
		String salt1 = username+"HelloWorld";

		int hashIterations = 2;

		SimpleHash hash = new SimpleHash(algorithmName, password, salt1 , hashIterations);

//		return SecurityUtil.encryptMd5(SecurityUtil.encryptSHA(password));
		return hash.toHex();
	}

	// 加密
	@Override
	public String encryptPassword(String password) {
		return SecurityUtil.encryptMd5(SecurityUtil.encryptSHA(password));
	}


	@Override
	public boolean updateState(int state, Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("locked", state);
		params.put("id", userId);
		long count = sysUserMapper.updateState(params);
		return count > 0;
	}

	@Override
	@Async
	public void init() {
        List<Long> list = mapper.selectIdPage(Collections.<String, Object>emptyMap());
		for (Long id : list) {
			CacheUtil.getCache().set(getCacheKey(id), mapper.selectById(id));
		}
	}

	@Override
	public boolean updateUserInfo(SysUser sysUser) throws NoSuchAlgorithmException {
		if(sysUser.getPassword() != null || sysUser.getPassword().trim() != ""){
			sysUser.setOpenpwd("1");
		}
		if("0".equals(sysUser.getOpenpwd())){
			
		}
		log.info("开始修改用户信息...");
		// 1.对密码进行md5加密
		Assert.notNull(sysUser, "USER", sysUser.getId());
		if (StringUtils.isNotBlank(sysUser.getPassword()) && "1".equals(sysUser.getOpenpwd())) {

			//加盐加密
			String newSalt = sysUser.getAccount()+sysUser.getPassword();
			String newPasswordEncryption  = new SimpleHash("MD5", sysUser.getPassword(), newSalt, 2).toString();
			sysUser.setPassword(newPasswordEncryption);
		} else if("".equals(sysUser.getPassword())){
			SysUser oneUser = sysUserMapper.selectById(sysUser.getId());
			sysUser.setPassword(oneUser.getPassword());
		}
//		sysUser.setEnable(1);
		// 保存或修改帐户
		SysUser model = update(sysUser);
		log.info("修改成功！");
		// 保存或修改柜员的岗位信息
		String roles = sysUser.getRoles();
		if (StringUtils.isNotBlank(roles)) {
			log.info("开始修改用户与岗位的关联...");
			List<SysUserRole> sysUserRoles = getFromSplitRoleString(roles, model.getId());
			if (sysUserRoles != null && !sysUserRoles.isEmpty()) {
				// 删除旧关系
				sysRoleProvider.deleteRoleByUser(model.getId());
				// 创建新关系
				sysRoleProvider.saveUserRole(sysUserRoles, model.getId());
			}
			log.info("关联修改成功！");
		}
		return true;
	}

	/**
	 * 描述：拆分岗位编号字符串，构造岗位对象
	 * 
	 * @param roles
	 * @return
	 */
	private List<SysUserRole> getFromSplitRoleString(String roles, Long userId) {
		if (StringUtils.isNotBlank(roles)) {
			List<SysUserRole> sysUserRoles = InstanceUtil.newArrayList();
			String[] roleIds = StringUtils.split(roles, ",");
			for (String id : roleIds) {
				if (StringUtils.isNotBlank(id)) {
					SysUserRole uRole = new SysUserRole();
					uRole.setCreateTime(new Date());
					uRole.setEnable(1);
					uRole.setRoleId(StringUtil.stringToLong(id));
					uRole.setUserId(userId);
					uRole.setRemark("从用户id:" + userId + "到岗位id:" + id);
					sysUserRoles.add(uRole);
				}
			}
			return sysUserRoles;
		}
		return null;
	}

	@Override
	public SysUser queryByStaffNo(String account) {
		SysUser sysUser = sysUserMapper.queryByStaffNo(account);
		return sysUser;

	}

	@Override
	public SysUser queryByAccount(String account) {
		return sysUserMapper.queryByAccount(account);
	}

	@Override
	public Map<String, Object> getUserNameAndRoleName(Long userId) {
		Map<String, Object> userNameAndRoleName = new HashMap<>();
		// 根据用户id查询用户信息
		SysUser sysUser = queryById(userId);
		userNameAndRoleName.put("userName",sysUser.getUserName());
		// 根据用户id查询角色信息
		List<SysUserRole> roleList = sysRoleProvider.getUserRoleByUserId(userId);
		// 目前不考虑一人多岗情况，取第一个
		if(roleList != null && roleList.size() > 0){
			SysUserRole sysUserRole = roleList.get(0);
			userNameAndRoleName.put("roleId",sysUserRole.getRoleId());
			SysRole sysRole = sysRoleProvider.queryById(sysUserRole.getRoleId());
			userNameAndRoleName.put("roleName",sysRole.getRoleName());
		}else{
			userNameAndRoleName.put("roleId","");
			userNameAndRoleName.put("roleName","");
		}
		return userNameAndRoleName;
	}

	@Override
	public Map<String, Object> getRoleAndDepartment(Long userId) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 根据用户id查询用户信息
		SysUser sysUser = queryById(userId);
		if(sysUser!=null){
			String deptCode = sysUser.getDeptCode();
			// 根据用户id查询角色信息
			List<SysUserRole> roleList = sysRoleProvider.getUserRoleByUserId(userId);
			// 根据编号查询部门
			SysDept dept = sysDeptProvider.queryDeptByCode(deptCode);
			//设置值
			model.put("user",sysUser);
			model.put("userName",sysUser.getUserName());
			if(dept!=null){
				model.put("dept",dept);
				model.put("deptCode", deptCode);
				model.put("deptName", dept.getDeptName());
			}
			//解析用户包含的多个岗位
			if(CollectionUtils.isNotEmpty(roleList)){
				model.put("roles",roleList);
				Long roleId = 0L;
				String roleName = "";
				for(int i=0;i< roleList.size();i++){
					SysUserRole userRole = roleList.get(i);
					roleId = userRole.getRoleId();
					SysRole role = sysRoleProvider.queryById(roleId);
					roleName = role.getRoleName();
				}
				model.put("roleId",roleId);
				model.put("roleName",roleName);
			}
			model.put("userId", userId);
		}
		
		return model;
	}

}
