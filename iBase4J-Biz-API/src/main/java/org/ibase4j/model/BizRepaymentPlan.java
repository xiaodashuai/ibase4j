/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 功能：还款计划
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_REPAYMENT_PLAN")
@SuppressWarnings("serial")
public class BizRepaymentPlan extends BaseModel implements Serializable {
	/**
	 * 业务编号
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 产品种类（二级分类）
	 */
	@TableField("BUSINESS_TYPES")
	private String businessTypes;
	/**
	 * 计划名称
	 */
	@TableField("PLAN_NAME")
	private String planName;
	/**
	 * 发放债项申请编号
	 */
	@TableField("GRANT_CODE")
	private String grantCode;
	/**
	 * 租金保理外键
	 */
	@TableField("RF_ID")
	private Long rfId;
	/**
	 * 放款编号
	 */
	@TableField("LOAN_ID")
	private Long loanId;
	/**
	 * 首次还本日
	 */
	@TableField("FIRST_REPAY_DAY")
	private Date firstRepayDay;
	/**
	 * 末次还款日
	 */
	@TableField("LAST_REPAYMENT_DAY")
	private Date lastRepaymentDay;

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
	 * 还款金额
	 */
	@TableField("PRINCIPAL_AMT")
	private Long principalAmy;

	/**
	 * 计划还息日
	 */
	@TableField("INTEREST_DATE")
	private Date interestDate;
	/**
	 * 还本方式
	 */
	@TableField("REPAYMENT_COST_MODE")
	private Long repaymentCostMode;
	/**
	 * 首次还息日
	 */
	@TableField("FIRST_DAY")
	private Date firstDay;
	/**
	 * 利息计收方式
	 */
	@TableField("CACL_WAY")
	private Long caclWay;

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(String businessTypes) {
		this.businessTypes = businessTypes;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public Long getRfId() {
		return rfId;
	}

	public void setRfId(Long rfId) {
		this.rfId = rfId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Date getFirstRepayDay() {
		return firstRepayDay;
	}

	public void setFirstRepayDay(Date firstRepayDay) {
		this.firstRepayDay = firstRepayDay;
	}

	public Date getLastRepaymentDay() {
		return lastRepaymentDay;
	}

	public void setLastRepaymentDay(Date lastRepaymentDay) {
		this.lastRepaymentDay = lastRepaymentDay;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayCny() {
		return payCny;
	}

	public void setPayCny(String payCny) {
		this.payCny = payCny;
	}

	public Long getPrincipalAmy() {
		return principalAmy;
	}

	public void setPrincipalAmy(Long principalAmy) {
		this.principalAmy = principalAmy;
	}

	public Date getInterestDate() {
		return interestDate;
	}

	public void setInterestDate(Date interestDate) {
		this.interestDate = interestDate;
	}

	public Long getRepaymentCostMode() {
		return repaymentCostMode;
	}

	public void setRepaymentCostMode(Long repaymentCostMode) {
		this.repaymentCostMode = repaymentCostMode;
	}

	public Date getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	public Long getCaclWay() {
		return caclWay;
	}

	public void setCaclWay(Long caclWay) {
		this.caclWay = caclWay;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (o == null || getClass() != o.getClass()){ return false;}
		BizRepaymentPlan that = (BizRepaymentPlan) o;
		return Objects.equals(debtCode, that.debtCode) &&
				Objects.equals(businessTypes, that.businessTypes) &&
				Objects.equals(planName, that.planName) &&
				Objects.equals(grantCode, that.grantCode) &&
				Objects.equals(rfId, that.rfId) &&
				Objects.equals(loanId, that.loanId) &&
				Objects.equals(firstRepayDay, that.firstRepayDay) &&
				Objects.equals(lastRepaymentDay, that.lastRepaymentDay) &&
				Objects.equals(payDate, that.payDate) &&
				Objects.equals(payCny, that.payCny) &&
				Objects.equals(principalAmy, that.principalAmy) &&
				Objects.equals(interestDate, that.interestDate) &&
				Objects.equals(repaymentCostMode, that.repaymentCostMode) &&
				Objects.equals(firstDay, that.firstDay) &&
				Objects.equals(caclWay, that.caclWay);
	}

	@Override
	public int hashCode() {

		return Objects.hash(debtCode, businessTypes, planName, grantCode, rfId, loanId, firstRepayDay, lastRepaymentDay, payDate, payCny, principalAmy, interestDate, repaymentCostMode, firstDay, caclWay);
	}

	public BizRepaymentPlan() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("planName", planName)
				.add("grantCode", grantCode)
				.add("rfId", rfId)
				.add("loanId", loanId)
				.add("firstRepayDay", firstRepayDay)
				.add("lastRepaymentDay", lastRepaymentDay)
				.add("payDate", payDate)
				.add("payCny", payCny)
				.add("principalAmy", principalAmy)
				.add("interestDate", interestDate)
				.add("repaymentCostMode", repaymentCostMode)
				.add("firstDay", firstDay)
				.add("caclWay", caclWay)
				.toString();
	}
}
