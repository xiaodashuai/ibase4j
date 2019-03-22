package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizCheckPlan;
import org.ibase4j.model.BizReductionRatioFormula;
import org.ibase4j.service.BizClassifyLevelService;
import org.ibase4j.service.BizReductionRatioFormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 减值比例配置控制类
 * 
 * @author xy
 * @date 2018/05/09
 */

@RestController
@Api(value = "减值比例配置", description = "减值比例配置")
@RequestMapping(value = "/reductionRatioFormula")
public class BizReductionRatioFormulaController extends BaseController {
	@Autowired
	private BizReductionRatioFormulaService bizReductionRatioFormulaService;
	@Autowired
	private BizClassifyLevelService bizClassifyLevelService;
	@ApiOperation(value = "查询减值比例配置")
	@RequiresPermissions("post.reductionRatioFormula.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizReductionRatioFormula) {
		Page<?> list = bizReductionRatioFormulaService.query(bizReductionRatioFormula);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "单条减值比例配置(详情)")
	@RequiresPermissions("post.reductionRatioFormula.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody BizCheckPlan bizReductionRatioFormula) {
		BizReductionRatioFormula record = bizReductionRatioFormulaService.queryById(bizReductionRatioFormula.getId());
		return setSuccessModelMap(modelMap, record);
	}
	
	@ApiOperation(value = "查询风险分类等级下拉框")
	@RequiresPermissions("post.reductionRatioFormula.read")
	@PostMapping(value = "/read/queryClassifyList")
	public Object getClassifyChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> classifyList = bizClassifyLevelService.queryList(params);
		return setSuccessModelMap(modelMap, classifyList);
	}

	@PostMapping
	@ApiOperation(value = "修改简直比例配置")
	@RequiresPermissions("post.reductionRatioFormula.update")
	public Object update(ModelMap modelMap, @RequestBody BizReductionRatioFormula record) {
		bizReductionRatioFormulaService.update(record);
		return setSuccessModelMap(modelMap);
	}


	@ApiOperation(value = "删除减值比例配置")
	/*@RequiresPermissions("sys.base.dept.delete")*/
	@RequiresPermissions("post.reductionRatioFormula.update")
	@PostMapping (value = "/delete/deleteCheckPlan")
	public Object delete(ModelMap modelMap, @RequestBody BizReductionRatioFormula record) {
		//使用逻辑删除,将Enable状态改为0(不可用)
		record.setEnable(0);
		bizReductionRatioFormulaService.update(record);
		return setSuccessModelMap(modelMap);
	}
}
