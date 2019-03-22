package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * @Description: 历史意见信息
 * @Author: dj
 * @CreateDate: 2018-06-14
 */
@TableName("BIZ_HISTORY_COMMENT_INFO")
@SuppressWarnings("serial")
public class BizHistoryCommentInfo extends BaseModel implements Serializable {

    /**
     * 任务id
     */
    @TableField("TASK_ID")
    private String taskId;

    /**
     * 审批流程ID
     */
    @TableField("APPROVALID")
    private Long approvalId;

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
     * 意见信息id
     */
    @TableField("COMMENT_ID")
    private String commentId;
    /**
     * 意见信息
     */
    @TableField("COMMENT_INFO")
    private String commentInfo;

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    public BizHistoryCommentInfo() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("taskId", taskId)
                .add("approvalId", approvalId)
                .add("roleId", roleId)
                .add("roleName", roleName)
                .add("userId", userId)
                .add("userName", userName)
                .add("commentId", commentId)
                .add("commentInfo", commentInfo)
                .toString();
    }
}
