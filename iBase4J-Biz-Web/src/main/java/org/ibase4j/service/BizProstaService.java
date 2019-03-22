
/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.matrix.api.instance.task.Task;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizProStatement;
import org.ibase4j.provider.BizProStatementProvider;
import org.ibase4j.vo.ProstatAuditVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BizProstaService extends BaseService<BizProStatementProvider,BizProStatement> {
	@Reference
	public void setBizLeaveProvider(BizProStatementProvider provider) {
		this.provider = provider;
	}


	/** 
	* @Description: 查询待办
	* @Param: [params] 
	* @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page> 
	*/ 
	public Map<String,Page> getToDoTask(Map<String, Object> params) {
		return provider.getGrantToDoTask(params);
	}

    /**
     * @Description: 查询已办
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,com.baomidou.mybatisplus.plugins.Page>
     */
    public Map<String,Page> getHaveDoneTask(Map<String, Object> params) {
        return provider.getGrantHaveDoneTask(params);
    }

	/**
	 * @Description: 获得待选步骤
	 * @Param: [piid, ptid, adid, userId]
	 * @return: java.util.List
	 */
	public List getAvailableTransitions(String piid, String ptid, String adid, String userId){
		return provider.getAvailableTransitions(piid,ptid,adid,userId);
	}

	/**
	 * 根据选择的流转方向完成任务
	 * @param prostat
	 * @return
	 */
	public String auditProstat(ProstatAuditVo prostat){
		return provider.auditProSta(prostat);
	}
    /* -----------------------------------以下代码暂无用start--------------------------------------------------*/
	/**
	 * 创建并启动流程实例
	 *
	 * @param proStatement
	 * @return
	 */
	public void updateProStatement(BizProStatement proStatement) {
		provider.updateProStatement(proStatement);;
	}

	/**
	 * @Description: 查询待办任务的流程统计项列表
	 * @Param: []
	 * @return: java.util.List
	 */
	public List getTaskStatisticByProcess(String userId){
		return provider.getTaskStatisticByProcess(userId);
	}

	/**
	 * @Description: 根据流程定义编码查询待办任务
	 * @Param: []
	 * @return: com.baomidou.mybatisplus.plugins.Page<?>
	 */
	public Page<?> getWaitItemsByPdid(Map<String, Object> params){
		return provider.getWaitItemsByPdid(params);
	}

	/**
	 * 查询已办任务
	 *
	 * @param params
	 * @return
	 */
	public Page<?> queryFinished(Map<String, Object> params) {
		return provider.queryFinished(params);
	}

	/**
	 * 待办流程申请
	 * 
	 * @param params
	 * @return
	 */
	public Page<?> queryWait(Map<String, Object> params) {
		return provider.queryWait(params);
	}


	
	/**
	 * 功能：获取一个待办的执行任务
	 * @param taskId
	 * @param bizId
	 * @param userId
	 * @return
	 */
	public Task getByTaskId(String taskId,String bizId,String userId){
		return provider.getByTaskId(taskId, bizId, userId);
	}
	
	/**
	 * 功能：锁定待办任务，防止其他人员执行
	 * @param taskId
	 * @param bizId
	 * @param userId
	 * @return
	 */
	public Task getClaimTask(String taskId,String bizId,String userId){
		return provider.updateClaimTask(taskId, bizId, userId);
	}
	
	/**
	* @Description: 查询历史意见信息
	* @Param: [params]
	* @return: java.util.List
	*/
	public List getHistoryCommentInfos(Map<String, Object> params){
		return provider.getHistoryCommentInfos(params);
	}

	/**
	 * 按组查询代办
	 * @param params
	 * @return
	 */
	public Page<?> queryAllGroupWait(Map<String, Object> params){
		return provider.queryAllGroupWait(params);
	}


	// 获得待选人员列表
	public List getPotentialOwners(String piid, String ptid, String adid, String userId){
		return provider.getPotentialOwners(piid,ptid,adid,userId);
	}

	// 查询流程实例，返回所有具有管理权限的流程实例的分页对象page
	public void queryProcessInses(String userId, boolean isAuthorized){}

	// 调用暂停流程实例方法，暂停流程实例
	public void suspendProcessIns(String userId, String piid, boolean isDeep){}

	// 调用恢复流程实例方法，恢复流程实例
	public void resumeProcessIns(String userId, String piid, boolean isDeep){}

	// 调用终止流程实例方法，终止流程实例
	public void terminateProcessIns(String userId, String piid){}

	// 调用撤销工作任务的方法，撤销工作任务
	public void withdrawTask(String userId, String taskId){}

	// 调用回退工作任务的方法，回退工作任务至上一流程环节
	public void goBackTask(String userId, String taskId){}

	// 跳转流程环节
	public void gotoFlowTask(String userId, String taskId, String followingAdid, String atid){}

	//释放已声明执行的工作任务
	public void releaseTask(String userId, String taskId){}

	// 通过任务编码声明执行任务
	public void claimTask(String userId, String taskId){}

	//修改流程信息 完成工作任务
	public void updateProcessInsVariable(String taskId, String userId, String piid, String ptid, String name, Object value){}
    /* -----------------------------------以下代码暂无用end--------------------------------------------------*/

    public String getProcessInsBizId(String userId,String piid){
    	return provider.getProcessInsBizId(userId,piid);
	};
}
