package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysChargeType;
import org.ibase4j.service.SysChargeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 通知管理控制类
 *
 * @author XiaoYu
 * @version 2018年8月23日 上午10:12:00
 */
@RestController
@Api(value = "费用类型管理", description = "费用类型管理")
@RequestMapping(value = "chargeType")
public class SysChargeTypeController extends BaseController {
	
	@Autowired 
	private SysChargeTypeService sysChargeTypeService;
	
	@ApiOperation(value = "查询费用类型")
	@RequiresPermissions("post.chargeType.read")
	@RequestMapping(value = "/read/list", method = RequestMethod.POST)
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = sysChargeTypeService.queryAll(params);
		return setSuccessModelMap(modelMap, list);
	}

	@ApiOperation(value = "费用类型详情")
	@RequiresPermissions("post.chargeType.read")
	@RequestMapping(value = "/read/detail", method = RequestMethod.POST)
	public Object detail(ModelMap modelMap, @RequestBody SysChargeType params) {
		SysChargeType record = sysChargeTypeService.queryById(params.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@PostMapping
	@ApiOperation(value = "修改费用类型")
	@RequiresPermissions("post.chargeType.update")
	public Object update(ModelMap modelMap, @RequestBody SysChargeType record) {
		sysChargeTypeService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "删除费用类型")
	@RequiresPermissions("post.chargeType.delete")
	@RequestMapping(value="/delete")
	public Object delete(ModelMap modelMap, @RequestBody SysChargeType record) {
		sysChargeTypeService.delete(record.getId());
		return setSuccessModelMap (modelMap);
	}
//	//检查费用类型名称是否已存在
//		@ApiOperation(value = "检查费用类型名称是否已存在")
//		@RequiresPermissions("post.chargeType.read")
//		@GetMapping(value = "/read/noticeTitle")
//		@ResponseBody
//		public Object selectNoticeTitle(ModelMap modelMap, String account) {
//			Map<String,Object> resultMap = new HashMap<String, Object>();  
//			 Boolean b = sysChargeTypeService.queryBy(account);
//			 resultMap.put("accounts", b);
//			return resultMap;
//		}
//		//检查费用类型代码是否已存在
//		@ApiOperation(value = "检查费用类型代码是否已存在")
//		@RequiresPermissions("post.chargeType.read")
//		@GetMapping(value = "/read/noticeTitle")
//		@ResponseBody
//		public Object select(ModelMap modelMap, String account) {
//			Map<String,Object> resultMap = new HashMap<String, Object>();  
//			Boolean b = sysChargeTypeService.queryBy(account);
//			resultMap.put("accounts", b);
//			return resultMap;
//		}

}
