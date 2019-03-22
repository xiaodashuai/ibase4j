package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserRole;

import java.util.List;
import java.util.Map;

public interface ISysAuthorizeProvider extends BaseProvider<SysMenu> {

	public List<SysUserRole> getRolesByUserId(Long userId);

	public void updateUserRole(List<SysUserRole> sysUserRoles);

	public List<String> queryMenuIdsByRoleId(Long roleId);

	public void updateRoleMenu(List<SysRoleMenu> sysRoleMenus);

	public void updateRolePermission(List<SysRoleMenu> sysRoleMenus);

	public List<SysMenu> queryAuthorizeByUserId(Long userId , String powerType);

	public List<SysMenu> queryMenusPermission();

	public List<String> queryPermissionByUserId(Long userId);

	public List<Map<String, Object>> queryRolePermissions(SysRoleMenu record);
	/** 用户的所有角色id **/
	public List<String> queryRoleIdsByUserId(Long userId);
	
	/** 用户的所有角色编号 **/
	public List<String> queryRoleCodeByUserId(Long userId);
	
	/** 获取部门上级所有的节点 **/
	public List<String> queryParentDeptIdByDeptCode(String deptCode);
	
}
