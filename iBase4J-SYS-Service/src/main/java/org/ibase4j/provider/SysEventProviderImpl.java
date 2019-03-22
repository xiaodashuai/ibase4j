package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysEventMapper;
import org.ibase4j.model.SysEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "sysEvent")
@Service(interfaceClass = ISysEventProvider.class)
public class SysEventProviderImpl extends BaseProviderImpl<SysEvent> implements ISysEventProvider {
	@Autowired
	private ISysUserProvider sysUserProvider;
	@Autowired
	private	SysEventMapper sysEventMapper;

	@Override
    public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
		Page<Map<String, Object>> page = super.queryMap(params);
		for (Map<String, Object> map : page.getRecords()) {
			Long createBy = (Long) map.get("createBy");
			if (createBy != null) {
				map.put("userName", sysUserProvider.queryById(createBy).getUserName());
			}
		}
		return page;
	}

	@Override
	public List<SysEvent> queryByAccount(String account) {
		return sysEventMapper.queryByAccount(account);
	}
}
