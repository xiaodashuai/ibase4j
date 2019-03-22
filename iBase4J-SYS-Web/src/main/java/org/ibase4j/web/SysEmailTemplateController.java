package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysEmailTemplate;
import org.ibase4j.service.SysEmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件模版管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "邮件模版管理", description = "邮件模版管理")
@RequestMapping(value = "emailTemplate")
public class SysEmailTemplateController extends BaseController {
	@Autowired
	private SysEmailTemplateService sysEmailTemplateService;

	@ApiOperation(value = "查询邮件模版")
	@RequiresPermissions("sys.base.emailTemplate.read")
	//@RequestMapping(value = "/read/list", method = RequestMethod.POST)
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = sysEmailTemplateService.query(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "邮件模版详情")
	@RequiresPermissions("sys.base.emailTemplate.read")
	//@RequestMapping(value = "/read/detail", method = RequestMethod.POST)
	@PostMapping(value = "read/detail")
	public Object detail(ModelMap modelMap, @RequestBody SysEmailTemplate params) {
		SysEmailTemplate record = sysEmailTemplateService.queryById(params.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@ApiOperation(value = "修改邮件模版")
	@RequiresPermissions("sys.base.emailTemplate.add")
	//@RequestMapping(method = RequestMethod.POST)
    @PostMapping(value = "/save")
	public Object update(ModelMap modelMap, @RequestBody SysEmailTemplate record) {
		record.setEnable(1);
		sysEmailTemplateService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "删除邮件模版")
	@RequiresPermissions("sys.email.template.delete")
	@RequestMapping(method = RequestMethod.DELETE)
	public Object delete(ModelMap modelMap, @RequestBody SysEmailTemplate record) {
		sysEmailTemplateService.delete(record.getId());
		return setSuccessModelMap(modelMap);
	}
	//检查邮件模版名称是否已存在
	@ApiOperation(value = "检查邮件模版名称是否已存在")
	@RequiresPermissions("sys.base.emailTemplate.read")
	@GetMapping(value = "/read/emailName")
	@ResponseBody
	public Object selectEmailByName(ModelMap modelMap, String account) {
		Map<String,Object> resultMap = new HashMap<String, Object>();  
		 Boolean b = sysEmailTemplateService.queryByEmailName(account);
		 resultMap.put("accounts", b);
		return resultMap;
	}
}
