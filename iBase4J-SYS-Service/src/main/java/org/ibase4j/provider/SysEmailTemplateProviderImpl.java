package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysEmailTemplateMapper;
import org.ibase4j.model.SysEmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 *
 */
@CacheConfig(cacheNames = "sysEmailTemplate")
@Service(interfaceClass = ISysEmailTemplateProvider.class)
public class SysEmailTemplateProviderImpl extends BaseProviderImpl<SysEmailTemplate> implements ISysEmailTemplateProvider {
	@Autowired
	SysEmailTemplateMapper sysEmailTemplateMapper;
	@Override
	public SysEmailTemplate queryByEmailConfigName(String account) {
		SysEmailTemplate sysEmailTemplate = sysEmailTemplateMapper.queryByEmailConfigName(account);
		return sysEmailTemplate;
	}

}
