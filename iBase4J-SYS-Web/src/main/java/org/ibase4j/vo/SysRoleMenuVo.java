package org.ibase4j.vo;

import org.ibase4j.model.SysMenu;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysRoleMenuVo implements Serializable{

	private String roleName;
	private Integer menuType;
	private String menuName;
	private String permission;	
	private Long roleId;
	private Long menuId;			
	private Integer enable;
	private String remark;
	private Long createBy;
	private Date createTime;
	private Long updateBy;
	private Date updateTime;
	private Boolean checkState;
	
	public Boolean getCheckState() {
		return checkState;
	}
	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}
	private List<SysMenu> menuList;
	
	
	public List<SysMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	
}
