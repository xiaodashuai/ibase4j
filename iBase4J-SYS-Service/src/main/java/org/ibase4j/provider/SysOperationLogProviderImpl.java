package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.model.SysOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

@CacheConfig(cacheNames = "sysOperationLog")
@Service(interfaceClass = ISysOperationLogProvider.class)
public class SysOperationLogProviderImpl extends BaseProviderImpl<SysOperationLog> implements ISysOperationLogProvider {
	@Autowired
	private ISysOperationLogProvider iSysOperationLogProvider;

}
