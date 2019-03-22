package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：检查计划配置
 * 
 * @author xy
 * @date 2018/05/08
 */

@TableName("BIZ_AUDIT_CHECK")
@SuppressWarnings("serial")
public class BizAuditCheck extends BaseModel implements Serializable {
	/**
	 * 评审点名称
	 */
	@TableField("AUDIT_NAME")
	private String auditNmae;
	/**
	 * 排序号
	 */
	@TableField("SORT_NO")
	private Long sortNo;

	public String getAuditNmae() {
		return auditNmae;
	}

	public void setAuditNmae(String auditNmae) {
		this.auditNmae = auditNmae;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public BizAuditCheck() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("auditNmae", auditNmae)
				.add("sortNo", sortNo)
				.toString();
	}
}
