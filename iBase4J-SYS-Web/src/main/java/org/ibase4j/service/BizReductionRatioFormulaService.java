package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.BizReductionRatioFormula;
import org.ibase4j.provider.BizReductionRatioFormulaProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xy
 * @date 2018/05/09
 */
@Service
public class BizReductionRatioFormulaService extends BaseService<BizReductionRatioFormulaProvider, BizReductionRatioFormula> {
	@Reference
	public void setProvider(BizReductionRatioFormulaProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * 功能：查询所有检查计划配置
	 * @author xy
	 * @return 返回所有有效的减值比例配置信息
	 */
	
	public List<BizReductionRatioFormula> getAllReductionRatioFormula(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		//查询所有状态为1(是)的减值比例配置
		List<BizReductionRatioFormula> reductionRatioFormulaList = provider.queryList(params);
		return reductionRatioFormulaList;
	}
}
