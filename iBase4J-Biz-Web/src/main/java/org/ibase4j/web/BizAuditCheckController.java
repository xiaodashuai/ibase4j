package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizAuditCheck;
import org.ibase4j.model.BizClassifyLevel;
import org.ibase4j.service.BizAuditCheckService;
import org.ibase4j.service.BizClassifyLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 分类级别类
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "选项名称", description = "选项名称")
@RequestMapping(value = "auditCheck")
public class BizAuditCheckController extends BaseController {

	@Autowired
	private BizAuditCheckService bizAuditCheckService;

	@ApiOperation(value = "查询选项")
	@RequiresPermissions("post.level.read")
	@PutMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizAuditCheck) {
		Page<?> list = bizAuditCheckService.query(bizAuditCheck);
		return setSuccessModelMap(modelMap, list);
	}
	@ApiOperation(value = "选项详情")
	@RequiresPermissions("post.level.read")
	@PutMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody BizAuditCheck bizAuditCheck) {
		BizAuditCheck record = bizAuditCheckService.queryById(bizAuditCheck.getId());
		return setSuccessModelMap(modelMap, record);
	}
	@ApiOperation(value = "查询组织机构下拉框")
	@RequiresPermissions("post.level.read")
	@PutMapping(value = "/read/queryOrgList")
	public Object getOrgChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> orgList = bizAuditCheckService.queryList(params);
		return setSuccessModelMap(modelMap, orgList);
	}

	@PostMapping
	@ApiOperation(value = "修改选项")
	@RequiresPermissions("post.level.update")
	public Object update(ModelMap modelMap, @RequestBody BizAuditCheck record) {
		bizAuditCheckService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加选项")
	@RequiresPermissions("post.level.add")
	@PostMapping(value = "/save")
	public Object add(ModelMap modelMap, @RequestBody BizAuditCheck record) {
		record.setEnable(1);
		bizAuditCheckService.update(record);
		return setSuccessModelMap(modelMap);
	}





}
