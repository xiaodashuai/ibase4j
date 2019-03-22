package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizApprovalAuditValueMapper;
import org.ibase4j.model.BizApprovalAuditValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.ibase4j.model.BizApprovalAuditValue;
import java.util.Map;

/**
 * @Description: 分行业务经办相关
 * @Author: dj
 * @CreateDate: 2018-06-12
 */
@CacheConfig(cacheNames = "bizBranchBusiSponsor")
@Service(interfaceClass=BizBranchBusiSponsorProvider.class)
public class BizBranchBusiSponsorProviderImpl extends BaseProviderImpl<BizApprovalAuditValue> implements BizBranchBusiSponsorProvider {

    @Autowired
    private BizProStatementProvider bizProStatementProvider;
    @Autowired
    private BizApprovalAuditValueProvider bizApprovalAuditValueProvider;
    @Autowired
    private BizHistoryCommentInfoProvider bizHistoryCommentInfoProvider;

    /**
     * @Description: 分行业务经办保存check表单
     * @Param: [params]
     * @return: void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitCheckForm(Map<String, Object> params) {
        // 保存check值
        bizApprovalAuditValueProvider.saveCheckValues(params);
        // 历史记录
        bizHistoryCommentInfoProvider.saveCommentInfo(params);
        // 流程流转
        Map processParams = (Map) params.get("processParams");
        processParams.put("userId",params.get("userId"));
        bizProStatementProvider.completeTaskByTdid(processParams);
    }
}