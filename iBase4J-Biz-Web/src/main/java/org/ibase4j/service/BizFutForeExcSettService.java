package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizFutForeExcSett;
import org.ibase4j.provider.BizFutForeExcSettProvider;
import org.springframework.stereotype.Service;

/**
 * 保函担保
 * @author xiaoshuiquan
 * @version 2018年5月26日 下午3:47:21
 */
@Service
public class BizFutForeExcSettService extends BaseService<BizFutForeExcSettProvider,BizFutForeExcSett> {
    @Reference
    public void setProvider(BizFutForeExcSettProvider futForeExcSettProvider) {
        this.provider = futForeExcSettProvider;
    }

}