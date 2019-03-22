package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizClassifyLevel;
import org.ibase4j.service.BizClassifyLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 分类级别类
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "分类级别", description = "分类级别")
@RequestMapping(value = "classifyLevel")
public class BizClassifyLevelController extends BaseController {

	@Autowired
	private BizClassifyLevelService bizClassifyLevelService;

	@ApiOperation(value = "查询等级")
	@RequiresPermissions("post.level.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizClassifyLevel) {
		Page<?> list = bizClassifyLevelService.query(bizClassifyLevel);
		return setSuccessModelMap(modelMap, list);
	}
	@ApiOperation(value = "等级详情")
	@RequiresPermissions("post.level.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody BizClassifyLevel bizClassifyLevel) {
		BizClassifyLevel record = bizClassifyLevelService.queryById(bizClassifyLevel.getId());
		return setSuccessModelMap(modelMap, record);
	}
	@ApiOperation(value = "查询组织机构下拉框")
	@RequiresPermissions("post.level.read")
	@PostMapping(value = "/read/queryOrgList")
	public Object getOrgChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> orgList = bizClassifyLevelService.queryList(params);
		return setSuccessModelMap(modelMap, orgList);
	}

	@PostMapping
	@ApiOperation(value = "修改等级")
	@RequiresPermissions("post.level.update")
	public Object update(ModelMap modelMap, @RequestBody BizClassifyLevel record) {
		bizClassifyLevelService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加等级")
	@RequiresPermissions("post.level.add")
	@PostMapping(value = "/save")
	public Object add(ModelMap modelMap, @RequestBody BizClassifyLevel record) {
		record.setEnable(1);
		String clCode = String.valueOf(UUID.randomUUID());
		record.setClCode(clCode.substring(0,10));
		bizClassifyLevelService.update(record);
		return setSuccessModelMap(modelMap);
	}





}
