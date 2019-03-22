package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：check项存储
 * 
 * @author lwh
 * @date 2018/06/08
 */

@TableName("BIZ_APPROVAL_AUDIT_VALUE")
@SuppressWarnings("serial")
public class BizApprovalAuditValue extends BaseModel implements Serializable {

    /**
     * 业务种类
     */
    @TableField("CATEGORY_ID")
    private String categoryId;

    /**
     * 审批流程ID
     */
    @TableField("APPROVAL_ID")
    private String approvalId;

	/**
	 * 选项名称
	 */
	@TableField("AUDIT_NAME")
	private String auditName;

	/**
	 * 选项值
	 */
	@TableField("AUDIT_VALUE")
	private String auditValue;

    public BizApprovalAuditValue() {
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getAuditValue() {
        return auditValue;
    }

    public void setAuditValue(String auditValue) {
        this.auditValue = auditValue;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("categoryId", categoryId)
                .add("approvalId", approvalId)
                .add("auditName", auditName)
                .add("auditValue", auditValue)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizApprovalAuditValue)) return false;
        BizApprovalAuditValue that = (BizApprovalAuditValue) o;
        return Objects.equal(getCategoryId(), that.getCategoryId()) &&
                Objects.equal(getApprovalId(), that.getApprovalId()) &&
                Objects.equal(getAuditName(), that.getAuditName()) &&
                Objects.equal(getAuditValue(), that.getAuditValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCategoryId(), getApprovalId(), getAuditName(), getAuditValue());
    }
}
