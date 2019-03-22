package org.ibase4j.service;

import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.exception.IllegalParameterException;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserRole;
import org.ibase4j.provider.ISysAuthorizeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:00
 */
@Service
public class SysAuthorizeService extends BaseService<ISysAuthorizeProvider, SysMenu> {
	@Autowired
	private ISysAuthorizeProvider authorizeProvider;

	public List<SysMenu> queryAuthorizeByUserId(Long id, String powerType) {
		return authorizeProvider.queryAuthorizeByUserId(id, powerType);
	}

	public List<String> queryMenuIdsByRoleId(Long roleId) {
		return authorizeProvider.queryMenuIdsByRoleId(roleId);
	}

	public List<SysUserRole> getRolesByUserId(Long userId) {
		return authorizeProvider.getRolesByUserId(userId);
	}

	/**
	 * 功能: 修改用户与岗位关联
	 * 
	 * @param sysUserRoles
	 */
	public void updateUserRole(List<SysUserRole> sysUserRoles) {
		Long userId = null;
        Long currentUserId = SysWebUtil.getCurrentUser();
		for (SysUserRole sysUserRole : sysUserRoles) {
			if (sysUserRole.getUserId() != null) {
				if (userId != null && userId != sysUserRole.getUserId()) {
					throw new IllegalParameterException("参数错误.");
				}
				userId = sysUserRole.getUserId();
			}
			sysUserRole.setCreateBy(currentUserId);
			sysUserRole.setUpdateBy(currentUserId);
		}
		authorizeProvider.updateUserRole(sysUserRoles);
	}

	public void updateRoleMenu(List<SysRoleMenu> sysRoleMenus) {
		Long roleId = null;
        Long userId = SysWebUtil.getCurrentUser();
		for (SysRoleMenu sysRoleMenu : sysRoleMenus) {
			if (sysRoleMenu.getRoleId() != null) {
				if (roleId != null && roleId != sysRoleMenu.getRoleId()) {
					throw new IllegalParameterException("参数错误.");
				}
				roleId = sysRoleMenu.getRoleId();
			}
			sysRoleMenu.setCreateBy(userId);
			sysRoleMenu.setUpdateBy(userId);
		}
		authorizeProvider.updateRoleMenu(sysRoleMenus);
	}

	public List<SysMenu> queryMenusPermission() {
		return authorizeProvider.queryMenusPermission();
	}

	public void updateRolePermission(List<SysRoleMenu> sysRoleMenus) {
		authorizeProvider.updateRolePermission(sysRoleMenus);
	}

	/** 获取用户权限 */
	public List<String> queryPermissionByUserId(Long userId) {
		return authorizeProvider.queryPermissionByUserId(userId);
	}

	public List<Map<String, Object>> queryRolePermissions(SysRoleMenu record) {
		return authorizeProvider.queryRolePermissions(record);
	}

	/** 获取角色id **/
	public List<String> queryRoleIdsByUserId(Long userId) {
		return authorizeProvider.queryRoleIdsByUserId(userId);
	}

	/** 获取上级部门id **/
	public List<String> queryDeptParentIdByDeptCode(String deptCode) {
		return authorizeProvider.queryParentDeptIdByDeptCode(deptCode);
	}

	/**
	 * 获取工作流用户的所有角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getMFRole(Long userId) {
		List<String> roleIds = queryRoleIdsByUserId(userId);
		return roleIds;
	}

}
