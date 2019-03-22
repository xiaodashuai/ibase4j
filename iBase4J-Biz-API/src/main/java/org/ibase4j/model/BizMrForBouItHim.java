package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：福费廷(包买他行/自行买入)
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_FORFEIN_BOUGHT")
public class BizMrForBouItHim extends BaseModel implements Serializable {

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
	 * 申请方类型(1申请人;2进口商;3买方;4转卖行、5委托行 、6其他 )
	 */
	@TableField("APPLY_TYPE")
	private Long applyType;
	/**
	 * 申请方名称
	 */
	@TableField("APPLY_NAME")
	private String applyName;
	/**
	 * 申请方国别
	 */
	@TableField("APPLY_COUNTRY")
	private String applyCountry;
	/**
	 * 转卖方
	 */
	@TableField("SELLER_TRANS")
	private String selierTrans;
	/**
	 * 受益人类型
	 */
	@TableField("BENEFICIARY_TYPE")
	private Long beneficiaryType;
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
	 * 结算方式
	 (1L/C;2D/P;3D/A;4O/A;5T/T;6其他 )
	 */
	@TableField("SETTLEMENT_TYPE")
	private Long settlementType;
	/**
	 * 基础交易买方国别
	 */
	@TableField("BAYER_COUNTRY")
	private String bayerCountry;
	/**
	 * 基础交易买方名称
	 */
	@TableField("BAYER_NAME")
	private String bayerName;
	/**
	 * 基础交易卖方国别
	 */
	@TableField("SELLER_COUNTRY")
	private String sellerCountry;
	/**
	 * 基础交易卖方名称
	 */
	@TableField("SELLER_NAME")
	private String sellerName;
	/**
	 * 商务合同编号
	 */
	@TableField("BNS_CONTRACT_NO")
	private String bnsContractNo;
	/**
	 * 相关凭据说明
	 */
	@TableField("REVE_DESCRI")
	private String reveDescri;
	/**
	 * 转款情况说明
	 */
	@TableField("TRAN_DESCRI")
	private String tranDescri;
	/**
	 * 息费情况说明
	 */
	@TableField("RATE_DESCRI")
	private String rateDescri;
	/**
	 * 收款行名称
	 */
	@TableField("RECEVI_BAK")
	private String receviBak;
	/**
	 * 收款人名称
	 */
	@TableField("RECEVI_NAME")
	private String receviName;
	/**
	 * 收款人账号
	 */
	@TableField("RECEVI_ACCT")
	private String receviAcct;

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

	public Long getApplyType() {
		return applyType;
	}

	public void setApplyType(Long applyType) {
		this.applyType = applyType;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyCountry() {
		return applyCountry;
	}

	public void setApplyCountry(String applyCountry) {
		this.applyCountry = applyCountry;
	}

	public String getSelierTrans() {
		return selierTrans;
	}

	public void setSelierTrans(String selierTrans) {
		this.selierTrans = selierTrans;
	}

	public Long getBeneficiaryType() {
		return beneficiaryType;
	}

	public void setBeneficiaryType(Long beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
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

	public Long getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(Long settlementType) {
		this.settlementType = settlementType;
	}

	public String getBayerCountry() {
		return bayerCountry;
	}

	public void setBayerCountry(String bayerCountry) {
		this.bayerCountry = bayerCountry;
	}

	public String getBayerName() {
		return bayerName;
	}

	public void setBayerName(String bayerName) {
		this.bayerName = bayerName;
	}

	public String getSellerCountry() {
		return sellerCountry;
	}

	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBnsContractNo() {
		return bnsContractNo;
	}

	public void setBnsContractNo(String bnsContractNo) {
		this.bnsContractNo = bnsContractNo;
	}

	public String getReveDescri() {
		return reveDescri;
	}

	public void setReveDescri(String reveDescri) {
		this.reveDescri = reveDescri;
	}

	public String getTranDescri() {
		return tranDescri;
	}

	public void setTranDescri(String tranDescri) {
		this.tranDescri = tranDescri;
	}

	public String getRateDescri() {
		return rateDescri;
	}

	public void setRateDescri(String rateDescri) {
		this.rateDescri = rateDescri;
	}

	public String getReceviBak() {
		return receviBak;
	}

	public void setReceviBak(String receviBak) {
		this.receviBak = receviBak;
	}

	public String getReceviName() {
		return receviName;
	}

	public void setReceviName(String receviName) {
		this.receviName = receviName;
	}

	public String getReceviAcct() {
		return receviAcct;
	}

	public void setReceviAcct(String receviAcct) {
		this.receviAcct = receviAcct;
	}


	public BizMrForBouItHim() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("applyType", applyType)
				.add("applyName", applyName)
				.add("applyCountry", applyCountry)
				.add("selierTrans", selierTrans)
				.add("beneficiaryType", beneficiaryType)
				.add("beneficiaryName", beneficiaryName)
				.add("beneficiaryCountry", beneficiaryCountry)
				.add("valueDate", valueDate)
				.add("dueDate", dueDate)
				.add("periodOfGrace", periodOfGrace)
				.add("settlementType", settlementType)
				.add("bayerCountry", bayerCountry)
				.add("bayerName", bayerName)
				.add("sellerCountry", sellerCountry)
				.add("sellerName", sellerName)
				.add("bnsContractNo", bnsContractNo)
				.add("reveDescri", reveDescri)
				.add("tranDescri", tranDescri)
				.add("rateDescri", rateDescri)
				.add("receviBak", receviBak)
				.add("receviName", receviName)
				.add("receviAcct", receviAcct)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizMrForBouItHim)) return false;
		BizMrForBouItHim that = (BizMrForBouItHim) o;
		return Objects.equal(getDebtCode(), that.getDebtCode()) &&
				Objects.equal(getBusinessTypes(), that.getBusinessTypes()) &&
				Objects.equal(getApplyType(), that.getApplyType()) &&
				Objects.equal(getApplyName(), that.getApplyName()) &&
				Objects.equal(getApplyCountry(), that.getApplyCountry()) &&
				Objects.equal(getSelierTrans(), that.getSelierTrans()) &&
				Objects.equal(getBeneficiaryType(), that.getBeneficiaryType()) &&
				Objects.equal(getBeneficiaryName(), that.getBeneficiaryName()) &&
				Objects.equal(getBeneficiaryCountry(), that.getBeneficiaryCountry()) &&
				Objects.equal(getValueDate(), that.getValueDate()) &&
				Objects.equal(getDueDate(), that.getDueDate()) &&
				Objects.equal(getPeriodOfGrace(), that.getPeriodOfGrace()) &&
				Objects.equal(getSettlementType(), that.getSettlementType()) &&
				Objects.equal(getBayerCountry(), that.getBayerCountry()) &&
				Objects.equal(getBayerName(), that.getBayerName()) &&
				Objects.equal(getSellerCountry(), that.getSellerCountry()) &&
				Objects.equal(getSellerName(), that.getSellerName()) &&
				Objects.equal(getBnsContractNo(), that.getBnsContractNo()) &&
				Objects.equal(getReveDescri(), that.getReveDescri()) &&
				Objects.equal(getTranDescri(), that.getTranDescri()) &&
				Objects.equal(getRateDescri(), that.getRateDescri()) &&
				Objects.equal(getReceviBak(), that.getReceviBak()) &&
				Objects.equal(getReceviName(), that.getReceviName()) &&
				Objects.equal(getReceviAcct(), that.getReceviAcct());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDebtCode(), getBusinessTypes(), getApplyType(), getApplyName(), getApplyCountry(), getSelierTrans(), getBeneficiaryType(), getBeneficiaryName(), getBeneficiaryCountry(), getValueDate(), getDueDate(), getPeriodOfGrace(), getSettlementType(), getBayerCountry(), getBayerName(), getSellerCountry(), getSellerName(), getBnsContractNo(), getReveDescri(), getTranDescri(), getRateDescri(), getReceviBak(), getReceviName(), getReceviAcct());
	}
}