package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.BizRepaymentRec;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 还款记录表
 * @author hannasong
 */
@CacheConfig(cacheNames = "BizRepaymentRec")
@Service(interfaceClass = BizRepaymentRecProvider.class)
public class BizRepaymentRecProviderImpl extends BaseProviderImpl<BizRepaymentRec> implements BizRepaymentRecProvider {

	
}
