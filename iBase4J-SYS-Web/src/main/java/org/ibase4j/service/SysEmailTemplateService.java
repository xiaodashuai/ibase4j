package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysEmailTemplate;
import org.ibase4j.provider.ISysEmailTemplateProvider;
import org.springframework.stereotype.Service;

/**
 * 邮件模版管理
 * @author ShenHuaJie
 */
@Service
public class SysEmailTemplateService extends BaseService<ISysEmailTemplateProvider, SysEmailTemplate> {
	@Reference
	public void setProvider(ISysEmailTemplateProvider provider) {
		this.provider = provider;
	}

	public Boolean queryByEmailName(String account) {
		SysEmailTemplate sysEmailTemplate = provider.queryByEmailConfigName(account);
		if(sysEmailTemplate != null){
			return true;
		}
		return false;
	}
}
