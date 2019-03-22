package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysNotice;
import org.ibase4j.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "通知管理", description = "通知管理")
@RequestMapping(value = "notice")
public class SysNoticeController extends BaseController {
	@Autowired
	private SysNoticeService sysNoticeService;

	@ApiOperation(value = "查询公告")
	@RequiresPermissions("sys.base.notice.read")
	@RequestMapping(value = "/read/list", method = RequestMethod.POST)
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = sysNoticeService.query(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "公告详情")
	@RequiresPermissions("sys.base.notice.read")
	@RequestMapping(value = "/read/detail", method = RequestMethod.POST)
	public Object detail(ModelMap modelMap, @RequestBody SysNotice params) {
		SysNotice record = sysNoticeService.queryById(params.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@PostMapping
	@ApiOperation(value = "修改公告")
	@RequiresPermissions("sys.base.notice.update")
	public Object update(ModelMap modelMap, @RequestBody SysNotice record) {
		sysNoticeService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "删除公告")
	@RequiresPermissions("sys.base.notice.delete")
	@RequestMapping(value="/update/deleteNotice")
	public Object delete(ModelMap modelMap, @RequestBody SysNotice record) {
		sysNoticeService.delete(record.getId());
		return setSuccessModelMap (modelMap);
	}

	@ApiOperation(value = "发布公告")
	@RequiresPermissions("sys.base.notice.publishNotice")
	@PostMapping(value="/update/publishNotice")
	public Object publishNotice(ModelMap modelMap, @RequestBody SysNotice record) {
		sysNoticeService.update(record);
		return setSuccessModelMap(modelMap);
	}
	//检查公告名称是否已存在
	@ApiOperation(value = "检查公告名称是否已存在")
	@RequiresPermissions("sys.base.notice.read")
	@GetMapping(value = "/read/noticeTitle")
	@ResponseBody
	public Object selectNoticeTitle(ModelMap modelMap, String account) {
		Map<String,Object> resultMap = new HashMap<String, Object>();  
		 Boolean b = sysNoticeService.queryByNoticeTitle(account);
		 resultMap.put("accounts", b);
		return resultMap;
	}

}
