package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysOperationLog;
import org.ibase4j.provider.ISysOperationLogProvider;
import org.springframework.stereotype.Service;

@Service
public class SysOperationLogService extends BaseService<ISysOperationLogProvider, SysOperationLog>{
	@Reference
	public void setProvider(ISysOperationLogProvider provider) {
		this.provider = provider;
	}


}
