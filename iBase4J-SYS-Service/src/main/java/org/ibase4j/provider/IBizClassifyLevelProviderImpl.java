package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizClassifyLevelMapper;
import org.ibase4j.model.BizClassifyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "bizClassifyLevel")
@Service(interfaceClass = IBizClassifyLevelProvider.class)
public class IBizClassifyLevelProviderImpl extends BaseProviderImpl<BizClassifyLevel> implements IBizClassifyLevelProvider {
	@Autowired
	private BizClassifyLevelMapper bizClassifyLevelMapper;
	
}
