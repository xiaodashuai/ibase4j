package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizCountryRiskRating;
import org.ibase4j.service.BizClassifyLevelService;
import org.ibase4j.service.BizCountryRiskRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * 国别风险设置
 * 
 * @author XiaoYu
 * @date 2018/06/27
 */

@RestController
@Api(value = "国别风险设置", description = "国别风险设置")
@RequestMapping(value = "/countryRiskRating")
public class BizCountryRiskRatingController extends BaseController {
	@Autowired
	private BizCountryRiskRatingService bizCountryRiskRatingService;
	@Autowired
	private BizClassifyLevelService bizClassifyLevelService;
	
	@ApiOperation(value = "国别风险信息查询")
	@RequiresPermissions("biz.countryRiskRating.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		Page<BizCountryRiskRating> query = bizCountryRiskRatingService.query(param);
		return setSuccessModelMap(modelMap, query);
	}
	@ApiOperation(value = "国别风险等级查询")
	@RequiresPermissions("biz.countryRiskRating.read")
	@PostMapping(value = "/read/detail")
	public Object getById(ModelMap modelMap, @RequestBody BizCountryRiskRating bizCountryRiskRating) {
		BizCountryRiskRating queryById = bizCountryRiskRatingService.queryById(bizCountryRiskRating.getId());
		return setSuccessModelMap(modelMap, queryById);
	}
	@PostMapping
	@ApiOperation(value = "修改国别风险信息")
	@RequiresPermissions("biz.countryRiskRating.update")
	public Object update(ModelMap modelMap, @RequestBody BizCountryRiskRating record) {
		record.setEnable(1);
		bizCountryRiskRatingService.update(record);
		return setSuccessModelMap(modelMap);
	}
	@ApiOperation(value = "查询等级")
	@RequiresPermissions("post.level.read")
	@PostMapping(value = "/read/riskLevel")
	public Object getRiskLevel(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = bizClassifyLevelService.query(params);
		return setSuccessModelMap(modelMap, list);
	}
}
