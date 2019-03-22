package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysDic;
import org.ibase4j.service.BizDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 码表管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:34
 */
@RestController
@Api(value = "码表管理", description = "码表管理")
@RequestMapping(value = "dic")
public class BizDicController extends BaseController {
	@Autowired
	private BizDicService bizDicService;

	@ApiOperation(value = "查询码表项")
	@PostMapping(value = "read/list")
	public Object getDic(ModelMap modelMap, @RequestBody(required = false) SysDic sysDic) {
		List<SysDic> list = bizDicService.queryDicByCode(sysDic.getType());
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation("根据类型查询码表")
	@PostMapping(value = "/read/query")
	public Object getDicByType(ModelMap modelMap, @RequestBody(required = false) SysDic sysDic) {
		List<SysDic> list = bizDicService.queryDicByType(sysDic.getType());
		return this.setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "码表项详情")
	@PostMapping(value = "read/getByTypeCode")
	public Object getByTypeCode(ModelMap modelMap, @RequestBody(required = false) SysDic sysDic) {
		SysDic record = bizDicService.queryByTypeAndCode(sysDic.getType(), sysDic.getCode());
		return setSuccessModelMap(modelMap, record);
	}

}
