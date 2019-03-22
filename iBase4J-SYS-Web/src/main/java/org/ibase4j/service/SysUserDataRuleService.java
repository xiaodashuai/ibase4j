package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysUserDataRule;
import org.ibase4j.provider.ISysUserDataRuleProvider;
import org.springframework.stereotype.Service;

@Service
public class SysUserDataRuleService extends BaseService<ISysUserDataRuleProvider, SysUserDataRule> {
    @Reference
    public void setProvider(ISysUserDataRuleProvider provider) {
        this.provider = provider;
    }
}
