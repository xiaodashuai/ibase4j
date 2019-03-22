package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.ZTreeRadioNode;
import org.ibase4j.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "部门管理", description = "部门管理")
@RequestMapping(value = "dept")
public class SysDeptController extends BaseController {
	@Autowired
	private SysDeptService sysDeptService;
	
	@ApiOperation(value = "查询部门")
	@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> sysDept) {
		Page<SysDept> page = sysDeptService.findList(sysDept);
		return setSuccessModelMap(modelMap, page);
	}

	// 检查部门名称是否已存在
	@ApiOperation(value = "检查部门名称是否已存在")
	@RequiresPermissions("sys.base.dept.read")
	@GetMapping(value = "/read/deptName")
	@ResponseBody
	public Object selectDeptName(ModelMap modelMap, String account) {
		logger.debug(account);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean b = sysDeptService.queryByDeptName(account);
		resultMap.put("accounts", b);
		return resultMap;
	}
	// 检查机构代码是否已存在
	@ApiOperation(value = "检查机构代码是否已存在")
	@RequiresPermissions("sys.base.dept.read")
	@GetMapping(value = "/read/code")
	@ResponseBody
	public Object selectByCode(ModelMap modelMap, String account) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysDeptService.queryByCode(account);
		resultMap.put("accounts", b);
		return resultMap;
	}

	@ApiOperation(value = "部门详情")
	@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody SysDept sysDept) {
		SysDept record = sysDeptService.queryById(sysDept.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "查询组织机构下拉框")
	@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/queryOrgList")
	public Object getOrgChooses(ModelMap modelMap) {
		List<?> orgList = sysDeptService.queryList(new HashMap<String , Object>());
		return setSuccessModelMap(modelMap, orgList);
	}

	@PostMapping
	@ApiOperation(value = "修改部门")
	@RequiresPermissions("sys.base.dept.update")
	public Object update(ModelMap modelMap, @RequestBody SysDept record) {
		sysDeptService.updateOne(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "删除部门")
	/* @RequiresPermissions("sys.base.dept.delete") */
	@RequiresPermissions("sys.base.dept.update")
	@PostMapping(value = "/delete/deleteDept")
	public Object delete(ModelMap modelMap, @RequestBody SysDept record) {
		sysDeptService.delete(record.getId());
		return setSuccessModelMap(modelMap);
	}

	// add by zhy 20180207 启用禁用功能
	@ApiOperation(value = "修改部门状态")
	@RequiresPermissions("sys.base.dept.update")
	@PostMapping(value = "/update/updateEnable")
	public Object updateEnable(ModelMap modelMap, @RequestBody SysDept record) {
		sysDeptService.update(record);
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "查询所有有效部门")
	@RequiresPermissions("sys.base.dept.read")
	@PostMapping(value = "/read/getAllDept")
	public Object getDeptForUser(ModelMap modelMap, @RequestParam(value = "detpCode", required = false) String detpCode) {
		List<ZTreeRadioNode> deptList = sysDeptService.getAllDept(detpCode);
		return setSuccessModelMap(modelMap, deptList);
	}

}
