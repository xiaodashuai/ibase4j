package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.provider.ISysCacheProvider;
import org.springframework.stereotype.Service;

@Service
public class SysCacheService {
	@Reference(async = true)
	private ISysCacheProvider sysCacheProvider;

	public void flushCache() {
		sysCacheProvider.flush();
	}
}
