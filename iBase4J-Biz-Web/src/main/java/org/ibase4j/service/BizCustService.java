package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizCust;
import org.ibase4j.provider.BizCustProvider;
import org.springframework.stereotype.Service;
/**
 * @author XiaoYu
 * @date 2018/06/27
 */
@Service
public class BizCustService extends BaseService<BizCustProvider, BizCust> {
	@Reference
	public void setProvider(BizCustProvider provider) {
		this.provider = provider;
	}
}
