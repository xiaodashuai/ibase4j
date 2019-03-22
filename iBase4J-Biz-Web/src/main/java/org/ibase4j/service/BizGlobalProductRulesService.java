
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizGlobalProductRules;
import org.ibase4j.provider.IBizGlobalProductRulesProvider;
import org.springframework.stereotype.Service;

/**
 * 描述：请假流程
 * 
 * @author czm
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizGlobalProductRulesService extends BaseService<IBizGlobalProductRulesProvider, BizGlobalProductRules> {
	@Reference
	public void setIBizGlobalProductRulesProvider(IBizGlobalProductRulesProvider provider) {
		this.provider = provider;
	}

}
