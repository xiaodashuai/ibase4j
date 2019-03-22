package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.BizDebtSummary;
import org.ibase4j.provider.BizDebtSummaryProvider;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysDebtHistoryStateService extends BaseService<BizDebtSummaryProvider, BizDebtSummary> {
    @Reference
    public void setProvider(BizDebtSummaryProvider provider) {
        this.provider = provider;
    }
    public Page<?> queryPage(Map<String,Object> params){
        Page<BizDebtSummary> page = provider.query(params);
        return page;
    }
}
