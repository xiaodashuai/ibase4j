package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysRole;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserRole;
import org.ibase4j.service.SysRoleService;
import org.ibase4j.vo.SysRoleCheckVo;
import org.ibase4j.vo.SysRoleMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:15:43
 */
@RestController
@Api(value = "角色管理", description = "角色管理")
@RequestMapping(value = "role")
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService sysRoleService;

	@ApiOperation(value = "查询角色")
	@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> sysRole) {
		Page<?> list = sysRoleService.getList(sysRole);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "角色详情")
	@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody SysRole param) {
		SysRole record = sysRoleService.queryById(param.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "修改角色")
	@RequiresPermissions("sys.base.role.update")
	@RequestMapping(method = RequestMethod.POST)
	public Object update(ModelMap modelMap, @RequestBody SysRole record) {
		sysRoleService.updateModel(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "删除角色")
	@RequiresPermissions("sys.base.role.delete")
	@RequestMapping(method = RequestMethod.DELETE)
	public Object delete(ModelMap modelMap, @RequestBody SysRole record) {
		sysRoleService.delete(record.getId());
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "启用/禁用角色")
	@RequiresPermissions("sys.base.role.setMenu")
	@PostMapping(value = "/update/changeRole")
	public Object changeRole(ModelMap modelMap, @RequestBody SysRole record) {
		sysRoleService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "查询可绑定的角色")
	@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/listUnBind")
	public Object queryUnBind(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<SysRoleCheckVo> list = sysRoleService.getAllRole(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "查询已经绑定的角色")
	@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/getBinded")
	public Object queryBinded(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long userId = Long.valueOf(params.get("userId").toString());
		List<SysUserRole> list = sysRoleService.getUserRoleByUserId(userId);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "岗位绑定的菜单")
	@RequiresPermissions("sys.base.role.read")
	@RequestMapping(value="/read/getSelectedMenu",method=RequestMethod.POST)
	public Object getSelectedMenu(ModelMap modelMap, @RequestBody SysRoleMenu roleMenu) {
		List<SysRoleMenu> idList = sysRoleService.getSelectedMenuIds(roleMenu.getRoleId());
		return setSuccessModelMap(modelMap, idList);
	}

	@ApiOperation(value = "查询角色菜单")
	@RequiresPermissions("sys.base.role.read")
	@PostMapping(value = "/read/roleMenuPage")
	public Object roleMenuPage(ModelMap modelMap, @RequestBody SysRole sysRole) {
		Long id = sysRole.getId();
		List<SysRoleMenuVo> list = sysRoleService.findMenuAll(id);
		return setSuccessModelMap(modelMap, list);
	}

	// 检查岗位是否重复
	@ApiOperation(value = "检查岗位是否重复")
	@RequiresPermissions("sys.base.role.read")
	@GetMapping(value = "/read/roleName")
	@ResponseBody
	public Object roleName(ModelMap modelMap, String roleName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysRoleService.queryByAccount(roleName);
		resultMap.put("accounts", b);
		return resultMap;
	}
	// 检查岗位编码是否重复
	@ApiOperation(value = "检查岗位编码是否重复")
	@RequiresPermissions("sys.base.role.read")
	@GetMapping(value = "/read/code")
	@ResponseBody
	public Object code(ModelMap modelMap,String code) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysRoleService.queryByCode(code);
		resultMap.put("accounts", b);
		return resultMap;
	}
}
