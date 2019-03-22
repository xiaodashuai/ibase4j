package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizTradeCredit;
import org.ibase4j.provider.BizTradeCreditProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizTradeCreditService extends BaseService<BizTradeCreditProvider,BizTradeCredit> {
    @Reference
    public void setProvider(BizTradeCreditProvider tradeCreditProvider) {
        this.provider = tradeCreditProvider;
    }

}