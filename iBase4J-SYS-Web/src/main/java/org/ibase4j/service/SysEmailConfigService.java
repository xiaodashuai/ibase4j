package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysEmailConfig;
import org.ibase4j.provider.ISysEmailConfigProvider;
import org.springframework.stereotype.Service;

/**
 * 邮件配置管理
 * @author ShenHuaJie
 */
@Service
public class SysEmailConfigService extends BaseService<ISysEmailConfigProvider, SysEmailConfig> {
	@Reference
	public void setProvider(ISysEmailConfigProvider provider) {
		this.provider = provider;
	}
}
