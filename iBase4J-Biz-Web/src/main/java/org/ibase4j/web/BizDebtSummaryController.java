package org.ibase4j.web;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.IDBulider;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.*;
import org.ibase4j.service.BizCurrencyService;
import org.ibase4j.service.BizDebtSummaryService;
import org.ibase4j.vo.PairVo;
import org.ibase4j.vo.ProductBussinessVo;
import org.ibase4j.vo.SumInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* 描述：债项概要
* @author xiaoshuiquan
* @date  2018.5.15
* */
@RestController
@Api(value = "债项概要", description = "债项概要")
@RequestMapping(value = "debtSummary")
public class BizDebtSummaryController extends BaseController {

	@Autowired
	private BizDebtSummaryService debtSummaryService;
    @Autowired
    private BizCurrencyService bizCurrencyService;

	@ApiOperation(value = "查询用户信贷员")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getUserList/list")
	public Object getUserList(ModelMap modelMap, @RequestBody Map<String, Object> sysDept) {
		SysUser user = debtSummaryService.getUserList();
		return setSuccessModelMap(modelMap, user);
	}

	@ApiOperation(value = "查询用户所在部门")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping (value = "/getDeptList/list")
	public Object getDeptList(ModelMap modelMap, @RequestBody Map<String, Object> sysDept) {
		SysDept dept = debtSummaryService.getDeptList();
		return setSuccessModelMap(modelMap, dept);
	}

	@ApiOperation(value = "查询全部产品业务")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getProductBusiness/list")
	public Object getProductBusiness(ModelMap modelMap, @RequestBody Map<String, Object> productBusiness) {
//		List<ProductBussinessVo> productBusinessList = debtSummaryService.getProductBusiness();
		Set<PairVo> pbusiness = debtSummaryService.getAllBusinessType(productBusiness);
		return setSuccessModelMap(modelMap, pbusiness);
	}

	@ApiOperation(value = "查询租金保理业务")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getRentFactorList/list")
	public Object getRentFactorList(ModelMap modelMap, @RequestBody Map<String, Object> productBusiness) {
		List<BizProductTypes> pbusiness = debtSummaryService.getRentFactorList();
		return setSuccessModelMap(modelMap, pbusiness);
	}

	@ApiOperation(value = "查询勾选的产品")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getProduct/list")
	public Object getProduct(ModelMap modelMap, @RequestBody Map<String, String> product) {
		List<ProductBussinessVo> productBusinessList = debtSummaryService.getProduct(product);
		return setSuccessModelMap(modelMap, productBusinessList);
	}

	@ApiOperation(value = "保存勾选的产品")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getPro/save")
	public Object savePro(@RequestBody Map<String, Object> params, ModelMap modelMap) {
		String proIds = params.get("proIds").toString();
		String debNu = IDBulider.generator();
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("debtNum", debNu);
		debtSummaryService.savePro(proIds,debNu);
		return setSuccessModelMap(modelMap, dataMap);
	}

	@ApiOperation(value = "保存债项方案")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/saveDebt/list")
	public Object getDebtSummary(@RequestBody JSONObject objectDebtMain, ModelMap modelMap) {
		debtSummaryService.save(objectDebtMain);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "保存单一规则")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getSingleRule/save")
	public Object getSingleRule(@RequestBody ProductBussinessVo proVo, ModelMap modelMap) {
		debtSummaryService.saveSingleRule(proVo);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation("根据类型查询字典中的是否")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/read/query")
	public Object getDicByType(ModelMap modelMap, @RequestBody SysDic sysDic) {
		List<SysDic> dicList = debtSummaryService.queryDicByType(sysDic.getType());
		return this.setSuccessModelMap(modelMap, dicList);
	}

	@ApiOperation(value = "查询全部币种")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/chargeType/query")
    public Object getCurrency(ModelMap modelMap, @RequestBody(required = false) Map<String,Object> params) {
        String code = StringUtil.objectToString(params.get("code"));
        List<SysCurrency> result = bizCurrencyService.getCurrency(code);
        return setSuccessModelMap(modelMap, result);
    }

	@ApiOperation(value = "查看债项方案")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/checkDebtScheme/list")
	public Object checkDebtScheme(ModelMap modelMap, @RequestBody(required = false) Map<String,Object> params) {
		Page<?> checkDebtList=debtSummaryService.checkDebtScheme(params);
		return setSuccessModelMap(modelMap, checkDebtList);
	}

	@ApiOperation(value = "查询行业投向")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/industryInvestment")
	public Object getIndustry(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		List<BizGuaranteeType> guaranteeTypeList = debtSummaryService.getIndustry("005");
		return setSuccessModelMap(modelMap, guaranteeTypeList);
	}

	@ApiOperation(value = "得到担保方式类型")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getMortgageList/list")
	public Object getMortgageList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<BizGuaranteeType> guarList=debtSummaryService.getMortgageList(params);
		return setSuccessModelMap(modelMap,guarList);
	}

	@ApiOperation(value = "查询全部的静态客户")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getCustomerList/list")
	public Object getCustomerList(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
		List<BizCust> guarList=debtSummaryService.getCustomerList(customer);
		//List<BizCustDicVO> guarList=debtSummaryService.getALLCustDic(customer);
		return setSuccessModelMap(modelMap,guarList);
	}

	@ApiOperation(value = "方案修订的回显")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getAllSchemeInformation/list")
	public Object getAllSchemeInformation(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Map<String,Object>schemeMap=debtSummaryService.getAllSchemeInformation(param);
		return setSuccessModelMap(modelMap,schemeMap);
	}

	@ApiOperation(value = "查询担保类型分")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getGuaranteeInfo/list")
	public Object getGuaranteeInfo(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<BizGuaranteeType> guList=debtSummaryService.getGuaranteeInfo(params);
		return setSuccessModelMap(modelMap,guList);
	}

	@ApiOperation(value = "担保合同类型/占用额度类型")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getContractTypeList/list")
	public Object getContractTypeList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<BizGuaranteeType> garList=debtSummaryService.getContractTypeList(params);
		return setSuccessModelMap(modelMap,garList);
	}

	@ApiOperation(value = "得到押品编号")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/getRemandNum/list")
	public Object getRemandNum(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		String remandNum=debtSummaryService.getRemandNum(params);
		Map<String,Object> remandNumMap=new HashMap<>();
		remandNumMap.put("remandNum", remandNum);
		return setSuccessModelMap(modelMap,remandNumMap);
	}

	@ApiOperation(value = "得到摘要信息")
	@RequiresPermissions("biz.workflow.read")
	@PostMapping(value = "/getSumInformation/list")
	public Object getSumInformation(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		SumInformationVo sumInformationVo =debtSummaryService.getSumInformation(params);
		return setSuccessModelMap(modelMap,sumInformationVo);
	}

	@ApiOperation(value = "债项方案数据迁移列表")
	@RequiresPermissions("sys.history.debt.read")
	@PostMapping(value = "/checkHistoryDebt/list")
	public Object checkHistoryDebt(ModelMap modelMap, @RequestBody(required = false) Map<String,Object> params) {
		Page<?> checkDebtList=debtSummaryService.checkHistoryDebt(params);
		return setSuccessModelMap(modelMap, checkDebtList);
	}

	@ApiOperation(value = "债项方案数据迁移")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/historyDebt/list")
	public Object historyDebt(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Map<String,Object>schemeMap=debtSummaryService.historyDebt(param);
		return setSuccessModelMap(modelMap,schemeMap);
	}

	@ApiOperation(value = "历史数据的保存")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/historyDebtSave/list")
	public Object historyDebtSave(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		debtSummaryService.historyDebtSave(param);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "已驳回点击删除")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/checkState/list")
	public Object checkState(ModelMap modelMap, @RequestBody BizDebtSummary bizDebtSummary ) {
		debtSummaryService.checkState(bizDebtSummary);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "已驳回重新提交")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/ReSaveDebt/list")
	public Object ReSaveDebt(ModelMap modelMap, @RequestBody JSONObject param) {
		param.put("state","方案修订");
		debtSummaryService.save(param);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "方案查询/历史数据机构查询")
	@RequiresPermissions("before.base.reponse.read")
	@PutMapping(value = "/read/getAllDeptList")
	public Object getAllDeptList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List deptList = debtSummaryService.getAllDeptList();
		return setSuccessModelMap(modelMap, deptList);
	}

	@ApiOperation(value = "担保合同编号校验")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/queryConNum/list")
	public Object queryConNum(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Boolean b = debtSummaryService.queryConNum(params);
		return setSuccessModelMap(modelMap, b);
	}

	@ApiOperation(value = "历史数据迁移编辑提醒")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/queryTem/list")
	public Object queryTem(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Boolean b = debtSummaryService.queryTem(params);
		return setSuccessModelMap(modelMap, b);
	}

	@ApiOperation(value = "查询背景国别")
	@RequiresPermissions("before.base.reponse.read")
	@PostMapping(value = "/read/list")
	public Object getBackground(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<SysDic>dicList = debtSummaryService.getBackground(params);
		return setSuccessModelMap(modelMap, dicList);
	}
}
