package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizRentalFactoring;
import org.ibase4j.provider.BizRentalFactoringProvider;
import org.springframework.stereotype.Service;

/**
 * @author lwh
 * @version 2018年7月24日 租金保理
 */
@Service
public class BizRentalFactoringService extends BaseService<BizRentalFactoringProvider, BizRentalFactoring> {
	@Reference
	public void setProvider(BizRentalFactoringProvider provider) {
		this.provider = provider;
	}
}
