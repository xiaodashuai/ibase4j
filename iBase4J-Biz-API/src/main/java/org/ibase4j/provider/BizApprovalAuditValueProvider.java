package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizApprovalAuditValue;

import java.util.Map;

/**
 *
 * @author lwh
 * @date 2018/06/08
 */
public interface BizApprovalAuditValueProvider extends BaseProvider<BizApprovalAuditValue> {
	
    /** 
    * @Description: 保存check值
    * @Param: [params] 
    * @return: void 
    */ 
    void saveCheckValues(Map<String,Object> params);

    /**
    * @Description: 根据审批流程id查询check项的值
    * @Param: [params]
    * @return: java.util.Map<java.lang.String,java.lang.Object>
    */
    Map<String,Object> getCheakValuesByApprovalId(Map<String,Object> params);
    
    /**
    * @Description: 分行业务处长根据前手taskID查询check值
    * @Param: [taskId]
    * @return: java.util.Map<java.lang.String,org.omg.CORBA.Object>
    */
    Map<String,Object> getCheckValuesByTaskId(String taskId);

    /**
     * @Description: 已办详情
     * @param params
     * @return
     */
    Map<String,Object> getCheckValuesDetails(Map<String,Object> params);
}
