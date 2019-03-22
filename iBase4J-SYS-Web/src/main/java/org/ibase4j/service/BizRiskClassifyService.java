
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizRiskClassify;
import org.ibase4j.provider.IBizRiskClassifyProvider;
import org.springframework.stereotype.Service;

/**
 * 描述：请假流程
 * 
 * @author czm
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizRiskClassifyService extends BaseService<IBizRiskClassifyProvider, BizRiskClassify> {
	@Reference
	public void setIBizRiskClassifyProvider(IBizRiskClassifyProvider provider) {
		this.provider = provider;
	}

}
