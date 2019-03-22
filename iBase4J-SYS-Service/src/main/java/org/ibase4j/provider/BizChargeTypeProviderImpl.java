/**
 * 
 */
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizChargeTypeMapper;
import org.ibase4j.model.BizChargeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 功能：收费类型
 * 日期：2018/7/17
 * @author czm 
 * @version 1.0
 */
@CacheConfig(cacheNames = "BizChargeType")
@Service(interfaceClass = BizChargeTypeProvider.class)
public class BizChargeTypeProviderImpl extends BaseProviderImpl<BizChargeType> implements BizChargeTypeProvider {
	private static final Logger log = LogManager.getLogger(BizChargeTypeProviderImpl.class);
	@Autowired
	private BizChargeTypeMapper bizChargeTypeMapper;
}
