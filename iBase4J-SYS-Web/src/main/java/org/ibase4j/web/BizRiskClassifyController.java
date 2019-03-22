package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizClassifyLevel;
import org.ibase4j.model.BizRiskClassify;
import org.ibase4j.service.BizClassifyLevelService;
import org.ibase4j.service.BizRiskClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 风险分类等级类
 * 
 * @author lianwenhao
 */ 
@RestController
@Api(value = "风险分类等级", description = "风险分类等级")
@RequestMapping(value = "riskClassify")
public class BizRiskClassifyController extends BaseController {

	@Autowired
	private BizRiskClassifyService bizRiskClassifyService;

	@Autowired
	private BizClassifyLevelService bizClassifyLevelService;

	@ApiOperation(value = "查询风险分类等级")
	@RequiresPermissions("post.classify.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> record) {
		Page<?> list = bizRiskClassifyService.query(record);
//		Page<RiskClassifyVo> vList = new Page<>();
//		List<RiskClassifyVo> voList = new ArrayList<>();
//		List<BizRiskClassify> levelList = (List<BizRiskClassify>) list.getRecords();
//		for (BizRiskClassify bizRiskClassify:levelList
//			 ) {
//			RiskClassifyVo vo = new RiskClassifyVo();
//
//			Map<String, Object> map = new HashMap<>();
////			String clCode = "clCode";
////			map.put(clCode,bizRiskClassify.getClCode());
//			Page<BizClassifyLevel> bList = bizClassifyLevelService.query(map);
//			List<BizClassifyLevel> riskList = (List<BizClassifyLevel>) bList.getRecords();
//			for (BizClassifyLevel b:riskList
//				 ) {
//				if (b.getClCode().equals(bizRiskClassify.getClCode())){
//					vo.setName(b.getClName());
//				}
//			}
//			vo.setId(bizRiskClassify.getId());
//			vo.setRemark(bizRiskClassify.getRemark());
//			vo.setClassifyDate(bizRiskClassify.getClDate());
//			vo.setOverdueHint(bizRiskClassify.getOvHint());
//			vo.setClCode(bizRiskClassify.getClCode());
//			voList.add(vo);
//		}
//		vList.setRecords(voList);
//		vList.setCondition(list.getCondition());
//		vList.setTotal(voList.size());
		return setSuccessModelMap(modelMap, list);
	}

	@PostMapping
	@ApiOperation(value = "修改风险分类等级")
	@RequiresPermissions("post.classify.update")
	public Object update(ModelMap modelMap, @RequestBody BizRiskClassify vo) {
		String clCode = vo.getClCode();
		Map<String , Object> map = new HashMap<>();
		map.put("clCode", clCode);
		BizClassifyLevel queryOne = bizClassifyLevelService.queryOne(map);
		vo.setClName(queryOne.getClName());
		vo.setEnable(1);
		bizRiskClassifyService.update(vo);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "添加风险分类等级")
	@RequiresPermissions("post.classify.add")
	@PostMapping(value = "/save")
	public Object add(ModelMap modelMap, @RequestBody BizRiskClassify vo) {
		String clCode = vo.getClCode();
		Map<String , Object> map = new HashMap<>();
		map.put("clCode", clCode);
		BizClassifyLevel queryOne = bizClassifyLevelService.queryOne(map);
		vo.setClName(queryOne.getClName());
		vo.setEnable(1);
		bizRiskClassifyService.update(vo);
//		BizRiskClassify bizRiskClassify = new BizRiskClassify();
//		bizRiskClassify.setClCode(vo.getClCode());
//		bizRiskClassify.setClDate(vo.getClassifyDate());
//		bizRiskClassify.setOvHint(vo.getOverdueHint());
//		bizRiskClassify.setRemark(vo.getRemark());
//		bizRiskClassify.setEnable(1);
//		bizRiskClassifyService.update(bizRiskClassify);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "风险分类详情")
	@RequiresPermissions("post.classify.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody BizRiskClassify vo) {
		BizRiskClassify queryById = bizRiskClassifyService.queryById(vo.getId());
		return setSuccessModelMap(modelMap, queryById);
	}

	@ApiOperation(value = "查询组织机构下拉框")
	@RequiresPermissions("post.classify.read")
	@PostMapping(value = "/read/queryOrgList")
	public Object getOrgChooses(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		List<?> orgList = bizClassifyLevelService.queryList(params);
		return setSuccessModelMap(modelMap, orgList);
	}





}
