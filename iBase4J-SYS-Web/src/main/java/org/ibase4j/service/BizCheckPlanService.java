package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.BizCheckPlan;
import org.ibase4j.provider.BizCheckPlanProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xy
 * @date 2018/05/09
 */
@Service
public class BizCheckPlanService extends BaseService<BizCheckPlanProvider, BizCheckPlan> {
	@Reference
	public void setProvider(BizCheckPlanProvider provider) {
		this.provider = provider;
	}
	/**
	 * 功能：查询所有检查计划配置
	 * @author xy
	 * @return 返回所有有效的检查计划配置信息
	 */
	public List<BizCheckPlan> getAllCheckPlan(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		//查询所有状态为1(是)的检查计划配置
		List<BizCheckPlan> checkPlanList = provider.queryList(params);
		return checkPlanList;
	}

	public void create(BizCheckPlan bizCheckPlan) {
		bizCheckPlan.setEnable(1);
		provider.update(bizCheckPlan);
	}
}
