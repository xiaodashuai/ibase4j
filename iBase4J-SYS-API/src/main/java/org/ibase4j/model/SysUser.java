
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-02-15
 */
@TableName("sys_user")
@SuppressWarnings("serial")
public class SysUser extends BaseModel {

	/**
	 * 登陆帐户
	 */
	@TableField("account_")
	private String account;
	/**
	 * 密码
	 */
	@TableField("password_")
	private String password;
	/**
	 * 用户类型(1普通用户2管理员3系统用户)
	 */
	@TableField("user_type")
	private String userType;
	/**
	 * 姓名
	 */
	@TableField("user_name")
	private String userName;
	/**
	 * 性别(0:未知;1:男;2:女)
	 */
	@TableField("sex_")
	private Integer sex;
	/**
	 * 头像
	 */
	@TableField("avatar_")
	private String avatar;
	/**
	 * 电话
	 */
	@TableField("phone_")
	private String phone;
	/**
	 * 邮箱
	 */
	@TableField("email_")
	private String email;
	/**
	 * 证件类型
	 */
	@TableField("ID_TYPE")
	private String idType;
	/**
	 * 身份证号码
	 */
	@TableField("id_card")
	private String idCard;
	/**
	 * 部门编码
	 */
	@TableField("DEPT_CODE")
	private String deptCode;
	/**
	 * 组织机构
	 */
	@TableField("ORG_CODE")
	private String orgCode;
	/**
	 * 详细地址
	 */
	@TableField("address_")
	private String address;
	/**
	 * 工号
	 */
	@TableField("staff_no")
	private String staffNo;

	/**
	 * 是否修改密码
	 */
	@TableField(exist = false)
	private String openpwd;
	

	/**
	 * 身份证号码
	 */
	@TableField("BIRTH_DAY")
	private String birthDay;
	/**
	 * 是否接收到授权工作（1是0否)
	 */
	@TableField("CHANGE_WORK")
	private Integer changeWork;

	@TableField(exist = false)
	private String oldPassword;

	@TableField(exist = false)
	private String deptName;

	@TableField(exist = false)
	private String userTypeText;

	@TableField(exist = false)
	private String permission;
	// 非数据库字段,岗位编码字符串
	@TableField(exist = false)
	private String roles;

	@TableField(exist = false)
	private String salt = "HelloWorld"; //加密密码的盐


	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCredentialsSalt() {
		return account + salt;
	}
	
	
	
	public String getOpenpwd() {
		return openpwd;
	}

	public void setOpenpwd(String openpwd) {
		this.openpwd = openpwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public Integer getChangeWork() {
		return changeWork;
	}

	public void setChangeWork(Integer changeWork) {
		this.changeWork = changeWork;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserTypeText() {
		return userTypeText;
	}

	public void setUserTypeText(String userTypeText) {
		this.userTypeText = userTypeText;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}