package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizRiskAnalysisMapper;
import org.ibase4j.model.BizRiskAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "bizRiskAnalysis")
@Service(interfaceClass = IBizRiskAnalysisProvider.class)
public class IBizRiskAnalysisProviderImpl extends BaseProviderImpl<BizRiskAnalysis> implements IBizRiskAnalysisProvider {
	@Autowired
	private BizRiskAnalysisMapper bizRiskAnalysisMapper;
	
}
