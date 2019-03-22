package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizTF;
import org.ibase4j.provider.BizTFProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizTFService extends BaseService<BizTFProvider,BizTF> {
    @Reference
    public void setProvider(BizTFProvider TFProvider) {
        this.provider = TFProvider;
    }

}