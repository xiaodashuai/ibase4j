package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizTradeCreditMapper;
import org.ibase4j.model.BizTradeCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/06/20
 */
@CacheConfig(cacheNames = "bizTradeCredit")
@Service(interfaceClass = BizTradeCreditProvider.class)
public class BizTradeCreditProviderImpl extends BaseProviderImpl<BizTradeCredit> implements BizTradeCreditProvider {

    @Autowired
    private BizTradeCreditMapper tradeCreditMapper;


}
