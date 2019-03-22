package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizTemporarySave;
import org.ibase4j.service.BizDebtSummaryService;
import org.ibase4j.service.BizTempSaveService;
import org.ibase4j.vo.GrantRuleVerifVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 暂存管理，负责临时保存、清除、完善数据 日期：2018/8/31
 * 
 * @author czm
 * @version 1.0
 */
@RestController
@Api(value = "暂存管理", description = "暂存管理")
@RequestMapping(value = "temp")
public class BizTempController extends BaseController {
	@Autowired
	private BizTempSaveService tempSaveService;
	@Autowired
	private BizDebtSummaryService bizDebtSummaryService;

	
	@ApiOperation(value = "发放暂存")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/tempStorage")
	public Object tempStorage(ModelMap modelMap, @RequestBody Map<String, Object> record) {
		boolean success = tempSaveService.saveTemp(record);
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "方案约束检查")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/check")
	public Object check(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long pid = StringUtil.objectToLong(params.get("properInfo"));
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		GrantRuleVerifVo ds = bizDebtSummaryService.getGrantRuleVoCode(pid, debtCode);

		return setSuccessModelMap(modelMap, ds);
	}
	
	@ApiOperation(value = "查询发放条件有没有暂存记录")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getTempCount")
	public Object getTempCount(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String grantCode = StringUtil.objectToString(params.get("grantCode"));
		int count = tempSaveService.getTempCountByBizCode(grantCode);
		logger.debug(grantCode+"--------存在("+count+")条暂存信息------");
		return setSuccessModelMap(modelMap,Integer.valueOf(count));
	}
	
	@ApiOperation(value = "查询变更发放条件有没有暂存记录")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getChangeTempCount")
	public Object getChangeTempCount(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String grantCode = StringUtil.objectToString(params.get("grantCode"));
		int count = tempSaveService.getTempCountLikeBizCode(grantCode);
		logger.debug(grantCode+"--------存在("+count+")条暂存信息------");
		return setSuccessModelMap(modelMap,Integer.valueOf(count));
	}
	
	@ApiOperation(value = "查询暂存列表")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/queryTemp")
	public Object queryTemp(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		//需要排除的数据
		params.put("exclId","M");
		Page<BizTemporarySave> list = tempSaveService.getTempResult(params);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "继续填写暂存内容")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/editTemp")
	public Object editTemp(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long id = StringUtil.objectToLong(params.get("id"));
		Object list = tempSaveService.getTempFile(id);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "删除暂存内容")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/delTemp")
	public Object delTemp(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long id = StringUtil.objectToLong(params.get("id"));
		boolean success = tempSaveService.delTempDataAndFile(id);
		return setSuccessModelMap(modelMap, success);
	}
	
	@ApiOperation(value = "查询历史数据迁移的暂存列表")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/queryHistory")
	public Object queryHistroy(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		params.put("taskId", "M");
		Page<BizTemporarySave> list = tempSaveService.getTempResult(params);
		return setSuccessModelMap(modelMap, list);
	}

}
