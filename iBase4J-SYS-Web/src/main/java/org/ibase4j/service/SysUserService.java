package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.UnauthorizedException;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:21
 */
@Service
public class SysUserService extends BaseService<ISysUserProvider, SysUser> {
	@Reference
	public void setProvider(ISysUserProvider provider) {
		this.provider = provider;
	}


	/** 修改用户信息 */
	public void updateUserInfo(SysUser sysUser)  {

		try{
			provider.updateUserInfo(sysUser);
		}catch (Exception e){
			new Exception("用户信息修改出错了！！！！");
		}

	}

	/** 修改密码 */
	public void updatePassword(Long id, String oldPassword, String password) {
		Assert.notNull(id, "USER_ID");
		Assert.isNotBlank(oldPassword, "OLDPASSWORD");
		Assert.isNotBlank(password, "PASSWORD");
		SysUser sysUser = provider.queryById(id);
		Assert.notNull(sysUser, "USER", id);
        Long userId = SysWebUtil.getCurrentUser();
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
        sysUser.setUpdateBy(SysWebUtil.getCurrentUser());
		provider.update(sysUser);
	}
	
	/**
	 * 功能：分页查询账户信息
	 * @param params
	 * @return
	 */
	public Page<SysUser> getList(Map<String, Object> params) {
		return provider.getList(params);
	}

	public String encryptPassword(String password) {
		return provider.encryptPassword(password);
	}

	/**
	 *  功能：通过员工号查询用户
	 * @param account
	 * @return
	 */
	public Boolean queryByStaffNo(String account) {
		SysUser sysUser = provider.queryByStaffNo(account);
        return sysUser != null;
    }
	
	/**
	 * 功能：查询帐号
	 * 
	 * @param account 账户名
	 * @return
	 */
	public SysUser getByAcct(String account) {
		SysUser sysUser = provider.queryByAccount(account);
		return sysUser;
	}

	/***
	 * 功能：查询帐号是否存在
	 * 
	 * @param acct 账户名
	 * @return 存在返回真，否则返回假
	 */
	public boolean queryByAccount(String acct) {
		SysUser sysUser = provider.queryByAccount(acct);
		return sysUser != null;
	}

	public Page<?> queryAll(Map<String, Object> params) {
		if(!params.get("param").toString().equals("{}")){
			String[] split = params.get("param").toString().split(":");
			char pg = split[1].charAt(0);
			params.put("pageIndex",pg);
		}
		Page<SysUser> query = provider.query(params);
		return query;
	}

	public Page<?> querySomeSys(Map<String, Object> params) {
		if (!"{}".equals(params.get("param").toString())) {
			String pg = params.get("pageNum").toString();
			if (params.get("keyword") != null || !"".equals(params.get("keyword").toString().trim())) {
				params.put("pageIndex", 1);
			} else {
				params.put("pageIndex", pg);
			}
		}
		params.put("userType", "2");
		return provider.query(params);
	}

	public Page<?> querySomeCounter(Map<String, Object> params) {
		if (!"{}".equals(params.get("param").toString())) {
			String pg = params.get("pageNum").toString();
			if (params.get("keyword") != null || !"".equals(params.get("keyword").toString().trim())) {
				params.put("pageIndex", 1);
			} else {
				params.put("pageIndex", pg);
			}
		}
		params.put("userType", "1");
		return provider.query(params);
	}
}
