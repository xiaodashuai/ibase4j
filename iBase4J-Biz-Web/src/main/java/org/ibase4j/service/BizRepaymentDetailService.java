package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizRepaymentDetail;
import org.ibase4j.provider.BizRepaymentDetailProvider;
import org.springframework.stereotype.Service;

/**
 * @author lwh
 * @version 2018年7月24日 还款计划详细信息
 */
@Service
public class BizRepaymentDetailService extends BaseService<BizRepaymentDetailProvider, BizRepaymentDetail> {
	@Reference
	public void setProvider(BizRepaymentDetailProvider provider) {
		this.provider = provider;
	}
}
