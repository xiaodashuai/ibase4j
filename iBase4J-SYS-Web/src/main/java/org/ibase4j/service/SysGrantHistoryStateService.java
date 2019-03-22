package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;

import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.provider.BizDebtGrantProvider;
import org.springframework.stereotype.Service;

@Service
public class SysGrantHistoryStateService extends BaseService<BizDebtGrantProvider,BizDebtGrant> {
    @Reference
    public void setProvider(BizDebtGrantProvider provider) {
        this.provider = provider;
    }
    
    public Page<?> queryPage(Map<String,Object> params){
    	Page<BizDebtGrant> page = provider.query(params);
    	return page;
    }
}
