package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizInterfaceResultMapper;
import org.ibase4j.model.BizInterfaceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.Map;

/**
 * @Description: 接口返回结果
 * @Author: dj
 * @CreateDate: 2018-12-13
 */
@CacheConfig(cacheNames = "bizInterfaceResult")
@Service(interfaceClass = BizInterfaceResultProvider.class)
public class BizInterfaceResultProviderImpl extends BaseProviderImpl<BizInterfaceResult> implements BizInterfaceResultProvider{

    @Autowired
    private BizInterfaceResultMapper bizInterfaceResultMapper;

    @Override
    public Map getInterfaceResponseStatus(Map<String, Object> params) {
        return bizInterfaceResultMapper.getInterfaceResponseStatus(params);
    }
}
