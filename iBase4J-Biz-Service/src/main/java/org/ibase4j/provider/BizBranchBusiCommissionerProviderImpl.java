package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizApprovalAuditValueMapper;
import org.ibase4j.model.BizApprovalAuditValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description: 分行业务处长相关
 * @Author: dj
 * @CreateDate: 2018-06-12
 */
@CacheConfig(cacheNames = "bizBranchBusiCommissioner")
@Service(interfaceClass=BizBranchBusiCommissionerProvider.class)
public class BizBranchBusiCommissionerProviderImpl extends BaseProviderImpl<BizApprovalAuditValue> implements BizBranchBusiCommissionerProvider{

    @Autowired
    private BizApprovalAuditValueMapper bizApprovalAuditValueMapper;
    @Autowired
    private BizProStatementProvider bizProStatementProvider;
    @Autowired
    private BizApprovalAuditValueProvider bizApprovalAuditValueProvider;

    @Override
    public Map<String,Object> getCheckValues(Map<String,Object> params) {
        // 获得前手taskid
        String taskId = bizProStatementProvider.getBeforeTaskId(params);
        // 通过前手taskid获得check值
        Map<String, Object> checkValues = bizApprovalAuditValueProvider.getCheckValuesByTaskId(taskId);
        return checkValues;
    }
}
