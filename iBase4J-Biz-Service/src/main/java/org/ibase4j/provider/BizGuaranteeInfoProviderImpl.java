package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizGuaranteeInfoMapper;
import org.ibase4j.model.BizGuaranteeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizGuaranteeInfo")
@Service(interfaceClass = BizGuaranteeInfoProvider.class)
public class BizGuaranteeInfoProviderImpl extends BaseProviderImpl<BizGuaranteeInfo> implements BizGuaranteeInfoProvider {

    @Autowired
    private BizGuaranteeInfoMapper guaranteeInfoMapper;


}
