package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizFactoringMapper;
import org.ibase4j.model.BizFactoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizFactoring")
@Service(interfaceClass = BizFactoringProvider.class)
public class BizFactoringProviderImpl extends BaseProviderImpl<BizFactoring> implements BizFactoringProvider {

    @Autowired
    private BizFactoringMapper factoringapper;


}
