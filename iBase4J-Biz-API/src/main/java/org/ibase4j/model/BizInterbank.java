package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：同业
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_INTERBANK")
public class BizInterbank extends BaseModel implements Serializable {

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
	 * 开立方式(1.直开;2.转开)
	 */
	@TableField("OPEN_WAY")
	private Long openWay;
	/**
	 * 开立日期
	 */
	@TableField("OPENING_DATE")
	private Date openingDate;
	/**
	 * 开出方式(1.电开;2.信开)
	 */
	@TableField("OUT_WAY")
	private Date outWay;
	/**
	 * 溢短装范围
	 */
	@TableField("OVERLOADING_RANGE")
	private String overloadingRange;
	/**
	 * 付款期限(文本)
	 */
	@TableField("EXPIRATION_OF_PAY_TXT")
	private String expirationOfPayTxt;
	/**
	 * 付款期限(数字)
	 */
	@TableField("EXPIRATION_OF_PAY_NO")
	private Long expirationOfPayNo;
	/**
	 * 申请人名称
	 */
	@TableField("PROPOSER")
	private String proposer;
	/**
	 * 受益人国别
	 */
	@TableField("BENEFICIARY_COUNTRY")
	private String beneficiaryCountry;
	/**
	 * 受益人名称
	 */
	@TableField("BENEFICIARY_NAME")
	private String beneficiaryName;
	/**
	 * 商务合同编号
	 */
	@TableField("BNS_CONTRACT_NO")
	private String bnsCoutractNo;
		/**
	 * 委托银行
	 */
	@TableField("ENTRUSTMENT_BANK")
	private String entrustmentBank;
	/**
	 * 委托方式(1.转开保函 2.代开保函)
	 */
	@TableField("ENTRUSTMENT_MODEL")
	private Long entrustmentModel;
	/**
	 * 被担保人
	 */
	@TableField("VOUCHEE")
	private String vouchee;
	/**
	 * 被担保人国别
	 */
	@TableField("COUNTRY_GUARANTOR")
	private String countryGuarantor;
	/**
	 * 保费收取方式
	 */
	@TableField("PREMIUM_COLLECTION")
	private Long premiumCollection;
	/**
	 * 反担保函生效日期
	 */
	@TableField("GUAR_EFFECTIVE_DATE")
	private Date guarEffectiveDate;
	/**
	 * 生效条件
	 */
	@TableField("ENTRY_CONDITION")
	private String entryCondittion;
	/**
	 * 反担保函到期日
	 */
	@TableField("EXPIRY_COUNTRY_GUARANTEE")
	private Date expiryCountryGuaranter;
	/**
	 * 失效事件
	 */
	@TableField("FAILURE_EVENTS")
	private String failureEvents;
	/**
	 * 业务生效日期
	 */
	@TableField("EFFECTIVE_DATE_BUSINESS")
	private Date effectiveDateBusiness;
	/**
	 * 业务生效条件
	 */
	@TableField("OPERAT_CONDITION")
	private String operatCondittion;
	/**
	 * 业务到期日期
	 */
	@TableField("BUSINESS_EXPIRATION_DATE")
	private Date businessExpirationDate;
	/**
	 * 业务失效事件
	 */
	@TableField("BUSINESS_FAILURE_EVENT")
	private String businessFailureEvent;
	/**
	 * 保函格式(1.我行标准格式2.其他如选“其他”，是否经法审：
	 */
	@TableField("LETTER_GUARANTEE")
	private Long letterGuarantee;
	/**
	 * 是否经法审
	 */
	@TableField("LAW")
	private Long law;
	/**
	 * 融资金额
	 */
	@TableField("FINANCING_AMOUNT")
	private Long financingAmount;
	/**
	 * 融资成本（利率+手续费）
	 */
	@TableField("FINANCING_COST")
	private Long financingCost;
	/**
	 *资金用途
	 */
	@TableField("USE_OF_FUNDS")
	private String useOfFunds;
	/**
	 * 还款来源
	 */
	@TableField("PAYMENT_SOURCE")
	private String paymentSource;
	/**
	 * 生效日期
	 */
	@TableField("EFFECTIVE_DATE")
	private Date effectiveDate;
	/**
	 * 风险承担比例
	 */
	@TableField("RISK_BEARING_RATIO")
	private Long riskBearingRatio;
	/**
	 * 起息日
	 */
	@TableField("VALUE_DATE")
	private Date valueDate;
	/**
	 * 到期日
	 */
	@TableField("DUE_DATE")
	private Date dueDate;
	/**
	 * 宽限期
	 */
	@TableField("PERIOD_OF_GRACE")
	private Long periodOfGrace;
	/**
	 * 基础交易买方名称
	 */
	@TableField("NAME_UNDER_BUYER")
	private Long nameUnderBuyer;
	/**
	 * 基础交易卖方名称
	 */
	@TableField("NAME_UNDER_SELLER")
	private Long nameUnderSeller;
	/**
	 * 买方国别
	 */
	@TableField("BUYER_COUNTRY")
	private Long buyerCountry;
	/**
	 * 卖方国别
	 */
	@TableField("SELLER_COUNTRY")
	private Long sellerCountry;
	/**
	 * 业务相关凭据
	 */
	@TableField("RELATED_CREDENTIALS")
	private String relatedCredentials;
	/**
	 * 收款行名称
	 */
	@TableField("NAME_RECEIVE_BANK")
	private String nameReceiveBank;
	/**
	 * 收款人名称
	 */
	@TableField("NAME_OF_PAYEE")
	private String nameOfPayee;
	/**
	 * 收款人账号
	 */
	@TableField("ACCOUNT_BENEFICIARY")
	private String accountBeneficiary;
	/**
	 * 债务人银行
	 */
	@TableField("OBLIGOR_BANK")
	private String obligorBank;
	/**
	 * 信用证/备用证号码
	 */
	@TableField("CREDIT_NO")
	private String creditNo;
	/**
	 * 息费收取方式(息\费收取方式1:息\费先收2;息\费后收;3:息\费内扣;4:其他;)
	 */
	@TableField("COLLECT_MODEL")
	private String collectModel;
	/**
	 * 息费收取方式其他
	 */
	@TableField("COLLECT_MODEL_OTHER")
	private String collectModelOther;

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

	public Long getOpenWay() {
		return openWay;
	}

	public void setOpenWay(Long openWay) {
		this.openWay = openWay;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getOutWay() {
		return outWay;
	}

	public void setOutWay(Date outWay) {
		this.outWay = outWay;
	}

	public String getOverloadingRange() {
		return overloadingRange;
	}

	public void setOverloadingRange(String overloadingRange) {
		this.overloadingRange = overloadingRange;
	}

	public String getExpirationOfPayTxt() {
		return expirationOfPayTxt;
	}

	public void setExpirationOfPayTxt(String expirationOfPayTxt) {
		this.expirationOfPayTxt = expirationOfPayTxt;
	}

	public Long getExpirationOfPayNo() {
		return expirationOfPayNo;
	}

	public void setExpirationOfPayNo(Long expirationOfPayNo) {
		this.expirationOfPayNo = expirationOfPayNo;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBnsCoutractNo() {
		return bnsCoutractNo;
	}

	public void setBnsCoutractNo(String bnsCoutractNo) {
		this.bnsCoutractNo = bnsCoutractNo;
	}

	public String getEntrustmentBank() {
		return entrustmentBank;
	}

	public void setEntrustmentBank(String entrustmentBank) {
		this.entrustmentBank = entrustmentBank;
	}

	public Long getEntrustmentModel() {
		return entrustmentModel;
	}

	public void setEntrustmentModel(Long entrustmentModel) {
		this.entrustmentModel = entrustmentModel;
	}

	public String getVouchee() {
		return vouchee;
	}

	public void setVouchee(String vouchee) {
		this.vouchee = vouchee;
	}

	public String getCountryGuarantor() {
		return countryGuarantor;
	}

	public void setCountryGuarantor(String countryGuarantor) {
		this.countryGuarantor = countryGuarantor;
	}

	public Long getPremiumCollection() {
		return premiumCollection;
	}

	public void setPremiumCollection(Long premiumCollection) {
		this.premiumCollection = premiumCollection;
	}

	public Date getGuarEffectiveDate() {
		return guarEffectiveDate;
	}

	public void setGuarEffectiveDate(Date guarEffectiveDate) {
		this.guarEffectiveDate = guarEffectiveDate;
	}

	public String getEntryCondittion() {
		return entryCondittion;
	}

	public void setEntryCondittion(String entryCondittion) {
		this.entryCondittion = entryCondittion;
	}

	public Date getExpiryCountryGuaranter() {
		return expiryCountryGuaranter;
	}

	public void setExpiryCountryGuaranter(Date expiryCountryGuaranter) {
		this.expiryCountryGuaranter = expiryCountryGuaranter;
	}

	public String getFailureEvents() {
		return failureEvents;
	}

	public void setFailureEvents(String failureEvents) {
		this.failureEvents = failureEvents;
	}

	public Date getEffectiveDateBusiness() {
		return effectiveDateBusiness;
	}

	public void setEffectiveDateBusiness(Date effectiveDateBusiness) {
		this.effectiveDateBusiness = effectiveDateBusiness;
	}

	public String getOperatCondittion() {
		return operatCondittion;
	}

	public void setOperatCondittion(String operatCondittion) {
		this.operatCondittion = operatCondittion;
	}

	public Date getBusinessExpirationDate() {
		return businessExpirationDate;
	}

	public void setBusinessExpirationDate(Date businessExpirationDate) {
		this.businessExpirationDate = businessExpirationDate;
	}

	public String getBusinessFailureEvent() {
		return businessFailureEvent;
	}

	public void setBusinessFailureEvent(String businessFailureEvent) {
		this.businessFailureEvent = businessFailureEvent;
	}

	public Long getLetterGuarantee() {
		return letterGuarantee;
	}

	public void setLetterGuarantee(Long letterGuarantee) {
		this.letterGuarantee = letterGuarantee;
	}

	public Long getLaw() {
		return law;
	}

	public void setLaw(Long law) {
		this.law = law;
	}

	public Long getFinancingAmount() {
		return financingAmount;
	}

	public void setFinancingAmount(Long financingAmount) {
		this.financingAmount = financingAmount;
	}

	public Long getFinancingCost() {
		return financingCost;
	}

	public void setFinancingCost(Long financingCost) {
		this.financingCost = financingCost;
	}

	public String getUseOfFunds() {
		return useOfFunds;
	}

	public void setUseOfFunds(String useOfFunds) {
		this.useOfFunds = useOfFunds;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getRiskBearingRatio() {
		return riskBearingRatio;
	}

	public void setRiskBearingRatio(Long riskBearingRatio) {
		this.riskBearingRatio = riskBearingRatio;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getPeriodOfGrace() {
		return periodOfGrace;
	}

	public void setPeriodOfGrace(Long periodOfGrace) {
		this.periodOfGrace = periodOfGrace;
	}

	public Long getNameUnderBuyer() {
		return nameUnderBuyer;
	}

	public void setNameUnderBuyer(Long nameUnderBuyer) {
		this.nameUnderBuyer = nameUnderBuyer;
	}

	public Long getNameUnderSeller() {
		return nameUnderSeller;
	}

	public void setNameUnderSeller(Long nameUnderSeller) {
		this.nameUnderSeller = nameUnderSeller;
	}

	public Long getBuyerCountry() {
		return buyerCountry;
	}

	public void setBuyerCountry(Long buyerCountry) {
		this.buyerCountry = buyerCountry;
	}

	public Long getSellerCountry() {
		return sellerCountry;
	}

	public void setSellerCountry(Long sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getRelatedCredentials() {
		return relatedCredentials;
	}

	public void setRelatedCredentials(String relatedCredentials) {
		this.relatedCredentials = relatedCredentials;
	}

	public String getNameReceiveBank() {
		return nameReceiveBank;
	}

	public void setNameReceiveBank(String nameReceiveBank) {
		this.nameReceiveBank = nameReceiveBank;
	}

	public String getNameOfPayee() {
		return nameOfPayee;
	}

	public void setNameOfPayee(String nameOfPayee) {
		this.nameOfPayee = nameOfPayee;
	}

	public String getAccountBeneficiary() {
		return accountBeneficiary;
	}

	public void setAccountBeneficiary(String accountBeneficiary) {
		this.accountBeneficiary = accountBeneficiary;
	}

	public String getObligorBank() {
		return obligorBank;
	}

	public void setObligorBank(String obligorBank) {
		this.obligorBank = obligorBank;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getCollectModel() {
		return collectModel;
	}

	public void setCollectModel(String collectModel) {
		this.collectModel = collectModel;
	}

	public String getCollectModelOther() {
		return collectModelOther;
	}

	public void setCollectModelOther(String collectModelOther) {
		this.collectModelOther = collectModelOther;
	}

	public BizInterbank() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("openWay", openWay)
				.add("openingDate", openingDate)
				.add("outWay", outWay)
				.add("overloadingRange", overloadingRange)
				.add("expirationOfPayTxt", expirationOfPayTxt)
				.add("expirationOfPayNo", expirationOfPayNo)
				.add("proposer", proposer)
				.add("beneficiaryCountry", beneficiaryCountry)
				.add("beneficiaryName", beneficiaryName)
				.add("bnsCoutractNo", bnsCoutractNo)
				.add("entrustmentBank", entrustmentBank)
				.add("entrustmentModel", entrustmentModel)
				.add("vouchee", vouchee)
				.add("countryGuarantor", countryGuarantor)
				.add("premiumCollection", premiumCollection)
				.add("guarEffectiveDate", guarEffectiveDate)
				.add("entryCondittion", entryCondittion)
				.add("expiryCountryGuaranter", expiryCountryGuaranter)
				.add("failureEvents", failureEvents)
				.add("effectiveDateBusiness", effectiveDateBusiness)
				.add("operatCondittion", operatCondittion)
				.add("businessExpirationDate", businessExpirationDate)
				.add("businessFailureEvent", businessFailureEvent)
				.add("letterGuarantee", letterGuarantee)
				.add("law", law)
				.add("financingAmount", financingAmount)
				.add("financingCost", financingCost)
				.add("useOfFunds", useOfFunds)
				.add("paymentSource", paymentSource)
				.add("effectiveDate", effectiveDate)
				.add("riskBearingRatio", riskBearingRatio)
				.add("valueDate", valueDate)
				.add("dueDate", dueDate)
				.add("periodOfGrace", periodOfGrace)
				.add("nameUnderBuyer", nameUnderBuyer)
				.add("nameUnderSeller", nameUnderSeller)
				.add("buyerCountry", buyerCountry)
				.add("sellerCountry", sellerCountry)
				.add("relatedCredentials", relatedCredentials)
				.add("nameReceiveBank", nameReceiveBank)
				.add("nameOfPayee", nameOfPayee)
				.add("accountBeneficiary", accountBeneficiary)
				.add("obligorBank", obligorBank)
				.add("creditNo", creditNo)
				.add("collectModel", collectModel)
				.add("collectModelOther", collectModelOther)
				.toString();
	}
}