package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizTFMapper;
import org.ibase4j.model.BizTF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizTF")
@Service(interfaceClass = BizTFProvider.class)
public class BizTFProviderImpl extends BaseProviderImpl<BizTF> implements BizTFProvider {

    @Autowired
    private BizTFMapper TFapper;


}
