package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizLC;
import org.ibase4j.provider.BizLCProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizLCService extends BaseService<BizLCProvider,BizLC> {
    @Reference
    public void setProvider(BizLCProvider LCProvider) {
        this.provider = LCProvider;
    }

}