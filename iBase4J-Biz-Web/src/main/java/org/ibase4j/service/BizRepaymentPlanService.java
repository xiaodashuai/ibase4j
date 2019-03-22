package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizRepaymentPlan;
import org.ibase4j.provider.BizRepaymentPlanProvider;
import org.springframework.stereotype.Service;

/**
 * @author lwh
 * @version 2018年7月24日 还款计划
 */
@Service
public class BizRepaymentPlanService extends BaseService<BizRepaymentPlanProvider, BizRepaymentPlan> {
	@Reference
	public void setProvider(BizRepaymentPlanProvider provider) {
		this.provider = provider;
	}
}
