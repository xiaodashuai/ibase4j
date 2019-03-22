package org.ibase4j.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.ZTreeRadioNode;
import org.ibase4j.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:54
 */
@RestController
@Api(value = "菜单管理", description = "菜单管理")
@RequestMapping(value = "menu")
public class SysMenuController extends BaseController {
	
	@Autowired
	private SysMenuService sysMenuService;

	@ApiOperation(value = "查询菜单")
	@PostMapping(value = "/read/page")
	@RequiresPermissions("sys.base.menu.read")
	public Object getPage(ModelMap modelMap, @RequestBody Map<String, Object> sysMenu) {
		Page<?> list = sysMenuService.queryMenu(sysMenu);
		logger.debug("输出json数据"+JSON.toJSONString(list));
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "查询菜单")
	@PostMapping(value = "/read/list")
	@RequiresPermissions("sys.base.menu.read")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> sysMenu) {
		List<?> list = sysMenuService.queryList(sysMenu);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "显示菜单树")
	@RequiresPermissions("sys.base.menu.read")
	@GetMapping(value = "/read/getAllMenu")
	public Object getForRole(ModelMap modelMap , String roleType) {
		List<ZTreeRadioNode> deptList = sysMenuService.getMenuTree(roleType);
		return setSuccessModelMap(modelMap, deptList);
	}

	@ApiOperation(value = "菜单详情")
	@PostMapping(value = "/read/detail")
	@RequiresPermissions("sys.base.menu.read")
	public Object detail(ModelMap modelMap, @RequestBody SysMenu param) {
		SysMenu record = sysMenuService.queryById(param.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@PostMapping
	@ApiOperation(value = "修改菜单")
	@RequiresPermissions("sys.base.menu.update")
	public Object update(ModelMap modelMap, @RequestBody SysMenu record) {
		sysMenuService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加菜单")
	@RequiresPermissions("sys.base.menu.add")
	@PostMapping(value = "/save")
	public Object add(ModelMap modelMap, @RequestBody SysMenu record) {
		sysMenuService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@DeleteMapping
	@ApiOperation(value = "删除菜单")
	@RequiresPermissions("sys.base.menu.delete")
	public Object delete(ModelMap modelMap, @RequestBody SysMenu record) {
		sysMenuService.delete(record.getId());
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "获取所有权限")
	@RequiresPermissions("sys.base.menu.read")
	@RequestMapping(value = "/read/permission")
	public Object getPermissions(HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, String>> permissions = sysMenuService.getPermissions();
		return setSuccessModelMap(modelMap, permissions);
	}

	// add by zhy 20180207
	@ApiOperation(value = "查询上级菜单信息下拉框")
	@RequiresPermissions("sys.base.menu.read")
	@PostMapping(value = "/read/queryOrgList")
	public Object getOrgChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> orgList = sysMenuService.queryList(params);
		return setSuccessModelMap(modelMap, orgList);
	}

	// 检查菜单名称是否已存在
	@ApiOperation(value = "检查菜单名称是否已存在")
	@RequiresPermissions("sys.base.menu.read")
	@GetMapping(value = "/read/menuName")
	@ResponseBody
	public Object selectDeptName(ModelMap modelMap, String account) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysMenuService.queryByMenuName(account);
		resultMap.put("accounts", b);
		return resultMap;
	}
}
