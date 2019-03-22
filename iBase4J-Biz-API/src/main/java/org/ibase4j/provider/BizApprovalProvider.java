package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizApprovalAuditValue;
import org.ibase4j.model.BizApprovalWorkflowTask;

import java.util.Map;

/**
 * @Description: 审批相关
 * @Author: dj
 * @CreateDate: 2018-08-08
 */
public interface BizApprovalProvider extends BaseProvider<BizApprovalAuditValue> {

    /** 
    * @Description: 保存债项发放审批表单 
    * @Param: [params] 
    * @return: void 
    */ 
    void submitApprovalForm(Map<String,Object> params);


    /**
     * @Description: 中断流程
     * @Param: [params]
     * @return: void
     */
    String stopProcess(Map<String,Object> params);


    /** 
    * @Description: 查询债项发放审批历史意见 
    * @Param: [param] 
    * @return: com.baomidou.mybatisplus.plugins.Page<org.ibase4j.model.BizApprovalWorkflowTask> 
    */ 
    Page<BizApprovalWorkflowTask> getHistoryCommentPage (Map<String,Object> param);

    /**
    * @Description: 根据审批流程id查询check项的值
     * @Param: [params]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String, Object> getCheakValuesByApprovalId(Map<String, Object> params);

    /**
     * 接口调用
     * @param params
     * @return
     */
    boolean invokeInf(Map<String, Object> params);
    /**
     * 修改已办状态
     * @param params
     * @return
     */
    void UpdateHaveDone(Map<String, Object> params);

}
