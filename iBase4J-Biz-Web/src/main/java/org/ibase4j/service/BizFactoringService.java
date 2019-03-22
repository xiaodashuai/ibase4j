package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizFactoring;
import org.ibase4j.provider.BizFactoringProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizFactoringService extends BaseService<BizFactoringProvider,BizFactoring> {
    @Reference
    public void setProvider(BizFactoringProvider FactoringProvider) {
        this.provider = FactoringProvider;
    }

}