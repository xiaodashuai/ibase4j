package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.BizEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 邮件发送记录
 * 
 * @author xy
 * @date 2018/05/25
 */

@RestController
@Api(value = "邮件发送记录", description = "邮件发送记录")
@RequestMapping(value = "/email")
public class BizEmailController extends BaseController {
	@Autowired
	private BizEmailService bizEmailService;
	
	@ApiOperation(value = "查询邮件发送记录")
	@RequiresPermissions("record.email.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizEmail) {
		Page<?> list = bizEmailService.query(bizEmail);
		return setSuccessModelMap(modelMap, list);
	}
}
