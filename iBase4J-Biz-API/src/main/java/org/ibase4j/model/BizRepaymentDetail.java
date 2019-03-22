/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能： 还款计划详细信息
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_REPAYMENT_DETAIL")
@SuppressWarnings("serial")
public class BizRepaymentDetail extends BaseModel implements Serializable {
	/**
	 * 计划编号
	 */
	@TableField("PLAN_ID")
	private Long planId;
	/**
	 * 还款日期
	 */
	@TableField("PAY_DATE")
	private Date payDate;
	/**
	 * 还款币种
	 */
	@TableField("PAY_CNY")
	private String payCny;
	/**
	 * 还本金额
	 */
	@TableField("PRINCIPAL_AMT")
	private BigDecimal principalAmt;
	/**
	 * 还息金额
	 */
	@TableField("INTEREST_AMT")
	private BigDecimal interestAmt;
	/**
	 * 还款期名称
	 */
	@TableField("REPAYMENT_NAME")
	private String repaymentName;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interestAmt == null) ? 0 : interestAmt.hashCode());
		result = prime * result + ((payCny == null) ? 0 : payCny.hashCode());
		result = prime * result + ((payDate == null) ? 0 : payDate.hashCode());
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
		result = prime * result + ((principalAmt == null) ? 0 : principalAmt.hashCode());
		result = prime * result + ((repaymentName == null) ? 0 : repaymentName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		BizRepaymentDetail other = (BizRepaymentDetail) obj;
		if (interestAmt == null) {
			if (other.interestAmt != null){
				return false;
			}
		} else if (!interestAmt.equals(other.interestAmt)){
			return false;
		}
		if (payCny == null) {
			if (other.payCny != null){
				return false;
			}
		} else if (!payCny.equals(other.payCny)){
			return false;
		}
		if (payDate == null) {
			if (other.payDate != null){
				return false;
			}
		} else if (!payDate.equals(other.payDate)){
			return false;
		}
		if (planId == null) {
			if (other.planId != null){
				return false;
			}
		} else if (!planId.equals(other.planId)){
			return false;
		}
		if (principalAmt == null) {
			if (other.principalAmt != null){
				return false;
			}
		} else if (!principalAmt.equals(other.principalAmt)){
			return false;
		}
		if (repaymentName == null) {
			if (other.repaymentName != null){
				return false;
			}
		} else if (!repaymentName.equals(other.repaymentName)){
			return false;
		}
		return true;
	}

	public BizRepaymentDetail() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("planId", planId)
				.add("payDate", payDate)
				.add("payCny", payCny)
				.add("principalAmt", principalAmt)
				.add("interestAmt", interestAmt)
				.add("repaymentName", repaymentName)
				.toString();
	}
}
