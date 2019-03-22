package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserRole;
import org.ibase4j.service.SysAuthorizeService;
import org.ibase4j.service.SysCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:05
 */
@RestController
@Api(value = "权限管理", description = "权限管理")
public class SysAuthorizeController extends BaseController {
	@Autowired
	private SysAuthorizeService authorizeService;
	@Autowired
	private SysCacheService sysCacheService;

	@ApiOperation(value = "获取用户角色")
	@PostMapping(value = "user/read/role")
	@RequiresPermissions("sys.permisson.userRole.read")
	public Object getUserRole(ModelMap modelMap, @RequestBody SysUserRole record) {
		List<SysUserRole> menus = authorizeService.getRolesByUserId(record.getUserId());
		return setSuccessModelMap(modelMap, menus);
	}

	@ApiOperation(value = "修改用户角色")
	@PostMapping(value = "/user/update/role")
	@RequiresPermissions("sys.permisson.userRole.update")
	public Object userRole(ModelMap modelMap, @RequestBody List<SysUserRole> list) {
		authorizeService.updateUserRole(list);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "获取角色菜单编号")
	@PostMapping(value = "role/read/menu")
	@RequiresPermissions("sys.permisson.roleMenu.read")
	public Object getRoleMenu(ModelMap modelMap, @RequestBody SysRoleMenu record) {
		List<String> menus = authorizeService.queryMenuIdsByRoleId(record.getRoleId());
		return setSuccessModelMap(modelMap, menus);
	}

	@ApiOperation(value = "修改角色菜单")
	@PostMapping(value = "/role/update/menu")
	@RequiresPermissions("sys.permisson.roleMenu.update")
	public Object roleMenu(ModelMap modelMap, @RequestBody List<SysRoleMenu> list) {
		authorizeService.updateRoleMenu(list);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "获取角色操作权限")
	@PostMapping(value = "role/read/permission")
	@RequiresPermissions("sys.permisson.role.read")
	public Object queryRolePermissions(ModelMap modelMap, @RequestBody SysRoleMenu record) {
		List<?> menuIds = authorizeService.queryRolePermissions(record);
		return setSuccessModelMap(modelMap, menuIds);
	}

	@ApiOperation(value = "修改角色操作权限")
	@PostMapping(value = "/role/update/permission")
	@RequiresPermissions("sys.permisson.role.update")
	public Object updateRolePermission(ModelMap modelMap, @RequestBody List<SysRoleMenu> list) {
		authorizeService.updateRolePermission(list);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "清理缓存")
	@RequiresPermissions("sys.cache.update")
	@RequestMapping(value = "/cache/update", method = RequestMethod.POST)
	public Object flush(HttpServletRequest request, ModelMap modelMap) {
		sysCacheService.flushCache();
		return setSuccessModelMap(modelMap);
	}
}
