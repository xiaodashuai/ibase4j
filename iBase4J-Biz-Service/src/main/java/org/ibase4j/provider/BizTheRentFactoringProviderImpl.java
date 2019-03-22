package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizTheRentFactoringMapper;
import org.ibase4j.model.BizTheRentFactoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizTheRentFactoring")
@Service(interfaceClass = BizTheRentFactoringProvider.class)
public class BizTheRentFactoringProviderImpl extends BaseProviderImpl<BizTheRentFactoring> implements BizTheRentFactoringProvider {

    @Autowired
    private BizTheRentFactoringMapper TheRentFactoringMapper;


}
