package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizGuaranteeResult;
import org.ibase4j.provider.BizGuaranteeResultProvider;
import org.springframework.stereotype.Service;

/**
 * @author lwh
 * @version 2018年7月24日 发放担保信息
 */
@Service
public class BizGuaranteeResultService extends BaseService<BizGuaranteeResultProvider, BizGuaranteeResult> {
	@Reference
	public void setProvider(BizGuaranteeResultProvider provider) {
		this.provider = provider;
	}
}
