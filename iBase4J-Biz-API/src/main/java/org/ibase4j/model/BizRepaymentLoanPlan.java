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

/**
 * 功能：还息计划表
 * 
 * @author lwh
 * 日期：2018/7/6
 */
@TableName("BIZ_REPAYMENT_LOAN_PLAN")
@SuppressWarnings("serial")
public class BizRepaymentLoanPlan extends BaseModel implements Serializable {
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
	 * 计划还息日
	 */
	@TableField("INTEREST_DATE")
	private Date interestDate;

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

	public Date getInterestDate() {
		return interestDate;
	}

	public void setInterestDate(Date interestDate) {
		this.interestDate = interestDate;
	}

	public BizRepaymentLoanPlan() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("grantCode", grantCode)
				.add("interestDate", interestDate)
				.toString();
	}
}
