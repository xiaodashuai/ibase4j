package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizMrForBouItHimMapper;
import org.ibase4j.model.BizMrForBouItHim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizMrForBouItHim")
@Service(interfaceClass = BizMrForBouItHimProvider.class)
public class BizMrForBouItHimProviderImpl extends BaseProviderImpl<BizMrForBouItHim> implements BizMrForBouItHimProvider {

    @Autowired
    private BizMrForBouItHimMapper mrForBouItHimapper;


}
