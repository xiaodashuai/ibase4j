package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：保理
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_FACTORING")
public class BizFactoring extends BaseModel implements Serializable {

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
	 * 申请人
	 */
	@TableField("THE_APPLICANT")
	private String theApplicant;
	/**
	 * 进口商名称
	 */
	@TableField("EDF_IMPORTER")
	private String edfImporter;
	/**
	 * 进口保理商名称
	 */
	@TableField("EDF_IMPORT_FACTOR")
	private String edfImportFactor;
	/**
	 * 出口商
	 */
	@TableField("EXPORTER")
	private String exporter;
	/**
	 * 出口保理商
	 */
	@TableField("EXPORTER_AGENT")
	private String exporterAgent;
	/**
	 * 业务期限
	 */
	@TableField("BNS_DURATION_TYPE")
	private Long bnsDurationType;
	/**
	 * 公开性
	 */
	@TableField("OPENNESS")
	private Long openness;
	/**
	 * 追索权
	 */
	@TableField("RIGHT_RECOURSE")
	private Long rightRecourse;
		/**
	 * 融资
	 */
	@TableField("FINANCE")
	private Long finance;
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
	 * 结算方式（1.O/A；2.D/A）
	 */
	@TableField("CLEAR_FORM")
	private Long clearForm;
	/**
	 * 买方保理商
	 */
	@TableField("BUYER_SELLER_AGENT")
	private String buyerSellerAgent;
	/**
	 * 卖方保理商
	 */
	@TableField("SELLER_SELLER_AGENT")
	private String sellerSellerAgent;
	/**
	 * 购货方
	 */
	@TableField("BUYERS")
	private String buyers;
	/**
	 * 销货方
	 */
	@TableField("SELLER")
	private String seller;

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

	public String getTheApplicant() {
		return theApplicant;
	}

	public void setTheApplicant(String theApplicant) {
		this.theApplicant = theApplicant;
	}

	public String getEdfImporter() {
		return edfImporter;
	}

	public void setEdfImporter(String edfImporter) {
		this.edfImporter = edfImporter;
	}

	public String getEdfImportFactor() {
		return edfImportFactor;
	}

	public void setEdfImportFactor(String edfImportFactor) {
		this.edfImportFactor = edfImportFactor;
	}

	public String getExporter() {
		return exporter;
	}

	public void setExporter(String exporter) {
		this.exporter = exporter;
	}

	public String getExporterAgent() {
		return exporterAgent;
	}

	public void setExporterAgent(String exporterAgent) {
		this.exporterAgent = exporterAgent;
	}

	public Long getBnsDurationType() {
		return bnsDurationType;
	}

	public void setBnsDurationType(Long bnsDurationType) {
		this.bnsDurationType = bnsDurationType;
	}

	public Long getOpenness() {
		return openness;
	}

	public void setOpenness(Long openness) {
		this.openness = openness;
	}

	public Long getRightRecourse() {
		return rightRecourse;
	}

	public void setRightRecourse(Long rightRecourse) {
		this.rightRecourse = rightRecourse;
	}

	public Long getFinance() {
		return finance;
	}

	public void setFinance(Long finance) {
		this.finance = finance;
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

	public Long getClearForm() {
		return clearForm;
	}

	public void setClearForm(Long clearForm) {
		this.clearForm = clearForm;
	}

	public String getBuyerSellerAgent() {
		return buyerSellerAgent;
	}

	public void setBuyerSellerAgent(String buyerSellerAgent) {
		this.buyerSellerAgent = buyerSellerAgent;
	}

	public String getSellerSellerAgent() {
		return sellerSellerAgent;
	}

	public void setSellerSellerAgent(String sellerSellerAgent) {
		this.sellerSellerAgent = sellerSellerAgent;
	}

	public String getBuyers() {
		return buyers;
	}

	public void setBuyers(String buyers) {
		this.buyers = buyers;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public BizFactoring() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("theApplicant", theApplicant)
				.add("edfImporter", edfImporter)
				.add("edfImportFactor", edfImportFactor)
				.add("exporter", exporter)
				.add("exporterAgent", exporterAgent)
				.add("bnsDurationType", bnsDurationType)
				.add("openness", openness)
				.add("rightRecourse", rightRecourse)
				.add("finance", finance)
				.add("valueDate", valueDate)
				.add("dueDate", dueDate)
				.add("periodOfGrace", periodOfGrace)
				.add("clearForm", clearForm)
				.add("buyerSellerAgent", buyerSellerAgent)
				.add("sellerSellerAgent", sellerSellerAgent)
				.add("buyers", buyers)
				.add("seller", seller)
				.toString();
	}
}