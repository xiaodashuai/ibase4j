package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizFutForeExcSettMapper;
import org.ibase4j.model.BizFutForeExcSett;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizFutForeExcSett")
@Service(interfaceClass = BizFutForeExcSettProvider.class)
public class BizFutForeExcSettProviderImpl extends BaseProviderImpl<BizFutForeExcSett> implements BizFutForeExcSettProvider {

    @Autowired
    private BizFutForeExcSettMapper futForeExcSettMapper;


}
