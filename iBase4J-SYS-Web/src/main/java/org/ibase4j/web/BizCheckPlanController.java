package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizCheckPlan;
import org.ibase4j.service.BizCheckPlanService;
import org.ibase4j.service.BizClassifyLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 检查计划配置控制类
 * 
 * @author xy
 * @date 2018/05/09
 */

@RestController
@Api(value = "检查计划配置", description = "检查计划配置")
@RequestMapping(value = "/checkPlan")
public class BizCheckPlanController extends BaseController {
	@Autowired
	private BizCheckPlanService bizCheckPlanService;
	@Autowired
	private BizClassifyLevelService bizClassifyLevelService;
	// 新增检查计划配置
		@PostMapping
		@ApiOperation(value = "新增检查计划配置")
		@RequiresPermissions("post.checkplan.add")
		public Object create(ModelMap modelMap, @RequestBody BizCheckPlan bizCheckPlan) {
			bizCheckPlanService.create(bizCheckPlan);
			return setSuccessModelMap(modelMap);
		}
		
		
	@ApiOperation(value = "查询检查计划配置")
	@RequiresPermissions("post.checkplan.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizCheckPlan) {
		Page<?> list = bizCheckPlanService.query(bizCheckPlan);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "单条检查计划配置(详情)")
	@RequiresPermissions("post.checkplan.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody BizCheckPlan bizCheckPlan) {
		BizCheckPlan record = bizCheckPlanService.queryById(bizCheckPlan.getId());
		return setSuccessModelMap(modelMap, record);
	}
	
	@ApiOperation(value = "查询风险分类等级下拉框")
	@RequiresPermissions("post.checkplan.read")
	@PostMapping(value = "/read/queryClassifyList")
	public Object getClassifyChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> classifyList = bizClassifyLevelService.queryList(params);
		return setSuccessModelMap(modelMap, classifyList);
	}

	@PostMapping(value = "/update/updateCheckPlan")
	@ApiOperation(value = "修改检查计划配置")
	@RequiresPermissions("post.checkplan.update")
	public Object update(ModelMap modelMap, @RequestBody BizCheckPlan record) {
		bizCheckPlanService.update(record);
		return setSuccessModelMap(modelMap);
	}


	@ApiOperation(value = "删除检查计划配置")
	/*@RequiresPermissions("sys.base.dept.delete")*/
	@RequiresPermissions("post.checkplan.update")
	@PostMapping (value = "/delete/deleteCheckPlan")
	public Object delete(ModelMap modelMap, @RequestBody BizCheckPlan record) {
		//使用逻辑删除,将Enable状态改为0(不可用)
		record.setEnable(0);
		bizCheckPlanService.update(record);
		return setSuccessModelMap(modelMap);
	}

}
