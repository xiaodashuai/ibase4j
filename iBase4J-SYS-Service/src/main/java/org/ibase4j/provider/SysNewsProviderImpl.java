package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.SysNews;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 *
 */
@CacheConfig(cacheNames = "sysNews")
@Service(interfaceClass = ISysNewsProvider.class)
public class SysNewsProviderImpl extends BaseProviderImpl<SysNews> implements ISysNewsProvider {

}
