package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysMsgConfigMapper;
import org.ibase4j.model.SysMsgConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 *
 */
@CacheConfig(cacheNames = "sysMsgConfig")
@Service(interfaceClass = ISysMsgConfigProvider.class)
public class SysMsgConfigProviderImpl extends BaseProviderImpl<SysMsgConfig> implements ISysMsgConfigProvider {
	@Autowired
	SysMsgConfigMapper sysMsgConfigMapper;
	@Override
	public SysMsgConfig queryByaccount(String account) {
		SysMsgConfig sysMsgConfig = sysMsgConfigMapper.queryByAccount(account);
		return sysMsgConfig;
	}

}
