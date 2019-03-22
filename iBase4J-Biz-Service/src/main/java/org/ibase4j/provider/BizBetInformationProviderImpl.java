package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.BizBetInformation;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author xiaoshuiquan
 * @date 2018/08/26
 */
@CacheConfig(cacheNames = "bizBetInformation")
@Service(interfaceClass = BizBetInformationProvider.class)
public class BizBetInformationProviderImpl extends BaseProviderImpl<BizBetInformation> implements BizBetInformationProvider {
	private static final Logger log = LogManager.getLogger(BizBetInformationProviderImpl.class);





}
