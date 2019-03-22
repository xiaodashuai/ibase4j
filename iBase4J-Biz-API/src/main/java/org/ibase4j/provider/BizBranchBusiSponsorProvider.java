package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizApprovalAuditValue;

import java.util.Map;

/**
 * @Description: 分行业务经办相关
 * @Author: dj
 * @CreateDate: 2018-06-12
 */
public interface BizBranchBusiSponsorProvider extends BaseProvider<BizApprovalAuditValue> {
    /**
     * @Description: 分行业务经办提交check表单并进行业务流转
     * @Param: [params]
     * @return: void
     */
    void submitCheckForm(Map<String,Object> params);
}
