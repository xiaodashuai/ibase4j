package org.ibase4j.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysDeptProvider;
import org.ibase4j.provider.ISysUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:21
 */
@Service
public class BizUserService extends BaseService<ISysUserProvider, SysUser> {
	@Autowired
	private void setProvider(ISysUserProvider provider) {
		this.provider = provider;
	}
	@Autowired
	private ISysDeptProvider sysDeptProvider;

	/**
	 * 功能：根据部门编码查名称
	 * @param deptCode
	 * @return
	 */
	public SysDept getDepartmentByCode(String deptCode){
		SysDept dept = sysDeptProvider.queryDeptByCode(deptCode);
		return dept;
	}

	/**
	 * 功能: 根据用户帐号查询用户
	 *
	 * @param acct
	 * @return
	 */
	public SysUser getByAcct(String acct) {
		return provider.queryByAccount(acct);
	}

	/** 修改用户信息 */
	public void updateUserInfo(SysUser sysUser) {
		if (sysUser.getId() != null) {
			SysUser user = this.queryById(sysUser.getId());
			Assert.notNull(user, "USER", sysUser.getId());
			if (StringUtils.isNotBlank(sysUser.getPassword())) {
				sysUser.setPassword(encryptPassword(sysUser.getPassword()));
			}
			sysUser.setEnable(1);
		}
		update(sysUser);
	}

	/** 修改密码 */
	public void updatePassword(Long id, String oldPassword, String password) {
		Assert.notNull(id, "USER_ID");
		Assert.isNotBlank(oldPassword, "OLDPASSWORD");
		Assert.isNotBlank(password, "PASSWORD");
		SysUser sysUser = provider.queryById(id);
		Assert.notNull(sysUser, "USER", id);
		Long userId = BizWebUtil.getCurrentUser();
		if (!id.equals(userId)) {
			SysUser user = provider.queryById(userId);
			if ("1".equals(user.getUserType())) {
				throw new UnauthorizedException("您没有权限修改用户密码.");
			}
		} else {
			if (!sysUser.getPassword().equals(encryptPassword(oldPassword))) {
				throw new UnauthorizedException("原密码错误.");
			}
		}
		sysUser.setPassword(encryptPassword(password));
		sysUser.setUpdateBy(BizWebUtil.getCurrentUser());
		provider.update(sysUser);
	}
	/** 修改密码 */
	//返回值1为修改成功 , 2为账户密码不匹配  , 3为服务器异常
	public String updatePassword(String account, String oldPassword, String password) {
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("account", account);
        //对旧密码加密 盐值是 账号和 旧密码
		String oldSalt = account+oldPassword;
		String oldPasswordEncryption = new SimpleHash("MD5", oldPassword, oldSalt, 2).toString();
		params.put("password", oldPasswordEncryption);
		//根据账号和旧密码查出用户
		Page<SysUser> query = provider.query(params);
		List<SysUser> records = query.getRecords();
		if(records != null && records.size() == 1){
			if(!oldPasswordEncryption.equals(records.get(0).getPassword())){
				return "2";
			}
			SysUser sysUser = records.get(0);
			//对新密码加密 盐值是 账号和 新密码
			String newSalt = account+password;
            String newPasswordEncryption  = new SimpleHash("MD5", password, newSalt, 2).toString();
//			logger.debug("修改后的密码:"+newPasswordEncryption);
            sysUser.setPassword(newPasswordEncryption);
			sysUser.setUpdateTime(new Date());
			sysUser.setUpdateBy(sysUser.getId());
			try {
				provider.update(sysUser);
			} catch (Exception e) {
				return "3";
			}
			return "1";
		}else{
			return "2";
		}
	}
	public String encryptPassword(String password) {
		return provider.encryptPassword(password);
	}

	public String encryptPassword(String password,String username ) {
		return provider.encryptPassword(password,username);
	}


	// 查询信贷员
	public SysUser getUserCredit(HttpServletRequest request) {
		SysUser user = (SysUser) request.getSession().getAttribute("USER");
		return user;
	}

	/**
	 * @Description: 根据用户id查询用户名称和角色名称
	 * @Param: [userId]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getUserNameAndRoleName(Long userId) {
		return provider.getUserNameAndRoleName(userId);
	}
}
