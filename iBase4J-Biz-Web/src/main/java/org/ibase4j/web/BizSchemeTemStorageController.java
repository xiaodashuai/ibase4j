package org.ibase4j.web;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.service.BizDebtSummaryService;
import org.ibase4j.service.BizSchemeTemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 暂存管理，负责临时保存、清除、完善数据 日期：2018/09/09
 * 
 * @author 肖水泉
 * @version 1.0
 */
@RestController
@Api(value = "方案暂存管理", description = "方案暂存管理")
@RequestMapping(value = "temStorage")
public class BizSchemeTemStorageController extends BaseController {
	@Autowired
	private BizSchemeTemStorageService bizSchemeTemStorageService;
	@Autowired
	private BizDebtSummaryService bizDebtSummaryService;
	
	
	@ApiOperation(value = "方案暂存")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/saveScheme/list")
	public Object tempStorage(ModelMap modelMap, @RequestBody Map<String, Object> record) {
		boolean success = bizSchemeTemStorageService.saveTemp(record);
		return setSuccessModelMap(modelMap);
	}
	
	//@ApiOperation(value = "方案约束检查")
	//@RequiresPermissions("before.base.reponse.read")
	//@PostMapping(value = "/check")
	/*public Object check(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long pid = StringUtil.objectToLong(params.get("properInfo"));
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		GrantRuleVerifVo ds = bizSchemeTemStorageService.getGrantRuleVo(pid, debtCode);

		return setSuccessModelMap(modelMap, ds);
	}*/

	@ApiOperation(value = "查询暂存列表")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/queryTemp")
	public Object queryTemp(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = bizSchemeTemStorageService.getTempResult(params);
		return setSuccessModelMap(modelMap,list);
	}
	
	@ApiOperation(value = "继续填写暂存内容")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/editTemp")
	public Object editTemp(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long id = StringUtil.objectToLong(params.get("id"));
		Object list = bizSchemeTemStorageService.getTempFile(id);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "删除暂存内容")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/delTemp")
	public Object delTemp(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		boolean success = bizSchemeTemStorageService.delTempDataAndFile(params);
		return setSuccessModelMap(modelMap, success);
	}

}
