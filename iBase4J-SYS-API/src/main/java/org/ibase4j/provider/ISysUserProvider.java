package org.ibase4j.provider;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysUser;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * SysUser服务接口
 * 
 * @author ShenHuaJie
 * @version 2016-08-27 22:39:42
 */
public interface ISysUserProvider extends BaseProvider<SysUser> {
	
	/**
	 * 功能：分页查询账户信息
	 * @param 
	 * @return
	 */
	Page<SysUser> getList(Map<String, Object> params);
	/**
	 * 功能：修改用户信息
	 * @param user
	 * @return
	 */
	boolean updateUserInfo(SysUser user) throws NoSuchAlgorithmException;
	/**
	 * 功能：密码加密（md5加密）
	 * @param password
	 * @return
	 */
	String encryptPassword(String password,String username);

	String encryptPassword(String password);

	/** 查询第三方帐号用户Id */
	Long queryUserIdByThirdParty(String openId, String provider);



	/**
	 * 加载所有用户信息
	 */
	void init();

	/**
	 * 功能：启用/禁用 用户
	 * 
	 * @param state 1启用0禁用
	 * @param userId  用户编号
	 * @return
	 */
	boolean updateState(int state, Long userId);
	
	/**
	 * 功能：通过账户查询用户
	 * @param account
	 * @return
	 */
	SysUser queryByAccount(String account);

	/**
	 * 功能：通过员工编号查询用户
	 * @param account
	 * @return
	 */
	SysUser queryByStaffNo(String account);
	/**
	 * @Description: 根据用户id查询用户名和角色名称
	 * @Param: [userId]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getUserNameAndRoleName(Long userId);
	
	/**
	 * 功能：通过登录帐号id查询岗位和部门信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> getRoleAndDepartment(Long userId);
}
