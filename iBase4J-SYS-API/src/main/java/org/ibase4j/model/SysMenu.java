package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.util.List;

@TableName("sys_menu")
@SuppressWarnings("serial")
public class SysMenu extends BaseModel {
	public SysMenu() {
	}

	public SysMenu(String request, String menuName) {
		this.request = request;
		this.menuName = menuName;
	}

	public Integer getIsShow() {
		return isShow;
	}
	@TableField("menu_name")
	private String menuName;
	@TableField("power_type")
	private String powerType;
	@TableField("menu_type")
	private Integer menuType;
	@TableField("permission_short")
	private String permissionShort;// 权限简称
	@TableField("parent_id")
	private Long parentId;
	@TableField("iconcls_")
	private String iconcls;
	@TableField("request_")
	private String request;
	@TableField("expand_")
	private Integer expand;
	@TableField("sort_no")
	private Integer sortNo;
	@TableField("is_show")
	private Integer isShow;
	@TableField("permission_")
	private String permission;
	@TableField(exist = false)
	private String parentName;
	@TableField("leaf_")
	private Integer leaf;
	@TableField(exist = false)
	private String typeName;
	@TableField(exist = false)
	private String permissionText;
	@TableField(exist = false)
	private List<SysMenu> menuBeans;
	@TableField(exist = false)
	private Boolean checkState;

	public Boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(Boolean checkState) {
		this.checkState = checkState;
	}

	/**
	 * @return the value of sys_menu.menu_name
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName
	 *            the value for sys_menu.menu_name
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	public Integer getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType
	 *            the value for sys_menu.menu_type
	 */
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	/**
	 * @return the value of sys_menu.parent_id
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the value for sys_menu.parent_id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the value of sys_menu.iconcls_
	 */
	public String getIconcls() {
		return iconcls;
	}

	/**
	 * @param iconcls
	 *            the value for sys_menu.iconcls_
	 */
	public void setIconcls(String iconcls) {
		this.iconcls = iconcls == null ? null : iconcls.trim();
	}

	/**
	 * @return the value of sys_menu.request_
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the value for sys_menu.request_
	 */
	public void setRequest(String request) {
		this.request = request == null ? null : request.trim();
	}

	public Integer getExpand() {
		return expand;
	}

	public void setExpand(Integer expand) {
		this.expand = expand;
	}

	/**
	 * @return the value of sys_menu.sort_no
	 */
	public Integer getSortNo() {
		return sortNo;
	}

	/**
	 * @param sortNo
	 *            the value for sys_menu.sort_no
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * @return the value of sys_menu.permission_
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @param permission
	 *            the value for sys_menu.permission_
	 */
	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPermissionText() {
		return permissionText;
	}

	public void setPermissionText(String permissionText) {
		this.permissionText = permissionText;
	}

	public List<SysMenu> getMenuBeans() {
		return menuBeans;
	}

	public void setMenuBeans(List<SysMenu> menuBeans) {
		this.menuBeans = menuBeans;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getPermissionShort() {
		return permissionShort;
	}

	public void setPermissionShort(String permissionShort) {
		this.permissionShort = permissionShort;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
}