package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.BizPTS;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author Xiaoshuiquan
 * @date 2018/09/06
 */
@CacheConfig(cacheNames = "bizPTS")
@Service(interfaceClass = BizPTSProvider.class)
public class BizPTSProviderImpl extends BaseProviderImpl<BizPTS> implements BizPTSProvider {

	
}
