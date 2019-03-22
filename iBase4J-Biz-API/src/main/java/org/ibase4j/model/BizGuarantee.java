package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：保函担保
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_GUARANTEES_LETTER")
public class BizGuarantee extends BaseModel implements Serializable {

	/**
	 * 业务编号
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 产品种类
	 */
	@TableField("BUSINESS_TYPES")
	private String businessTypes;
	/**
	 * 担保合同编号
	 */
	@TableField("GUAR_NO")
	private String guarNo;
	/**
	 * 业务名称
	 */
	@TableField("BUSINESS_NAME")
	private String businessName;
	/**
	 * 申请人
	 */
	@TableField("THE_APPLICANT")
	private String theApplicant;
	/**
	 * 被担保人
	 */
	@TableField("BY_GUARANTOR")
	private String byGuarantor;
	/**
	 * 受益人名称
	 */
	@TableField("BENEFICIARY_NAME")
	private String beneficiaryName;
	/**
	 * 受益人国别
	 */
	@TableField("COUNTRY_BENEFICIARY")
	private String countryBeneficiary;
	/**
	 * 被担保人国别
	 */
	@TableField("COUNTRY_GUARANTOR")
	private String countryGuarantor;
	/**
	 * 融资金额
	 */
	@TableField("FINANC_AMT")
	private Long financAmt;
		/**
	 * 融资期限单位
	 */
	@TableField("FINANC_TERM_UNIT")
	private Long financTermUnit;
	/**
	 * 融资担保方式
	 */
	@TableField("FINANC_GUAR_TYPE")
	private String financGuarType;
	/**
	 * 还款来源
	 */
	@TableField("PAYMENT")
	private String payment;
	/**
	 * 资金用途
	 */
	@TableField("USE_FUNDS")
	private String useFunds;
	/**
	 * 融资成本
	 */
	@TableField("FINANC_COST")
	private Long finanCost;
	/**
	 * 业务生效日期
	 */
	@TableField("EFFECTIVE_DATE")
	private Date effectiveDate;
	/**
	 * 业务生效条件
	 */
	@TableField("EFFECTIVE_CONDITIONS")
	private String effectiveConditions;
	/**
	 * 业务失效日期
	 */
	@TableField("EXPIRY_DATE")
	private Date expiryDate;
	/**
	 * 业务到期日期
	 */
	@TableField("EXPIRATION_DATE")
	private Date expirationDate;
	/**
	 * 业务失效事件
	 */
	@TableField("FAILURE_EVENT")
	private String failureEvent;
	/**
	 * 开立方式(1.直开2.转开)
	 */
	@TableField("OPEN_WAY")
	private Long openWay;
	/**
	 * 反担保到期日期
	 */
	@TableField("GUAR_EXPIRY_DATE")
	private Long guarExpiryDare;
	/**
	 * 反担保失效事件
	 */
	@TableField("GUAR_FAILURE")
	private String guarFailure;
	/**
	 * 息费收取方式
	 */
	@TableField("CHARGE_INTEREST")
	private String chargeInterest;
	/**
	 * 保函格式(1.我行标准格式2.其他)
	 */
	@TableField("GUARANTEE_FORMAT")
	private Long guaranterFormat;
	/**
	 * 是否经法审
	 */
	@TableField("LAW_FLAG")
	private Long lawFlag;

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

	public String getGuarNo() {
		return guarNo;
	}

	public void setGuarNo(String guarNo) {
		this.guarNo = guarNo;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getTheApplicant() {
		return theApplicant;
	}

	public void setTheApplicant(String theApplicant) {
		this.theApplicant = theApplicant;
	}

	public String getByGuarantor() {
		return byGuarantor;
	}

	public void setByGuarantor(String byGuarantor) {
		this.byGuarantor = byGuarantor;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getCountryBeneficiary() {
		return countryBeneficiary;
	}

	public void setCountryBeneficiary(String countryBeneficiary) {
		this.countryBeneficiary = countryBeneficiary;
	}

	public String getCountryGuarantor() {
		return countryGuarantor;
	}

	public void setCountryGuarantor(String countryGuarantor) {
		this.countryGuarantor = countryGuarantor;
	}

	public Long getFinancAmt() {
		return financAmt;
	}

	public void setFinancAmt(Long financAmt) {
		this.financAmt = financAmt;
	}

	public Long getFinancTermUnit() {
		return financTermUnit;
	}

	public void setFinancTermUnit(Long financTermUnit) {
		this.financTermUnit = financTermUnit;
	}

	public String getFinancGuarType() {
		return financGuarType;
	}

	public void setFinancGuarType(String financGuarType) {
		this.financGuarType = financGuarType;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getUseFunds() {
		return useFunds;
	}

	public void setUseFunds(String useFunds) {
		this.useFunds = useFunds;
	}

	public Long getFinanCost() {
		return finanCost;
	}

	public void setFinanCost(Long finanCost) {
		this.finanCost = finanCost;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEffectiveConditions() {
		return effectiveConditions;
	}

	public void setEffectiveConditions(String effectiveConditions) {
		this.effectiveConditions = effectiveConditions;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getFailureEvent() {
		return failureEvent;
	}

	public void setFailureEvent(String failureEvent) {
		this.failureEvent = failureEvent;
	}

	public Long getOpenWay() {
		return openWay;
	}

	public void setOpenWay(Long openWay) {
		this.openWay = openWay;
	}

	public Long getGuarExpiryDare() {
		return guarExpiryDare;
	}

	public void setGuarExpiryDare(Long guarExpiryDare) {
		this.guarExpiryDare = guarExpiryDare;
	}

	public String getGuarFailure() {
		return guarFailure;
	}

	public void setGuarFailure(String guarFailure) {
		this.guarFailure = guarFailure;
	}

	public String getChargeInterest() {
		return chargeInterest;
	}

	public void setChargeInterest(String chargeInterest) {
		this.chargeInterest = chargeInterest;
	}

	public Long getGuaranterFormat() {
		return guaranterFormat;
	}

	public void setGuaranterFormat(Long guaranterFormat) {
		this.guaranterFormat = guaranterFormat;
	}

	public Long getLawFlag() {
		return lawFlag;
	}

	public void setLawFlag(Long lawFlag) {
		this.lawFlag = lawFlag;
	}

	public BizGuarantee() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("guarNo", guarNo)
				.add("businessName", businessName)
				.add("theApplicant", theApplicant)
				.add("byGuarantor", byGuarantor)
				.add("beneficiaryName", beneficiaryName)
				.add("countryBeneficiary", countryBeneficiary)
				.add("countryGuarantor", countryGuarantor)
				.add("financAmt", financAmt)
				.add("financTermUnit", financTermUnit)
				.add("financGuarType", financGuarType)
				.add("payment", payment)
				.add("useFunds", useFunds)
				.add("finanCost", finanCost)
				.add("effectiveDate", effectiveDate)
				.add("effectiveConditions", effectiveConditions)
				.add("expiryDate", expiryDate)
				.add("expirationDate", expirationDate)
				.add("failureEvent", failureEvent)
				.add("openWay", openWay)
				.add("guarExpiryDare", guarExpiryDare)
				.add("guarFailure", guarFailure)
				.add("chargeInterest", chargeInterest)
				.add("guaranterFormat", guaranterFormat)
				.add("lawFlag", lawFlag)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizGuarantee)) return false;
		BizGuarantee that = (BizGuarantee) o;
		return Objects.equal(getDebtCode(), that.getDebtCode()) &&
				Objects.equal(getBusinessTypes(), that.getBusinessTypes()) &&
				Objects.equal(getGuarNo(), that.getGuarNo()) &&
				Objects.equal(getBusinessName(), that.getBusinessName()) &&
				Objects.equal(getTheApplicant(), that.getTheApplicant()) &&
				Objects.equal(getByGuarantor(), that.getByGuarantor()) &&
				Objects.equal(getBeneficiaryName(), that.getBeneficiaryName()) &&
				Objects.equal(getCountryBeneficiary(), that.getCountryBeneficiary()) &&
				Objects.equal(getCountryGuarantor(), that.getCountryGuarantor()) &&
				Objects.equal(getFinancAmt(), that.getFinancAmt()) &&
				Objects.equal(getFinancTermUnit(), that.getFinancTermUnit()) &&
				Objects.equal(getFinancGuarType(), that.getFinancGuarType()) &&
				Objects.equal(getPayment(), that.getPayment()) &&
				Objects.equal(getUseFunds(), that.getUseFunds()) &&
				Objects.equal(getFinanCost(), that.getFinanCost()) &&
				Objects.equal(getEffectiveDate(), that.getEffectiveDate()) &&
				Objects.equal(getEffectiveConditions(), that.getEffectiveConditions()) &&
				Objects.equal(getExpiryDate(), that.getExpiryDate()) &&
				Objects.equal(getExpirationDate(), that.getExpirationDate()) &&
				Objects.equal(getFailureEvent(), that.getFailureEvent()) &&
				Objects.equal(getOpenWay(), that.getOpenWay()) &&
				Objects.equal(getGuarExpiryDare(), that.getGuarExpiryDare()) &&
				Objects.equal(getGuarFailure(), that.getGuarFailure()) &&
				Objects.equal(getChargeInterest(), that.getChargeInterest()) &&
				Objects.equal(getGuaranterFormat(), that.getGuaranterFormat()) &&
				Objects.equal(getLawFlag(), that.getLawFlag());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDebtCode(), getBusinessTypes(), getGuarNo(), getBusinessName(), getTheApplicant(), getByGuarantor(), getBeneficiaryName(), getCountryBeneficiary(), getCountryGuarantor(), getFinancAmt(), getFinancTermUnit(), getFinancGuarType(), getPayment(), getUseFunds(), getFinanCost(), getEffectiveDate(), getEffectiveConditions(), getExpiryDate(), getExpirationDate(), getFailureEvent(), getOpenWay(), getGuarExpiryDare(), getGuarFailure(), getChargeInterest(), getGuaranterFormat(), getLawFlag());
	}
}