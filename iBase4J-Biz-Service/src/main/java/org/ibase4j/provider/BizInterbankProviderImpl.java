package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizInterbankMapper;
import org.ibase4j.model.BizInterbank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizInterbank")
@Service(interfaceClass = BizInterbankProvider.class)
public class BizInterbankProviderImpl extends BaseProviderImpl<BizInterbank> implements BizInterbankProvider {

    @Autowired
    private BizInterbankMapper interbankapper;


}
