package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：检查计划配置
 * 
 * @author xy
 * @date 2018/05/08
 */

@TableName("BIZ_CATEGORY_AUDIT")
@SuppressWarnings("serial")
public class BizCategoryAudit extends BaseModel implements Serializable {

	/**
	 * 排序号
	 */
	@TableField("AUDIT_ID")
	private Long auditId;
	/**
	 * 排序号
	 */
	@TableField("CATEGORY_ID")
	private Long categoryId;
	/**
	 * 排序号
	 */
	@TableField("SORT_NO")
	private Long sortNo;
	/**
	 * 评审点名称
	 */
	@TableField("CODE_")
	private String code;
	/**
	 * 评审点名称
	 */
	@TableField("CODE_TEXT")
	private String codeText;

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	public BizCategoryAudit() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("auditId", auditId)
				.add("categoryId", categoryId)
				.add("sortNo", sortNo)
				.add("code", code)
				.add("codeText", codeText)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizCategoryAudit)) return false;
		BizCategoryAudit that = (BizCategoryAudit) o;
		return Objects.equal(getAuditId(), that.getAuditId()) &&
				Objects.equal(getCategoryId(), that.getCategoryId()) &&
				Objects.equal(getSortNo(), that.getSortNo()) &&
				Objects.equal(getCode(), that.getCode()) &&
				Objects.equal(getCodeText(), that.getCodeText());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getAuditId(), getCategoryId(), getSortNo(), getCode(), getCodeText());
	}
}
