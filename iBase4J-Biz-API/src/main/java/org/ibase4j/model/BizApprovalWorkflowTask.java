package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：审批流程任务表
 * 
 * @author lwh
 * @date 2018/06/08
 */

@TableName("BIZ_APPROVAL_WORKFLOW_TASK")
@SuppressWarnings("serial")
public class BizApprovalWorkflowTask extends BaseModel implements Serializable {

	/**
	 * 任务id
	 */
	@TableField("TASK_ID")
	private String taskId;

	/**
	 * 审批流程ID
	 */
	@TableField("APPROVAL_ID")
	private String approvalId;

	/**
	 * 债项方案ID
	 */
	@TableField("DEBT_CODE")
	private String debtCode;

	/**
	 * 角色id
	 */
	@TableField("ROLE_ID")
	private Long roleId;

	/**
	 * 角色名称
	 */
	@TableField("ROLE_NAME")
	private String roleName;
	/**
	 * 用户id
	 */
	@TableField("USER_ID")
	private Long userId;
	/**
	 * 用户名称
	 */
	@TableField("USER_NAME")
	private String userName;

	/**
	 * 意见ID
	 */
	@TableField("COMMENT_ID")
	private String commentId;

	/**
	 * 意见内容
	 */
	@TableField("COMMENT_INFO")
	private String commentInfo;
	/**
	 * 业务表名称
	 */
	@TableField("OBJTYP")
	private String objtyp;
	/**
	 * 业务表INR
	 */
	@TableField("OBJINR")
	private String objinr;
	/**
	 * 流程实例编码
	 */
	@TableField("PROCESS_INS_ID")
	private String processInsId;
	/**
	 * 活动实例编码
	 */
	@TableField("ACTIVITY_INS_ID")
	private String activityInsId;
	/**
	 * 活动定义编码
	 */
	@TableField("ACTIVITY_DEF_ID")
	private String activityDefId;
	/**
	 * 活动名称
	 */
	@TableField("ACTIVITY_NAME")
	private String activityName;
	/**
	 * 流程名称
	 */
	@TableField("PROCESS_NAME")
	private String processName;

	public String getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProcessInsId() {
		return processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getActivityInsId() {
		return activityInsId;
	}

	public void setActivityInsId(String activityInsId) {
		this.activityInsId = activityInsId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}


	public String getActivityDefId() {
		return activityDefId;
	}

	public void setActivityDefId(String activityDefId) {
		this.activityDefId = activityDefId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentInfo() {
		return commentInfo;
	}

	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}

	public String getObjtyp() {
		return objtyp;
	}

	public void setObjtyp(String objtyp) {
		this.objtyp = objtyp;
	}

	public String getObjinr() {
		return objinr;
	}

	public void setObjinr(String objinr) {
		this.objinr = objinr;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public BizApprovalWorkflowTask() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("taskId", taskId)
				.add("approvalId", approvalId)
				.add("debtCode", debtCode)
				.add("roleId", roleId)
				.add("roleName", roleName)
				.add("userId", userId)
				.add("userName", userName)
				.add("commentId", commentId)
				.add("commentInfo", commentInfo)
				.add("objtyp", objtyp)
				.add("objinr", objinr)
				.add("processInsId", processInsId)
				.add("activityInsId", activityInsId)
				.add("activityDefId", activityDefId)
				.add("activityName", activityName)
				.add("processName", processName)
				.toString();
	}
}
