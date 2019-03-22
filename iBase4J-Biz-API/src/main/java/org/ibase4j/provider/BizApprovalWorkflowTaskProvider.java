package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizApprovalWorkflowTask;
import org.ibase4j.model.BizDebtGrant;

import java.util.Map;

/**
 *
 * @author lwh
 * @date 2018/06/08
 */
public interface BizApprovalWorkflowTaskProvider extends BaseProvider<BizApprovalWorkflowTask> {
	
    /** 
    * @Description: 保存审批流程任务对象
    * @Param: [params] 
    * @return: void 
    */ 
    void saveApprovalWorkflowTask(Map<String,Object> params);

    /**
    * @Description: 查询债项发放审批历史意见
    * @Param: [param]
    * @return: com.baomidou.mybatisplus.plugins.Page<org.ibase4j.model.BizApprovalWorkflowTask>
    */
    Page<BizApprovalWorkflowTask> getHistoryCommentPage (Map<String,Object> param);

    /**
     * @Description: 条件查询单条方案记录
     * @Param: [BizApprovalWorkflowTask]
     * @return: org.ibase4j.model.BizApprovalWorkflowTask
     */
    BizApprovalWorkflowTask selectOneBizBizApproval(BizApprovalWorkflowTask bizApprovalWorkflowTask);
}
