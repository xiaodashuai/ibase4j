package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.BizCategoryAuditService;
import org.ibase4j.vo.MiddlePageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 分类级别类
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "产品选项关系", description = "产品选项关系")
@RequestMapping(value = "categoryAudit")
public class BizCategoryAuditController extends BaseController {

	@Autowired
	private BizCategoryAuditService bizCategoryAuditService;

	@ApiOperation(value = "查询产品选项关系")
	@RequiresPermissions("biz.workflow.read")
	@PutMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizCategoryAudit) {

        List<MiddlePageVo> voList = bizCategoryAuditService.getMiddlePage(Long.valueOf(7),Long.valueOf(2));
		return setSuccessModelMap(modelMap, voList);
	}


}
