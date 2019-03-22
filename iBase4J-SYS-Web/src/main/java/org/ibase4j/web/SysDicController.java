package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysDic;
import org.ibase4j.service.SysDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 字典管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:34
 */
@RestController
@Api(value = "字典管理", description = "字典管理")
@RequestMapping(value = "dic")
public class SysDicController extends BaseController {
	@Autowired
	private SysDicService sysDicService;

	@ApiOperation(value = "查询字典项")
	@RequiresPermissions("sys.base.dic.read")
	@PostMapping(value = "read/list")
	public Object getDic(ModelMap modelMap, @RequestBody(required = false) String code) {
		List<SysDic> list = sysDicService.queryDicByCode(code);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation("根据类型查询字典")
	@RequiresPermissions("sys.base.dic.read")
	@PostMapping(value = "read/query")
	public Object getDicByType(ModelMap modelMap, @RequestBody(required = false) SysDic sysDic) {
		List<SysDic> list = sysDicService.queryDicByType(sysDic.getType());
		return this.setSuccessModelMap(modelMap, list);
	}

	@ApiOperation("根据类型查询字典")
	@RequiresPermissions("sys.base.dic.read")
	@PostMapping(value = "read/query/type")
	public Object getDicByType(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		 params.put("enable",1);
		Page<SysDic> query = sysDicService.queryDic(params);
		return this.setSuccessModelMap(modelMap, query);
	}

	@ApiOperation(value = "字典项详情")
	@RequiresPermissions("sys.base.dic.read")
	@PostMapping(value = "read/detail")
	public Object dicDetail(ModelMap modelMap, @RequestBody(required = false) SysDic sysDic) {
		SysDic record = sysDicService.queryDicById(sysDic.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@PostMapping(value = "dic")
	@ApiOperation(value = "修改字典项")
	@RequiresPermissions("sys.base.dic.update")
	public Object updateDic(ModelMap modelMap, @RequestBody(required = false) SysDic record) {
		if (record.getId() == null) {
			sysDicService.addDic(record);
		} else {
			sysDicService.updateDic(record);
		}
		return setSuccessModelMap(modelMap);
	}

	@DeleteMapping(value = "dic")
	@ApiOperation(value = "删除字典项")
	@RequiresPermissions("sys.base.dic.delete")
	public Object deleteDic(ModelMap modelMap, @RequestBody(required = false) SysDic record) {
		sysDicService.deleteDic(record.getId());
		return setSuccessModelMap(modelMap);
	}

	// 检查 字典 名称是否已存在
	@ApiOperation(value = "检查 字典 名称是否已存在")
	@RequiresPermissions("sys.base.dic.read")
	@GetMapping(value = "read/codeText")
	public Object selectDicByCodeText(ModelMap modelMap, String account) {
		Boolean b = sysDicService.queryByCodeText(account);
		return setSuccessModelMap(modelMap, b);
	}

	// 检查 字典 码是否已存在
	@ApiOperation(value = "检查 字典 码是否已存在")
	@RequiresPermissions("sys.base.dic.read")
	@GetMapping(value = "read/code")
	public Object selectDicByCode(ModelMap modelMap, String account) {
		Boolean b = sysDicService.queryByCode(account);
		return setSuccessModelMap(modelMap, b);
	}
}
