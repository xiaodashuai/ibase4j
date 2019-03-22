package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

@TableName("sys_role")
@SuppressWarnings("serial")
public class SysRole extends BaseModel {
	@TableField("ROLE_NAME")
	private String roleName;
	/**
	 * 所属部门编码
	 */
	@TableField("DEPT_CODE")
	private String deptCode;
	
	@TableField("ROLE_TYPE")
	private Integer roleType;
	
	/**
	 * 编码
	 */
	@TableField("CODE_")
	private String code;

	@TableField(exist = false)
	private String deptName;
	@TableField(exist = false)
	private String permission;
	@TableField(exist = false) // 权限菜单
	private String menus;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((roleType == null) ? 0 : roleType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		SysRole other = (SysRole) obj;
		if (deptCode == null) {
			if (other.deptCode != null){
				return false;
			}
		} else if (!deptCode.equals(other.deptCode)){
			return false;
		}
		if (roleName == null) {
			if (other.roleName != null){
				return false;
			}
		} else if (!roleName.equals(other.roleName)){
			return false;
		}
		if (code == null) {
			if (other.code != null){
				return false;
			}
		} else if (!code.equals(other.code)){
			return false;
		}
		if (roleType == null) {
			if (other.roleType != null){
				return false;
			}
		} else if (!roleType.equals(other.roleType)){
			return false;
		}
		return true;
	}

}