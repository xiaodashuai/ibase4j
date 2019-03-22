package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.SysEmailConfig;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 *
 */
@CacheConfig(cacheNames = "sysEmailConfig")
@Service(interfaceClass = ISysEmailConfigProvider.class)
public class SysEmailConfigProviderImpl extends BaseProviderImpl<SysEmailConfig> implements ISysEmailConfigProvider {

}
