package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizRiskAnalysis;
import org.ibase4j.service.BizRiskAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 风险分类等级类
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "风险分类等级", description = "风险分类等级")
@RequestMapping(value = "riskAnalysis")
public class BizRiskAnalysisController extends BaseController {

	@Autowired
	private BizRiskAnalysisService bizRiskAnalysisService;

	@ApiOperation(value = "查询等级")
	@RequiresPermissions("sys.before.read")
	@PutMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizRiskAnalysis) {
		Page<?> list = bizRiskAnalysisService.query(bizRiskAnalysis);
		return setSuccessModelMap(modelMap, list);
	}
	@ApiOperation(value = "等级详情")
	@RequiresPermissions("sys.before.read")
	@PutMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody BizRiskAnalysis bizRiskAnalysis) {
		BizRiskAnalysis record = bizRiskAnalysisService.queryById(bizRiskAnalysis.getId());
		return setSuccessModelMap(modelMap, record);
	}
	@ApiOperation(value = "查询组织机构下拉框")
	@RequiresPermissions("sys.before.read")
	@PutMapping(value = "/read/queryOrgList")
	public Object getOrgChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> orgList = bizRiskAnalysisService.queryList(params);
		return setSuccessModelMap(modelMap, orgList);
	}

	@PostMapping
	@ApiOperation(value = "修改等级")
	@RequiresPermissions("sys.before.update")
	public Object update(ModelMap modelMap, @RequestBody BizRiskAnalysis record) {
		bizRiskAnalysisService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加等级")
	@RequiresPermissions("sys.before.add")
	@PostMapping(value = "/save")
	public Object add(ModelMap modelMap, @RequestBody BizRiskAnalysis record) {
		record.setEnable(1);
		bizRiskAnalysisService.update(record);
		return setSuccessModelMap(modelMap);
	}




}
