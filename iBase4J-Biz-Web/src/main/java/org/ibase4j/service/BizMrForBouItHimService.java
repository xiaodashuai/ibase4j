package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizMrForBouItHim;
import org.ibase4j.provider.BizMrForBouItHimProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizMrForBouItHimService extends BaseService<BizMrForBouItHimProvider,BizMrForBouItHim> {
    @Reference
    public void setProvider(BizMrForBouItHimProvider mrForBouItHimProvider) {
        this.provider = mrForBouItHimProvider;
    }

}