package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：贸易融资
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_TRADE_FINANCING")
public class BizTF extends BaseModel implements Serializable {

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
	 * 结算方式(1.L/C;2.Domestic L/C;3.D/P;4.D/A;5.汇款;6.托收;7.D/A;
	 8.O/A)
	 */
	@TableField("CLEAR_FORM")
	private Long clearForm;
	/**
	 * 单据编号
	 */
	@TableField("RECEIPT_NO")
	private String receiptNo;
	/**
	 * 信用证编号
	 */
	@TableField("LC_NUM")
	private String lcNum;
	/**
	 * 汇出汇款编号
	 */
	@TableField("DRAFTS_NO")
	private String draftsNo;
		/**
	 * 交单业务编号
	 */
	@TableField("DELIVERY_SERVICE_NO")
	private String deliveryServiceNo;
	/**
	 * 保单号码
	 */
	@TableField("POLICY_NUM")
	private String policyNum;
	/**
	 * 到单编号
	 */
	@TableField("SINGLE_NUM")
	private String singleNum;
	/**
	 * 发票号码
	 */
	@TableField("INVOICE_NUM")
	private String invoiceNum;
	/**
	 * 出口商
	 */
	@TableField("EXPORT_MERCHANT")
	private String exportMerchant;
	/**
	 * 进口商
	 */
	@TableField("IMPORTER")
	private String importer;

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

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getLcNum() {
		return lcNum;
	}

	public void setLcNum(String lcNum) {
		this.lcNum = lcNum;
	}

	public String getDraftsNo() {
		return draftsNo;
	}

	public void setDraftsNo(String draftsNo) {
		this.draftsNo = draftsNo;
	}

	public String getDeliveryServiceNo() {
		return deliveryServiceNo;
	}

	public void setDeliveryServiceNo(String deliveryServiceNo) {
		this.deliveryServiceNo = deliveryServiceNo;
	}

	public String getPolicyNum() {
		return policyNum;
	}

	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}

	public String getSingleNum() {
		return singleNum;
	}

	public void setSingleNum(String singleNum) {
		this.singleNum = singleNum;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getExportMerchant() {
		return exportMerchant;
	}

	public void setExportMerchant(String exportMerchant) {
		this.exportMerchant = exportMerchant;
	}

	public String getImporter() {
		return importer;
	}

	public void setImporter(String importer) {
		this.importer = importer;
	}

	public BizTF() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("theApplicant", theApplicant)
				.add("valueDate", valueDate)
				.add("dueDate", dueDate)
				.add("periodOfGrace", periodOfGrace)
				.add("clearForm", clearForm)
				.add("receiptNo", receiptNo)
				.add("lcNum", lcNum)
				.add("draftsNo", draftsNo)
				.add("deliveryServiceNo", deliveryServiceNo)
				.add("policyNum", policyNum)
				.add("singleNum", singleNum)
				.add("invoiceNum", invoiceNum)
				.add("exportMerchant", exportMerchant)
				.add("importer", importer)
				.toString();
	}
}