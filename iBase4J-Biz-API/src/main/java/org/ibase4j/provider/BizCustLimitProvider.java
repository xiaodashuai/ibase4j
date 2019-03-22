package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCustLimit;

import java.util.List;
import java.util.Map;

/**
 * 功能：额度占用表
 * @author hannasong
 */
public interface BizCustLimitProvider extends BaseProvider<BizCustLimit> {

    /**
     * @Description: 获取客户额度占用信息
     * @Param: grantCode
     * @return: java.util.List
     */
    List<BizCustLimit> getCustLimit(Map<String, Object> params);
}
