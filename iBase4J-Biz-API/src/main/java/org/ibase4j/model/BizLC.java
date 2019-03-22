package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：结算类（信用证）
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_LETTER_OF_CREDIT")
public class BizLC extends BaseModel implements Serializable {

	/**
	 * 业务编号
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 产品种类
	 */
	@TableField("BUSINESS_TYPES")
	private String businessType;
	/**
	 * 申请人
	 */
	@TableField("THE_APPLICANT")
	private String theApplicant;
	/**
	 * 开立方式(1.直开;2.转开;3代开)
	 */
	@TableField("OPEN_WAY")
	private Long openWay;
	/**
	 * 溢短装范围
	 */
	@TableField("SPILLAGE_RANGE")
	private String spillageRange;
	/**
	 * 付款期限
	 */
	@TableField("PAYMENT_TERM")
	private Long paymentTerm;
	/**
	 * 远期付款
	 */
	@TableField("THE_FORWARD")
	private Long theForwaro;
	/**
	 * 受益人名称
	 */
	@TableField("BENEFICIARY_NAME")
	private String beneficiaryName;
	/**
	 * 受益人国别
	 */
	@TableField("BENEFICIARY_COUNTRY")
	private String beneficiaryCountry;
	/**
	 * 贸易方式(1.货物贸易;2.服务贸易)
	 */
	@TableField("TRADE_WAY")
	private Long tradrWay;
		/**
	 * 商务合同编号
	 */
	@TableField("BNS_CONT_NO")
	private String bneContNo;

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTheApplicant() {
		return theApplicant;
	}

	public void setTheApplicant(String theApplicant) {
		this.theApplicant = theApplicant;
	}

	public Long getOpenWay() {
		return openWay;
	}

	public void setOpenWay(Long openWay) {
		this.openWay = openWay;
	}

	public String getSpillageRange() {
		return spillageRange;
	}

	public void setSpillageRange(String spillageRange) {
		this.spillageRange = spillageRange;
	}

	public Long getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(Long paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public Long getTheForwaro() {
		return theForwaro;
	}

	public void setTheForwaro(Long theForwaro) {
		this.theForwaro = theForwaro;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}

	public Long getTradrWay() {
		return tradrWay;
	}

	public void setTradrWay(Long tradrWay) {
		this.tradrWay = tradrWay;
	}

	public String getBneContNo() {
		return bneContNo;
	}

	public void setBneContNo(String bneContNo) {
		this.bneContNo = bneContNo;
	}

	public BizLC() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessType", businessType)
				.add("theApplicant", theApplicant)
				.add("openWay", openWay)
				.add("spillageRange", spillageRange)
				.add("paymentTerm", paymentTerm)
				.add("theForwaro", theForwaro)
				.add("beneficiaryName", beneficiaryName)
				.add("beneficiaryCountry", beneficiaryCountry)
				.add("tradrWay", tradrWay)
				.add("bneContNo", bneContNo)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizLC)) return false;
		BizLC bizLC = (BizLC) o;
		return Objects.equal(getDebtCode(), bizLC.getDebtCode()) &&
				Objects.equal(getBusinessType(), bizLC.getBusinessType()) &&
				Objects.equal(getTheApplicant(), bizLC.getTheApplicant()) &&
				Objects.equal(getOpenWay(), bizLC.getOpenWay()) &&
				Objects.equal(getSpillageRange(), bizLC.getSpillageRange()) &&
				Objects.equal(getPaymentTerm(), bizLC.getPaymentTerm()) &&
				Objects.equal(getTheForwaro(), bizLC.getTheForwaro()) &&
				Objects.equal(getBeneficiaryName(), bizLC.getBeneficiaryName()) &&
				Objects.equal(getBeneficiaryCountry(), bizLC.getBeneficiaryCountry()) &&
				Objects.equal(getTradrWay(), bizLC.getTradrWay()) &&
				Objects.equal(getBneContNo(), bizLC.getBneContNo());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDebtCode(), getBusinessType(), getTheApplicant(), getOpenWay(), getSpillageRange(), getPaymentTerm(), getTheForwaro(), getBeneficiaryName(), getBeneficiaryCountry(), getTradrWay(), getBneContNo());
	}
}