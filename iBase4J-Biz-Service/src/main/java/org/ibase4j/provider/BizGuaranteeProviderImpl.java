package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizGuaranteeMapper;
import org.ibase4j.model.BizGuarantee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizGuaranteeKey")
@Service(interfaceClass = BizGuaranteeProvider.class)
public class BizGuaranteeProviderImpl extends BaseProviderImpl<BizGuarantee> implements BizGuaranteeProvider {

    @Autowired
    private BizGuaranteeMapper guaranteeMapper;


}
