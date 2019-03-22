package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysMsgConfig;
import org.ibase4j.provider.ISysMsgConfigProvider;
import org.springframework.stereotype.Service;

/**
 * 邮件模版管理
 * @author ShenHuaJie
 */
@Service
public class SysMsgConfigService extends BaseService<ISysMsgConfigProvider, SysMsgConfig> {
    @Reference
    public void setProvider(ISysMsgConfigProvider provider) {
        this.provider = provider;
    }

	public Boolean queryByMsgName(String account) {
		SysMsgConfig sysMsgConfig = provider.queryByaccount(account);
		if(sysMsgConfig != null){
			return true;
		}
		return false;
	}
}

