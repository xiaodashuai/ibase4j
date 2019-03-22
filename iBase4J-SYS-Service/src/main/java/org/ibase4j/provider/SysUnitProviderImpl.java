package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.SysUnit;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 *
 */
@CacheConfig(cacheNames = "sysUnit")
@Service(interfaceClass = ISysUnitProvider.class)
public class SysUnitProviderImpl extends BaseProviderImpl<SysUnit> implements ISysUnitProvider {

}
