package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizInterestRateMapper;
import org.ibase4j.model.BizInterestRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/07/17
 */
@CacheConfig(cacheNames = "bizInterestRate")
@Service(interfaceClass = BizInterestRateProvider.class)
public class BizInterestRateProviderImpl extends BaseProviderImpl<BizInterestRate> implements BizInterestRateProvider {
	@Autowired
	private BizInterestRateMapper bizInterestRateMapper;


	
}
