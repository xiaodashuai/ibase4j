package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：同业额度
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_TRADE_LINE")
public class BizTradeCredit extends BaseModel implements Serializable {

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
	 * 客户号
	 */
	@TableField("CUSROMER_NO")
	private String cusromerNo;
	/**
	 * 占用额度类型(1.基本授信额度;2.专项授信额度)
	 */
	@TableField("OCCUPATION_TYPE")
	private Long occupationType;
	/**
	 * 占用方式(1.占用本行自己额度;2.占用上级行额度)
	 */
	@TableField("TAKE_UP_WAY")
	private Long takeUpWay;
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
	 * 折算牌价
	 */
	@TableField("DISCOUNT_RATE")
	private Long discountRate;
	/**
	 * 申请额度币种
	 */
	@TableField("APPLY_QUOTA_CURRENCY")
	private String applyQuotaCurrency;
	/**
	 * 申请额度
	 */
	@TableField("CREDIT_APPLICATION")
	private Long creditApplication;
	/**
	 * 总额度币种
	 */
	@TableField("TOTAL_AMOUNT_CURRENCY")
	private String totalAmountCurrency;
		/**
	 * 总额度金额
	 */
	@TableField("TOTAL_AMOUNT")
	private Long totalAmount;
	/**
	 * 收费方式(1.先收;2.后收)
	 */
	@TableField("TOLL_COLLECTION_MANNER")
	private Long tollCollectionManner;
	/**
	 * 总价
	 */
	@TableField("TOTAL_PRICE")
	private Long totalPrice;
	/**
	 * 经办人
	 */
	@TableField("RESPONSIBLE_PERSON")
	private String responsiblePerson;
	/**
	 * 复核人
	 */
	@TableField("REVIEW_OFFICER")
	private String reviewOfficer;
	/**
	 * 资金来源内容
	 */
	@TableField("CAPITAL_SOURCE_OTH")
	private String capitalSourceOth;
	/**
	 * 资金来源(1.总行资金2.其他)
	 */
	@TableField("CAPITAL_SOURCE")
	private Long capitalSource;

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

	public String getCusromerNo() {
		return cusromerNo;
	}

	public void setCusromerNo(String cusromerNo) {
		this.cusromerNo = cusromerNo;
	}

	public Long getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(Long occupationType) {
		this.occupationType = occupationType;
	}

	public Long getTakeUpWay() {
		return takeUpWay;
	}

	public void setTakeUpWay(Long takeUpWay) {
		this.takeUpWay = takeUpWay;
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

	public Long getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Long discountRate) {
		this.discountRate = discountRate;
	}

	public String getApplyQuotaCurrency() {
		return applyQuotaCurrency;
	}

	public void setApplyQuotaCurrency(String applyQuotaCurrency) {
		this.applyQuotaCurrency = applyQuotaCurrency;
	}

	public Long getCreditApplication() {
		return creditApplication;
	}

	public void setCreditApplication(Long creditApplication) {
		this.creditApplication = creditApplication;
	}

	public String getTotalAmountCurrency() {
		return totalAmountCurrency;
	}

	public void setTotalAmountCurrency(String totalAmountCurrency) {
		this.totalAmountCurrency = totalAmountCurrency;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getTollCollectionManner() {
		return tollCollectionManner;
	}

	public void setTollCollectionManner(Long tollCollectionManner) {
		this.tollCollectionManner = tollCollectionManner;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getReviewOfficer() {
		return reviewOfficer;
	}

	public void setReviewOfficer(String reviewOfficer) {
		this.reviewOfficer = reviewOfficer;
	}

	public String getCapitalSourceOth() {
		return capitalSourceOth;
	}

	public void setCapitalSourceOth(String capitalSourceOth) {
		this.capitalSourceOth = capitalSourceOth;
	}

	public Long getCapitalSource() {
		return capitalSource;
	}

	public void setCapitalSource(Long capitalSource) {
		this.capitalSource = capitalSource;
	}

	public BizTradeCredit() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("cusromerNo", cusromerNo)
				.add("occupationType", occupationType)
				.add("takeUpWay", takeUpWay)
				.add("valueDate", valueDate)
				.add("dueDate", dueDate)
				.add("discountRate", discountRate)
				.add("applyQuotaCurrency", applyQuotaCurrency)
				.add("creditApplication", creditApplication)
				.add("totalAmountCurrency", totalAmountCurrency)
				.add("totalAmount", totalAmount)
				.add("tollCollectionManner", tollCollectionManner)
				.add("totalPrice", totalPrice)
				.add("responsiblePerson", responsiblePerson)
				.add("reviewOfficer", reviewOfficer)
				.add("capitalSourceOth", capitalSourceOth)
				.add("capitalSource", capitalSource)
				.toString();
	}
}