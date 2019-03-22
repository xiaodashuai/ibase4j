/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：租金保理（品种）<br/>
 * 时间：2018-04-02
 * 
 * @author lwh
 * @version 1.0
 */
@TableName("BIZ_RENTAL_FACTORING")
@SuppressWarnings("serial")
public class BizRentalFactoring extends BaseModel implements Serializable {
	@TableField("DEBT_CODE") // 业务编号
	private String debtCode;

	@TableField("BUSINESS_TYPES") // 产品种类
	private String businessTypes;

	@TableField("GRANT_CODE") // 发放债项申请编号
	private String grantCode;

	@TableField("LEASEHOLD") // 租赁物
	private String leasehold;

	@TableField("BIZ_RENTAL_FACTORING_CODE") // 租金保理合同编号
	private String bizRentalFactoringCode;

	@TableField("LESSE_NAME") // 承租人（债务人）名称
	private String lesseName;

	@TableField("LESSE_CODE") // 承租人（债务人）客户编号
	private String lesseCode;

	@TableField("LESSE_RATE") // 承租人（债务人）客户评级
	private String lesseRate;

	@TableField("INDUSTRY_INVESTMENT") // 行业投向
	private String industryInvestment;

	@TableField("BACKGROUND_NATIONALITY") // 背景国别
	private String backgroundNationality;

	@TableField("REPAYMENT_TYPE") // 还款方式
	private String repaymentType;

	@TableField("IOU_CODE") // 借据编号
	private String iouCode;

	@TableField("FINANCE_PLATFORM") // 承租人是否是地方性融资平台
	private String financePlatform;
	
	@TableField("REN_FILE_NAME") // 租金保理合同附件名称
	private String renFileName;

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

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public String getLeasehold() {
		return leasehold;
	}

	public void setLeasehold(String leasehold) {
		this.leasehold = leasehold;
	}

	public String getBizRentalFactoringCode() {
		return bizRentalFactoringCode;
	}

	public void setBizRentalFactoringCode(String bizRentalFactoringCode) {
		this.bizRentalFactoringCode = bizRentalFactoringCode;
	}

	public String getLesseName() {
		return lesseName;
	}

	public void setLesseName(String lesseName) {
		this.lesseName = lesseName;
	}

	public String getLesseCode() {
		return lesseCode;
	}

	public void setLesseCode(String lesseCode) {
		this.lesseCode = lesseCode;
	}

	public String getLesseRate() {
		return lesseRate;
	}

	public void setLesseRate(String lesseRate) {
		this.lesseRate = lesseRate;
	}

	public String getIndustryInvestment() {
		return industryInvestment;
	}

	public void setIndustryInvestment(String industryInvestment) {
		this.industryInvestment = industryInvestment;
	}

	public String getBackgroundNationality() {
		return backgroundNationality;
	}

	public void setBackgroundNationality(String backgroundNationality) {
		this.backgroundNationality = backgroundNationality;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getIouCode() {
		return iouCode;
	}

	public void setIouCode(String iouCode) {
		this.iouCode = iouCode;
	}

	public String getFinancePlatform() {
		return financePlatform;
	}

	public void setFinancePlatform(String financePlatform) {
		this.financePlatform = financePlatform;
	}

	public String getRenFileName() {
		return renFileName;
	}

	public void setRenFileName(String renFileName) {
		this.renFileName = renFileName;
	}

	public BizRentalFactoring() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("grantCode", grantCode)
				.add("leasehold", leasehold)
				.add("bizRentalFactoringCode", bizRentalFactoringCode)
				.add("lesseName", lesseName)
				.add("lesseCode", lesseCode)
				.add("lesseRate", lesseRate)
				.add("industryInvestment", industryInvestment)
				.add("backgroundNationality", backgroundNationality)
				.add("repaymentType", repaymentType)
				.add("iouCode", iouCode)
				.add("financePlatform", financePlatform)
				.toString();
	}
}
