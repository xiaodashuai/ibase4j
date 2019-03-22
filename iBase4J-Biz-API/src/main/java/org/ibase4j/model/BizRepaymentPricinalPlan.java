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
 * 功能：还本计划表
 * 
 * @author lwh
 * 日期：2018/7/6
 */
@TableName("BIZ_REPAYMENT_PRICIPAL_PLAN")
@SuppressWarnings("serial")
public class BizRepaymentPricinalPlan extends BaseModel implements Serializable {
	/**
	 * 业务编号
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 发放债项申请编号
	 */
	@TableField("GRANT_CODE")
	private String grantCode;
	/**
	 * 还款日期
	 */
	@TableField("PAY_DATE")
	private Date payDate;
	/**
	 * 还款金额
	 */
	@TableField("PRINCIPAL_AMT")
	private BigDecimal principalAmy;
	/**
	 * 还款币种
	 */
	@TableField("PAY_CNY")
	private String payCny;

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getPrincipalAmy() {
		return principalAmy;
	}

	public void setPrincipalAmy(BigDecimal principalAmy) {
		this.principalAmy = principalAmy;
	}

	public String getPayCny() {
		return payCny;
	}

	public void setPayCny(String payCny) {
		this.payCny = payCny;
	}

	public BizRepaymentPricinalPlan() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("grantCode", grantCode)
				.add("payDate", payDate)
				.add("principalAmy", principalAmy)
				.add("payCny", payCny)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizRepaymentPricinalPlan)) return false;
		BizRepaymentPricinalPlan that = (BizRepaymentPricinalPlan) o;
		return Objects.equal(getDebtCode(), that.getDebtCode()) &&
				Objects.equal(getGrantCode(), that.getGrantCode()) &&
				Objects.equal(getPayDate(), that.getPayDate()) &&
				Objects.equal(getPrincipalAmy(), that.getPrincipalAmy()) &&
				Objects.equal(getPayCny(), that.getPayCny());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDebtCode(), getGrantCode(), getPayDate(), getPrincipalAmy(), getPayCny());
	}
}
