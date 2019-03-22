package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizTheRentFactoring;
import org.ibase4j.provider.BizTheRentFactoringProvider;
import org.springframework.stereotype.Service;

/**
 * 结算类（信用证）
 * @author xiaoshuiquan
 * @version 2018年6月20日
 */
@Service
public class BizTheRentFactoringService extends BaseService<BizTheRentFactoringProvider,BizTheRentFactoring> {
    @Reference
    public void setProvider(BizTheRentFactoringProvider theRentFactoringProvider) {
        this.provider = theRentFactoringProvider;
    }

}