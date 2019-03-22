package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.SysUserDataRule;
import org.springframework.cache.annotation.CacheConfig;

@CacheConfig(cacheNames = "sysUserDataRule")
@Service(interfaceClass=ISysUserDataRuleProvider.class)
public class SysUserDataRuleProviderImpl extends BaseProviderImpl<SysUserDataRule> implements ISysUserDataRuleProvider {

}
