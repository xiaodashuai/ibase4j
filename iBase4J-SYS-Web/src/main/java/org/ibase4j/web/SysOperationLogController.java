package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.SysOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 操作日志控制类
 * 
 * @author gby
 * @version 2018年5月14日 下午3:13:31
 */
@RestController
@Api(value = "操作日志", description = "操作日志")
@RequestMapping(value = "operationLog")
public class SysOperationLogController extends BaseController {
	@Autowired
	private SysOperationLogService sysOperationLogService;

	@ApiOperation(value = "查询系统操作日志")
	@RequiresPermissions("log.operationLog.read")
	@RequestMapping(value = "/read/list", method = RequestMethod.POST)
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = sysOperationLogService.queryMap(params);
		return setSuccessModelMap(modelMap, list);
	}
}
