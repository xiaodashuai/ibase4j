package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysChargeTypeMapper;
import org.ibase4j.model.SysChargeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author XiaoYu
 *
 */
@CacheConfig(cacheNames = "sysChargeType")
@Service(interfaceClass = ISysChargeTypeProvider.class)
public class SysChargeTypeProviderImpl extends BaseProviderImpl<SysChargeType> implements ISysChargeTypeProvider {
	@Autowired
	private SysChargeTypeMapper sysChargeTypeMapper;
	
}
