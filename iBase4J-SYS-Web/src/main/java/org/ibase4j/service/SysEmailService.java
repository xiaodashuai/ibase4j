package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysEmail;
import org.ibase4j.provider.ISysEmailProvider;
import org.springframework.stereotype.Service;

/**
 * 邮件管理
 * @author ShenHuaJie
 */
@Service
public class SysEmailService extends BaseService<ISysEmailProvider, SysEmail> {
	@Reference
	public void setProvider(ISysEmailProvider provider) {
		this.provider = provider;
	}
}
