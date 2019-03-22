package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizProductLinesTypeMapper;
import org.ibase4j.model.BizProductLinesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/05/26
 */
@CacheConfig(cacheNames = "bizProductLinesType")
@Service(interfaceClass = BizProductLinesTypeProvider.class)
public class BizProductLinesTypeProviderImpl extends BaseProviderImpl<BizProductLinesType> implements BizProductLinesTypeProvider {

    @Autowired
    private BizProductLinesTypeMapper productLinesTypeMapper;


}
