package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.IDBulider;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.*;
import org.ibase4j.service.*;
import org.ibase4j.vo.CeshiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 租金保理专有信息
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "租金保理专有信息", description = "租金保理专有信息")
@RequestMapping(value = "bizRentalFactoring")
public class BizRentalFactoringController extends BaseController {

	@Autowired
	private BizRentalFactoringService bizRentalFactoringService;
	@Autowired
	private BizUserService bizUserService;
	@Autowired
	private BizDebtSummaryService bizDebtSummaryService;
	@Autowired
	private BizCustomerService bizCustomerService;
	@Autowired
	private BizGuaranteeInfoService bizGuaranteeInfoService;
	@Autowired
	private BizSingleProductRuleService bizSingleProductRuleService;

	@PutMapping(value = "/read/detail")
	@ApiOperation(value = "展示信息")
	@RequiresPermissions("event.base.grant.read")
	public Object detail(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Long userId = BizWebUtil.getCurrentUser();
		SysUser user = bizUserService.queryById(userId);
		//
		String debtCode = StringUtil.objectToString(param.get("debtCode"));
		// 查询用信主体
		Map<String, Object> debtCodeMap = new HashMap<>();
		debtCodeMap.put("debtCode", debtCode);
		List<BizCustomer> customerList = bizCustomerService.getBizCustomerList(debtCodeMap);

		List<BizDebtSummary> bizDebtSummaryList = bizDebtSummaryService.queryList(debtCodeMap);
		BizDebtSummary bizDebtSummary = bizDebtSummaryList.get(0);
		// 返回页面
		CeshiVo vo = new CeshiVo();
		vo.setDescriptionProgramQuoqate(bizDebtSummary.getDescriptionProgramQuoqate());
		vo.setDescriptionRateRules(bizDebtSummary.getDescriptionRateRules());
		vo.setPolicy(bizDebtSummary.getPolicy());
		vo.setPolicyDescription(bizDebtSummary.getPolicyDescription());
		if (user != null) {
			SysDept dept = bizUserService.getDepartmentByCode(user.getDeptCode());
			vo.setAttn(user.getUserName());
			vo.setAttnOrg(dept.getDeptName());
		}
		// 背景国别和行业投向回显数据
		List<BizSingleProductRule> bizSingleProductRuleList = bizSingleProductRuleService.queryList(debtCodeMap);
		if (bizSingleProductRuleList.size() > 0) {
			BizSingleProductRule bizSingleProductRule = bizSingleProductRuleList.get(0);
			vo.setIndustryInvestment(bizSingleProductRule.getIndustryInvestment());
			vo.setBackgroundNationality(bizSingleProductRule.getBackgroundNationality());
		}
		vo.setProjectName("365天以上代理开证");
		vo.setRepaymentType("不规则还款");
		vo.setIouCode(IDBulider.getInstance().generatorIouCode());
		vo.setCustUserLetter(customerList);
		return setSuccessModelMap(modelMap, vo);
	}
	
	@PutMapping(value = "/read/getCustUser")
	@ApiOperation(value = "查询客户信息")
	public Object getCustUser(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		String debtCode = StringUtil.objectToString(param.get("debtCode"));
		// 查询用信主体
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("debtCode", debtCode);
		List<BizCustomer> customerList = bizCustomerService.getBizCustomerList(query);
		return setSuccessModelMap(modelMap, customerList);
	}
	
	@PutMapping(value = "/read/getBizGuaranteeInfo")
	@ApiOperation(value = "查询担保信息")
	public Object getBizGuaranteeInfo(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Long id = StringUtil.objectToLong(param.get("gId"));
		BizGuaranteeInfo result = bizGuaranteeInfoService.getById(id);
		return setSuccessModelMap(modelMap,result);
	}

	@PostMapping
	@ApiOperation(value = "保存信息")
	public Object update(ModelMap modelMap, @RequestBody BizRentalFactoring record) {
		bizRentalFactoringService.update(record);
		return setSuccessModelMap(modelMap);
	}


}
