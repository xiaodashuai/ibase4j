/**
 * 
 */
package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.BizDebtSummary;
import org.ibase4j.model.NameValuePair;
import org.ibase4j.service.BizDebtSummaryService;
import org.ibase4j.service.BizDeptGrantService;
import org.ibase4j.service.BizGrantService;
import org.ibase4j.vo.GrantRuleVerifVo;
import org.ibase4j.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 描述：债项发放申请
 * 
 * @author czm
 * @date 2018/7/13
 * @version 1.0
 */
@RestController
@Api(value = "债项发放申请", description = "债项发放申请")
@RequestMapping(value = "/grant")
public class BizGrantController extends BaseController {

	@Autowired
	private BizDebtSummaryService bizDebtSummaryService;
	// 债项发放
	@Autowired
	private BizGrantService bizGrantService;
	// 债项发放
	@Autowired
	private BizDeptGrantService bizDeptGrantService;

	@ApiOperation(value = "查询方案")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = bizDebtSummaryService.queryByCompletedSolutions(params);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "查询可发放的产品列表")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/read/getProductList")
	public Object getProductList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		List<ProductVo> list = bizDebtSummaryService.getProduct(debtCode);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "新增发放申请")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/add")
	public Object add(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		BizDebtSummary ds = bizDebtSummaryService.getByCode(debtCode);
		return setSuccessModelMap(modelMap, ds);
	}

	@ApiOperation(value = "发放申请约束")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/check")
	public Object check(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long pid = StringUtil.objectToLong(params.get("properInfo"));
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		GrantRuleVerifVo ds = bizDebtSummaryService.getGrantRuleVoCode(pid, debtCode);

		return setSuccessModelMap(modelMap, ds);
	}

	@ApiOperation(value = "查看方案选择的币种")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getCurrency")
	public Object getCurrency(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		List<NameValuePair> nameValues = bizDeptGrantService.getCurrencyPair(debtCode);
		return setSuccessModelMap(modelMap, nameValues);
	}

	@ApiOperation(value = "查看方案选择的担保类型")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getGuaranteeInfo")
	public Object getGuaranteeInfo(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String debtCode = StringUtil.objectToString(params.get("debtCode"));
		String parentCode = StringUtil.objectToString(params.get("parentCode"));
		List<NameValuePair> nameValues = null;
		if (StringUtils.isNotBlank(parentCode)) {
			nameValues = bizDeptGrantService.getGuaranteeInfoByParentCode(debtCode, parentCode);
		} else {
			nameValues = bizDeptGrantService.getGuaranteeInfoList(debtCode);
		}
		return setSuccessModelMap(modelMap, nameValues);
	}

	@ApiOperation(value = "保存发放申请")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/save")
	public Object save(ModelMap modelMap, @RequestBody Map<String, Object> record) throws Exception {
		BizDebtGrant model = bizGrantService.save(record);
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "修改状态")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/updateStatus")
	public Object updateStatus(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long currentUser = BizWebUtil.getCurrentUser();
		params.put("userId", currentUser.toString());
		bizDeptGrantService.updateStatus(params);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "废弃之前查询发放信息")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getById")
	public Object getById(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long id = StringUtil.objectToLong(params.get("grantId"));
		BizDebtGrant model = bizGrantService.getById(id);
		return setSuccessModelMap(modelMap, model);
	}
	
	
	@ApiOperation(value = "变更修改")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getGrant")
	public Object getGrant(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Map<String, Object> map = bizGrantService.getEditChangeGrant(params);
		return setSuccessModelMap(modelMap, map);
	}
	
	@ApiOperation(value = "保存变更申请")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/saveChange")
	public Object saveChange(ModelMap modelMap, @RequestBody Map<String, Object> record) throws Exception {
		BizDebtGrant model = bizGrantService.save(record);
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "发起废弃")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/saveDiscard")
	public Object saveDiscard(ModelMap modelMap, @RequestBody Map<String, Object> record) {
		BizDebtGrant model = bizGrantService.saveDiscard(record);
		return setSuccessModelMap(modelMap, model);
	}
	
	@ApiOperation(value = "删除发放")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/del")
	public Object delete(ModelMap modelMap, @RequestBody Map<String, Object> record) {
		BizDebtGrant model = bizGrantService.saveDisabled(record);
		return setSuccessModelMap(modelMap, model);
	}

	@ApiOperation(value = "发放条件查询")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/grantQueryList")
	public Object grantQueryList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> result = bizDeptGrantService.queryResultList(params);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "获取概要信息")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/getGrantInfo")
	public Object getGrantInfo(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		BizDebtGrant grantInfo = bizGrantService.getGrantInfo(params);
		return setSuccessModelMap(modelMap,grantInfo);
	}
	
	@ApiOperation(value = "驳回后重新编辑修改")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/edit")
	public Object edit(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Map<String, Object> map = bizGrantService.getEditGrant(params);
		return setSuccessModelMap(modelMap, map);
	}

	@ApiOperation(value = "重新提交驳回申请")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/update")
	public Object update(ModelMap modelMap, @RequestBody Map<String, Object> record) throws Exception {
		BizDebtGrant model = bizGrantService.update(record);
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "通过债项方案编码查询方案")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getDebt")
	public Object getDebt(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String debtCode = StringUtil.objectToString(params.get("debtCode")) ;
		BizDebtSummary model = bizDeptGrantService.getByDebtCode(debtCode);
		return setSuccessModelMap(modelMap, model);
	}
}
