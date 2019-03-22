package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.support.ISysEventService;
import org.ibase4j.model.SysEvent;
import org.ibase4j.provider.ISysEventProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BizEventService extends BaseService<ISysEventProvider, SysEvent> implements ISysEventService {
	@Reference
	public void setProvider(ISysEventProvider provider) {
		this.provider = provider;
	}

	@Override
	public List<SysEvent> queryByAccount(String account) {
		
		return provider.queryByAccount(account);
	}
}
