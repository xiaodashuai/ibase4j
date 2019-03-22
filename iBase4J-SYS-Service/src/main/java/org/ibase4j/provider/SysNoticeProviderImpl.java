package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysNoticeMapper;
import org.ibase4j.model.SysNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

/**
 * @author ShenHuaJie
 *
 */
@CacheConfig(cacheNames = "sysNoticeTemplate")
@Service(interfaceClass = ISysNoticeProvider.class)
public class SysNoticeProviderImpl extends BaseProviderImpl<SysNotice> implements ISysNoticeProvider {
	@Autowired
	SysNoticeMapper sysNoticeMapper;
	@Override
	public SysNotice queryByNoticeTitle(String account) {
		SysNotice sysNotice = sysNoticeMapper.queryByNoticeTitle(account);
		return sysNotice;
	}

}
