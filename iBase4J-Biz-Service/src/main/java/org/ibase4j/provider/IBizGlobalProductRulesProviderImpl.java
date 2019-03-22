package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizGlobalProductRulesMapper;
import org.ibase4j.model.BizGlobalProductRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "bizGlobalProductRules")
@Service(interfaceClass = IBizGlobalProductRulesProvider.class)
public class IBizGlobalProductRulesProviderImpl extends BaseProviderImpl<BizGlobalProductRules> implements IBizGlobalProductRulesProvider {
	@Autowired
	private BizGlobalProductRulesMapper bizGlobalProductRulesMapper;
	
}
