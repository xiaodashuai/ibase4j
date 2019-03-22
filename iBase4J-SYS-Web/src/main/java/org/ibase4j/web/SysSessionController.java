package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.listener.SessionListener;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.BizSession;
import org.ibase4j.model.SysSession;
import org.ibase4j.service.SysSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户会话管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:06
 */
@RestController
@Api(value = "会话管理", description = "会话管理")
@RequestMapping(value = "/session")
public class SysSessionController extends BaseController {
	@Autowired
	private SysSessionService sysSessionService;


	// 查询会话
	@ApiOperation(value = "查询会话")
	@PostMapping(value = "/read/list")
	@RequiresPermissions("sys.base.session.read")
	public Object get(ModelMap modelMap, @RequestBody(required = false) Map<String, Object> sysSession) {
		Page<?> list = sysSessionService.query(sysSession);
		Integer number = SessionListener.getAllUserNumber();
		modelMap.put("userNumber", number); // 用户数大于会话数,有用户没有登录
		return setSuccessModelMap(modelMap, list);
	}
	// 查询会话
	@ApiOperation(value = "查询前台会话")
	@PostMapping(value = "/read/bizList")
	@RequiresPermissions("sys.base.session.read")
	public Object getList(ModelMap modelMap, @RequestBody(required = false) Map<String, Object> bizSession) {
		Page<?> list = sysSessionService.queryBizList(bizSession);
		Integer number = SessionListener.getAllUserNumber();
		modelMap.put("userNumber", number); // 用户数大于会话数,有用户没有登录
		return setSuccessModelMap(modelMap, list);
	}

	@DeleteMapping(value = "/deleteSysSession")
	@ApiOperation(value = "删除SYS会话")
//	@RequiresPermissions("sys.base.session.delete")
	public Object update(ModelMap modelMap, @RequestBody SysSession param) {
		sysSessionService.delete(param.getId());
		return setSuccessModelMap(modelMap);
	}


	@DeleteMapping
	@ApiOperation(value = "删除BIZ会话踢出")
//	@RequiresPermissions("sys.base.session.delete")
	public Object updateBizSession(ModelMap modelMap, @RequestBody BizSession param) {
		Long userId = SysWebUtil.getCurrentUser();
		sysSessionService.deleteBizSession(param.getId(),userId);
		return setSuccessModelMap(modelMap);
	}
}
