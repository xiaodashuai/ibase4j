
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizRiskAnalysis;
import org.ibase4j.provider.IBizRiskAnalysisProvider;
import org.springframework.stereotype.Service;

/**
 * 描述：请假流程
 * 
 * @author czm
 * @version 1.0
 * @date 2018/1/15
 */
@Service
public class BizRiskAnalysisService extends BaseService<IBizRiskAnalysisProvider, BizRiskAnalysis> {
	@Reference
	public void setIBizRiskAnalysisProvider(IBizRiskAnalysisProvider provider) {
		this.provider = provider;
	}

}
