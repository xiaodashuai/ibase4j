package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.BizMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * 短信发送记录
 * 
 * @author xy
 * @date 2018/05/25
 */

@RestController
@Api(value = "短信发送记录", description = "短信发送记录")
@RequestMapping(value = "/msg")
public class BizMsgController extends BaseController {
	@Autowired
	private BizMsgService bizMsgService;
	
	@ApiOperation(value = "查询短信发送记录")
	@RequiresPermissions("record.msg.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> bizMsg) {
		Page<?> list = bizMsgService.query(bizMsg);
		return setSuccessModelMap(modelMap, list);
	}
}
