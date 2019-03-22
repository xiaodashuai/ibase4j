package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.matrix.api.instance.task.Task;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.mapper.BizApprovalWorkflowTaskMapper;
import org.ibase4j.mapper.BizAuditCheckMapper;
import org.ibase4j.model.BizApprovalWorkflowTask;
import org.ibase4j.model.BizAuditCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwh
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizApprovalWorkflowTask")
@Service(interfaceClass = BizApprovalWorkflowTaskProvider.class)
public class BizApprovalWorkflowTaskProviderImpl extends BaseProviderImpl<BizApprovalWorkflowTask> implements BizApprovalWorkflowTaskProvider {
	@Reference
	private ISysUserProvider sysUserProvider;
	@Autowired
	private BizProStatementProvider bizProStatementProvider;
	@Override
	public void saveApprovalWorkflowTask(Map<String, Object> params) {
		// 构造审批流程任务对象
		BizApprovalWorkflowTask bizApprovalWorkflowTask = new BizApprovalWorkflowTask();
		// 任务id
		String taskId = StringUtil.objectToString(params.get("taskId"));
		bizApprovalWorkflowTask.setTaskId(taskId);
		// 审批流程id
		bizApprovalWorkflowTask.setApprovalId(StringUtil.objectToString(params.get("approvalId")));
		// 债项方案id
		bizApprovalWorkflowTask.setDebtCode(StringUtil.objectToString(params.get("debtCode")));
		// 意见id
		bizApprovalWorkflowTask.setCommentId(StringUtil.objectToString(params.get("commentId")));
		// 意见内容
		bizApprovalWorkflowTask.setCommentInfo(StringUtil.objectToString(params.get("commentInfo")));
		// 业务表名称
		bizApprovalWorkflowTask.setObjtyp(BizContant.DEBT_GRANT_OBJTYP);
		//备注 用来标识 1：经办提交；空：其他提交。
		bizApprovalWorkflowTask.setRemark(StringUtil.objectToString(params.get("remark")));
		// 通过userId查询用户角色信息
		Long userId = Long.valueOf(params.get("userId").toString());
		Map<String, Object> userNameAndRoleName = sysUserProvider.getUserNameAndRoleName(userId);
		// 用户id
		bizApprovalWorkflowTask.setUserId(userId);
		// 用户名
		bizApprovalWorkflowTask.setUserName(userNameAndRoleName.get("userName").toString());
		// 角色id
		bizApprovalWorkflowTask.setRoleId(Long.valueOf(userNameAndRoleName.get("roleId").toString()));
		// 角色名
		bizApprovalWorkflowTask.setRoleName(userNameAndRoleName.get("roleName").toString());
		// 通过taskid查询task相关数据
		Task task = bizProStatementProvider.getTaskByTaskId(userId.toString(), taskId);
		// 流程实例编码
		bizApprovalWorkflowTask.setProcessInsId(task.getPiid());
		// 活动实例编码
		bizApprovalWorkflowTask.setActivityInsId(task.getAiid());
		// 活动定义编码
		bizApprovalWorkflowTask.setActivityDefId(task.getAdid());
		// 活动名称
		bizApprovalWorkflowTask.setActivityName(task.getActivityName());
		// 流程名称
		bizApprovalWorkflowTask.setProcessName(task.getProcessName());
		bizApprovalWorkflowTask.setEnable(1);
		// 根据实例编码获取债项发放编码
		String grantCode = bizProStatementProvider.getProcessInsBizId(params.get("userId").toString(), task.getPiid());
		// 业务表INR
		bizApprovalWorkflowTask.setObjinr(grantCode);
		update(bizApprovalWorkflowTask);
	}

	@Override
	public Page<BizApprovalWorkflowTask> getHistoryCommentPage(Map<String, Object> params) {
		Map<String, Object> param = new HashMap<>();
		param.put("enable",1);
		// 债项发放编码
		param.put("piid",StringUtil.objectToString(params.get("piid")));
		Page<BizApprovalWorkflowTask> bizApprovalWorkflowTaskPage = query(param);
		return bizApprovalWorkflowTaskPage;
	}

	@Override
	public BizApprovalWorkflowTask selectOneBizBizApproval(BizApprovalWorkflowTask bizApprovalWorkflowTask) {
		return selectOne(bizApprovalWorkflowTask);
	}
}
