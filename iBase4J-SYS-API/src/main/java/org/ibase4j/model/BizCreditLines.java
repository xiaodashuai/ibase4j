package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.util.Date;

/**
 * 描述：用户主体授信信息表
 * 
 * @author xiaoshuiquan
 * @date 2018/08/08
 */

@TableName("BIZ_CREDIT_LINES")
public class BizCreditLines extends BaseModel {

	/**
	 * 债项方案id
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 客户信息表ID
	 */
	@TableField("BIZ_CUSTOMER_ID")
	private Long customerId;
	/**
	 * 业务表名称（XXD）
	 */
	@TableField("OBJTYP")
	private String objtyp;
	/**
	 * 业务表INR(XXD的ID)
	 */
	@TableField("OBJINR")
	private String objinr;
	/**
	 * 客户编号
	 */
	@TableField("CUST_NO")
	private String 	custNo;
	/**
	 * 额度类型
	 */
	@TableField("AMOUNT_TYPE")
	private Long amountType;
	/**
	 * 授信号码
	 */
	@TableField("CREDIT_NO")
	private String 	creditNo;
	/**
	 * 授信额度名称
	 */
	@TableField("CREDIT_LINE_NAME")
	private String creditLineName;
	/**
	 * 额度总金额
	 */
	@TableField("TOTAL_AMOUNT")
	private Long totalAmount;
	/**
	 * 已用金额
	 */
	@TableField("USED_AMOUNT")
	private Long usedAmount;
	/**
	 * 可用余额
	 */
	@TableField("AVAILABLE_BALANCE")
	private Long availableBalance;
	/**
	 * 起始日期
	 */
	@TableField("START_DATE")
	private Date startDate;
	/**
	 * 到期日期
	 */
	@TableField("MATURITY_DATE")
	private Date maturityDate;

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getAmountType() {
		return amountType;
	}

	public void setAmountType(Long amountType) {
		this.amountType = amountType;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getCreditLineName() {
		return creditLineName;
	}

	public void setCreditLineName(String creditLineName) {
		this.creditLineName = creditLineName;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public Long getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(Long usedAmount) {
		this.usedAmount = usedAmount;
	}

	public Long getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Long availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
}
