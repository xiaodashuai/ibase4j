/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizRepaymentDetail)) return false;
		BizRepaymentDetail that = (BizRepaymentDetail) o;
		return Objects.equal(planId, that.planId) &&
				Objects.equal(payDate, that.payDate) &&
				Objects.equal(payCny, that.payCny) &&
				Objects.equal(principalAmt, that.principalAmt) &&
				Objects.equal(interestAmt, that.interestAmt) &&
				Objects.equal(repaymentName, that.repaymentName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(planId, payDate, payCny, principalAmt, interestAmt, repaymentName);
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
