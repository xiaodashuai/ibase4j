package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysDicType;
import org.ibase4j.service.SysDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:34
 */
@RestController
@Api(value = "一级字典管理", description = "一级字典管理")
@RequestMapping(value = "dicType")
public class SysDicTypeController extends BaseController {
	@Autowired
	private SysDicTypeService sysDicTypeService;

	@ApiOperation(value = "查询一级字典项")
	@RequiresPermissions("sys.base.dicType.read")
	@PostMapping(value = "read/list")
	public Object getDic(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		params.put("enable",1);
		Page<SysDicType> query = sysDicTypeService.queryDic(params);
		return setSuccessModelMap(modelMap, query);
	}

	@ApiOperation(value = "一级字典项详情")
	@RequiresPermissions("sys.base.dicType.read")
	@PostMapping(value = "read/detail")
	public Object dicDetail(ModelMap modelMap, @RequestBody(required = false) SysDicType sysDicType) {
		SysDicType record = sysDicTypeService.queryDicTypeById(sysDicType.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "修改一级字典项")
	@RequiresPermissions("sys.base.dicType.update")
	@PostMapping(value = "dicType")
	public Object updateDicType(ModelMap modelMap, @RequestBody(required = false) SysDicType record) {
		if (record.getId() == null) {
			sysDicTypeService.addDicType(record);
		} else {
			sysDicTypeService.updateDicType(record);
		}
		return setSuccessModelMap(modelMap);
	}

	
	@ApiOperation(value = "删除一级字典项")
	@RequiresPermissions("sys.base.dicType.delete")
	@DeleteMapping(value = "dicType")
	public Object deleteDicType(ModelMap modelMap, @RequestBody(required = false) SysDicType record) {
		sysDicTypeService.deleteDicType(record.getId());
		return setSuccessModelMap(modelMap);
	}

	// 检查 字典 码是否已存在
	@ApiOperation(value = "检查 字典 码是否已存在")
	@RequiresPermissions("sys.base.dicType.read")
	@GetMapping(value = "read/code")
	public Object selectDicByCode(ModelMap modelMap, String account) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysDicTypeService.queryByCode(account);
		resultMap.put("accounts", b);
		return resultMap;
	}

	// 检查 字典 名称是否已存在
	@ApiOperation(value = "检查 字典 名称是否已存在")
	@RequiresPermissions("sys.base.dicType.read")
	@GetMapping(value = "read/codeText")
	public Object selectDicByCodeText(ModelMap modelMap, String account) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysDicTypeService.queryByCodeText(account);
		resultMap.put("accounts", b);
		return resultMap;
	}
}
