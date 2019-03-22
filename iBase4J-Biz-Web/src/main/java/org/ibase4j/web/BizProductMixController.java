package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizGlobalProductRules;
import org.ibase4j.model.BizSingleProductRule;
import org.ibase4j.service.BizGlobalProductRulesService;
import org.ibase4j.service.BizSingleProductRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 风险分类等级类
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "风险分类等级", description = "风险分类等级")
@RequestMapping(value = "productMix")
public class BizProductMixController extends BaseController {

	@Autowired
	private BizSingleProductRuleService bizSingleProductRuleService;

	@Autowired
	private BizGlobalProductRulesService bizGlobalProductRulesService;

	@PostMapping
	@ApiOperation(value = "修改全局")
	@RequiresPermissions("sys.before.update")
	public Object update(ModelMap modelMap, @RequestBody BizGlobalProductRules record) {
		bizGlobalProductRulesService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加全局")
	@RequiresPermissions("sys.before.add")
	@PostMapping(value = "/save")
	public Object add(ModelMap modelMap, @RequestBody BizGlobalProductRules record) {
		record.setEnable(1);
		bizGlobalProductRulesService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "修改单一")
	@RequiresPermissions("sys.before.update")
	@PostMapping(value = "/singleUpdate")
	public Object update(ModelMap modelMap, @RequestBody BizSingleProductRule record) {
		bizSingleProductRuleService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加单一")
	@RequiresPermissions("sys.before.add")
	@PostMapping(value = "/singleSave")
	public Object add(ModelMap modelMap, @RequestBody BizSingleProductRule record) {
		record.setEnable(1);
		bizSingleProductRuleService.update(record);
		return setSuccessModelMap(modelMap);
	}


}
