package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizLCMapper;
import org.ibase4j.model.BizLC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizLC")
@Service(interfaceClass = BizLCProvider.class)
public class BizLCProviderImpl extends BaseProviderImpl<BizLC> implements BizLCProvider {

    @Autowired
    private BizLCMapper LCapper;


}
