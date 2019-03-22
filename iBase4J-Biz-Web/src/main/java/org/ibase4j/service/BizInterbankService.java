package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizInterbank;
import org.ibase4j.provider.BizInterbankProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizInterbankService extends BaseService<BizInterbankProvider,BizInterbank> {
    @Reference
    public void setProvider(BizInterbankProvider InterbankProvider) {
        this.provider = InterbankProvider;
    }

}