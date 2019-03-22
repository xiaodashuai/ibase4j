package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import com.matrix.api.instance.task.Task;
import com.matrix.engine.proxy.MFExecutionServiceProxy;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizProStatement;
import org.ibase4j.vo.ProstatAuditVo;

import java.util.List;
import java.util.Map;

public interface BizProStatementProvider extends BaseProvider<BizProStatement> {

    /**
     * @Description: 查询发放待办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    Map<String,Page> getGrantToDoTask(Map<String, Object> params);

    /**
     * @Description: 查询方案待办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    Map<String,Page> getSchemeToDoTask(Map<String, Object> params);

    /**
     * @Description: 查询发放已办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    Map<String,Page> getGrantHaveDoneTask(Map<String, Object> params);

    /**
     * @Description: 查询方案已办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    Map getSchemeHaveDoneTask(Map<String, Object> params);

    /** 
    * @Description: 流程运行中获得有效的流转步骤 
    * @Param: [piid, ptid, adid, userId] 
    * @return: java.util.List 
    */ 
    List getAvailableTransitions(String piid, String ptid, String adid, String userId);

    /** 
    * @Description: 根据任务id完成任务
    * @Param: [userId, taskId] 
    * @return: void 
    */ 
    void completeTaskByTaskId(String userId,String taskId);

    /** 
    * @Description: 获得前手taskid 
    * @Param: [params] 
    * @return: java.lang.String 
    */ 
    String getBeforeTaskId(Map<String, Object> params);

    /**
    * @Description: 根据选择的流转方向完成任务
    * @Param: [params]
    * @return: void
    */
    void completeTaskByTdid(Map<String, Object> params);
    
    /** 
    * @Description: 创建并启动流程实例
    * @Param: [userId, pdid] 
    * @return: void 
    */
    void createAndstartProcess(Map<String, Object> params);

    /**
    * @Description: 获取业务编码
    * @Param: [userId, piid]
    * @return: java.lang.String
    */
    String getProcessInsBizId(String userId,String piid);

    /**
    * @Description: 根据taskID查询task相关数据
    * @Param: [userId,taskId]
    * @return: com.matrix.api.instance.task.Task
    */
    Task getTaskByTaskId(String userId,String taskId);
    /**
     * @Description: 流程存储用户信息
     * @Param: [userId]
     * @return: null
     */
    MFExecutionServiceProxy signonNew(String userId);
    /* -----------------------------------以下代码暂无用start--------------------------------------------------*/

    // 创建并启动流程实例
    void updateProStatement(BizProStatement proStatement);

    // 查询待办任务的流程统计项列表
    List getTaskStatisticByProcess(String userId);

    // 根据流程定义编码查询待办任务
    Page<?> getWaitItemsByPdid(Map<String, Object> params);

    // 查询已办任务
    Page<?> queryFinished(Map<String, Object> params);

    /**
     * @Description: 根据选择的流转方向完成任务
     * @Param: [prostat]
     * @return: java.lang.String
     */
    String auditProSta(ProstatAuditVo prostat);


    /** 查询历史意见信息 **/
    List getHistoryCommentInfos(Map<String, Object> params);

    /** 获取实例详情 ***/
    Task getByTaskId(String taskId, String bizId, String userId);
    /*** 声明执行任务（锁定任务）防止其他人员执行 **/
    Task updateClaimTask(String taskId, String bizId, String userId);

    /** 流程待办申请 **/
    Page<?> queryWait(Map<String, Object> params);
    // 按流程分组查询待办任务项
    Page<?> queryAllGroupWait(Map<String, Object> params);


    // 获得待选人员列表
    List getPotentialOwners(String piid, String ptid, String adid, String userId);

    // 查询流程实例，返回所有具有管理权限的流程实例的分页对象page
    void queryProcessInses(String userId, boolean isAuthorized);

    // 调用暂停流程实例方法，暂停流程实例
    void suspendProcessIns(String userId, String piid, boolean isDeep);

    // 调用恢复流程实例方法，恢复流程实例
    void resumeProcessIns(String userId, String piid, boolean isDeep);

    // 调用终止流程实例方法，终止流程实例
    void terminateProcessIns(String userId, String piid);

    // 调用撤销工作任务的方法，撤销工作任务
    void withdrawTask(String userId, String taskId);

    // 调用回退工作任务的方法，回退工作任务至上一流程环节
    void goBackTask(String userId, String taskId);

    // 跳转流程环节
    void gotoFlowTask(String userId, String taskId, String followingAdid, String atid);

    //释放已声明执行的工作任务
    void releaseTask(String userId, String taskId);

    // 通过任务编码声明执行任务
    void claimTask(String userId, String taskId);

    //修改流程信息 完成工作任务
    void updateProcessInsVariable(String taskId, String userId, String piid, String ptid, String name, Object value);
}
