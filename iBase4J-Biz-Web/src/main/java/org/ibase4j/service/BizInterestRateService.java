package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizInterestRate;
import org.ibase4j.provider.BizInterestRateProvider;
import org.springframework.stereotype.Service;

/**
 * @author xiaoshuiquan
 * @date 2018/07/17
 */
@Service
public class BizInterestRateService extends BaseService<BizInterestRateProvider, BizInterestRate> {
	@Reference
	public void setProvider(BizInterestRateProvider provider) {
		this.provider = provider;
	}


	
}
