package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizApprovalAuditValue;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分行业务处长相关
 * @Author: dj
 * @CreateDate: 2018-06-12
 */
public interface BizBranchBusiCommissionerProvider extends BaseProvider<BizApprovalAuditValue> {

    /**
    * @Description: 获得分行业务待办提交的check值
    * @Param: [params]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String,Object> getCheckValues(Map<String,Object> params);
}
