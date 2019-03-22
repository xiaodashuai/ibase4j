/**
 * 
 */
package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.model.NameValuePair;
import org.ibase4j.service.BizChargeTypeService;
import org.ibase4j.service.BizCurrencyService;
import org.ibase4j.service.BizGuaranteeTypeService;
import org.ibase4j.service.BizProductTypeService;
import org.ibase4j.vo.PairVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 描述：前台使用各种组件
 * @author czm
 * @date 2018/7/13
 * @version 1.0
 */
@RestController
@Api(value = "组件管理", description = "组件管理")
@RequestMapping(value = "/components")
public class BizComponentsController extends BaseController {
	@Autowired
	private BizCurrencyService bizCurrencyService;
	@Autowired
	private BizGuaranteeTypeService bizGuaranteeTypeService;
	@Autowired
	private BizChargeTypeService bizChargeTypeService;
	@Autowired
	private BizProductTypeService bizProductTypeService;
	
	@ApiOperation(value = "查询产品对象")
	@PutMapping(value = "/product")
	public Object getProduct(ModelMap modelMap, @RequestBody(required = false) Map<String,Object> params) {
		BizProductTypes type = bizProductTypeService.queryOne(params);
		return setSuccessModelMap(modelMap, type);
	}
	
	@ApiOperation(value = "货币单位")
	@PutMapping(value = "/currency")
	public Object getCurrency(ModelMap modelMap, @RequestBody(required = false) Map<String,Object> params) {
		String code = StringUtil.objectToString(params.get("code"));
		List<NameValuePair> result = bizCurrencyService.getCurrencyPair(code);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "押品类型")
	@PutMapping(value = "/guaranteeType")
	public Object getGuaranteeType(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String code = StringUtil.objectToString(params.get("parentCode"));
		List<NameValuePair> result = bizGuaranteeTypeService.getGuaranteeTypes(code,BizContant.BIZ_BNS_GUARANTEE_TYPE);
		return setSuccessModelMap(modelMap, result);
	}
	

	@ApiOperation(value = "担保类型")
	@PutMapping(value = "/guaranteeInfoType")
	public Object getGuaranteeInfoType(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String code = StringUtil.objectToString(params.get("parentCode"));
		List<NameValuePair> result = bizGuaranteeTypeService.getGuaranteeTypes(code,BizContant.BIZ_BNS_INFO_TYPE);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "通过类型查询多级码表")
	@PutMapping(value = "/getDicType")
	public Object getDicType(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String parentCode = StringUtil.objectToString(params.get("parentCode"));
		String type = StringUtil.objectToString(params.get("type"));
		List<PairVo> result = bizGuaranteeTypeService.getByTypeCode(type, parentCode);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "通过多个类型查询多级码表")
	@PutMapping(value = "/getMulitDicType")
	public Object getMulitDicType(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String parentCodes = StringUtil.objectToString(params.get("parentCodes"));
		String types = StringUtil.objectToString(params.get("types"));
		Map<String,List<PairVo>> result = bizGuaranteeTypeService.getByMulitTypeCode(types, parentCodes);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "通过类型查询多级码表详情")
	@PutMapping(value = "/getDicTypeDetail")
	public Object getDicTypeDetail(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String typeCodes = StringUtil.objectToString(params.get("types"));
		String codes = StringUtil.objectToString(params.get("codes"));
		List<PairVo> result = bizGuaranteeTypeService.getByCode(typeCodes,codes);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "通过类型和编码查询详情")
	@PutMapping(value = "/getTypeDetail")
	public Object getTypeDetail(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String type = StringUtil.objectToString(params.get("type"));
		String code = StringUtil.objectToString(params.get("code"));
		PairVo result = bizGuaranteeTypeService.getPairByTypeCode(type, code);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "利率类型")
	@PutMapping(value = "/interestRate")
	public Object getInterestRate(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String code = StringUtil.objectToString(params.get("parentCode"));
		List<NameValuePair> result = bizGuaranteeTypeService.getInterestRateTypes(code);
		return setSuccessModelMap(modelMap, result);
	}
	
	@ApiOperation(value = "可用收费类型")
	@PutMapping(value = "/chargeType")
	public Object getChargeType(ModelMap modelMap,@RequestBody(required = false) Map<String,Object> params) {
		String code = StringUtil.objectToString(params.get("productTypesCode"));
		List<NameValuePair> result = bizChargeTypeService.getChargeTypeByProductCode(code);
		return setSuccessModelMap(modelMap, result);
	}

	@ApiOperation(value = "查询经办机构")
	@PutMapping(value = "/getDept")
	public Object getDept(ModelMap modelMap, @RequestBody(required = false) Map<String,Object> params) {
		List<NameValuePair> result = bizCurrencyService.getDeptList();
		return setSuccessModelMap(modelMap, result);
	}
	
}
