package org.ibase4j.vo;

/**
 * 功能：判断角色是否被选中
 * 
 * @author czm
 * @version 1.0
 */
public class SysRoleCheckVo implements java.io.Serializable{
	private static final long serialVersionUID = 1406275095023736604L;
	
	private Long roleId;
	private String roleName;
	private Integer roleType;
	private String permission;
	
	private boolean checked;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
