/**
 * 
 */
package org.ibase4j.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 功能：债项发放申请
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_DEBT_GRANT")
public class BizDebtGrant extends BaseModel implements Serializable {
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
	 * 发放债项申请编号
	 */
	@TableField("GRANT_CODE")
	private String grantCode;
	/**
	 * 发放日期
	 */
	@TableField("SEND_DATE")
	private Date sendDate;
	/**
	 * 业务期限
	 */
	@TableField("SCOPE_BUSIN_PERIOD")
	private String scopeBusinPeriod;
	/**
	 * 宽限期
	 */
	@TableField("GRACE_PERIOD")
	private Integer gracePeriod;
	/**
	 * 容忍期
	 */
	@TableField("TOIERANCE_PERIOD")
	private Integer toierancePeriod;
	/**
	 * 债项发放状态
	 */
	@TableField("GRANT_STATUS")
	private Integer grantStatus;
	/**
	 * 产品专有信息外键
	 */
	@TableField("PROPER_INFO")
	private Long properInfo;
	
	/**
	 * 产品种类名称
	 */
	@TableField(exist=false)
	private String businessTypeName;
	/**
	 * 经办机构编码
	 */
	@TableField("INSTITUTION_CODE")
	private String institutionCode;
	/**
	 * 方案综合报价描述
	 */
	@TableField("DESCRIPTION_PROGRAM_QUOQATE")
	private String descriptionProgramQuoqate;

	/**
	 * 方案利率规则描述
	 */
	@TableField("DESCRIPTION_RATE_RULES")
	private String descriptionRateRules;
	
	/**
	 * 变更之前的流水号
	 */
	@TableField("ORIGINAL_GRANT_CODE")
	private String originalGrantCode;

	/**
	 * 申请人
	 */
	@TableField("PROPOSER")
	private String proposer;

	/**
	 * 币种
	 */
	@TableField("CURRENCY")
	private String currency;

	/**
	 * 发放金额
	 */
	@TableField("GRANT_AMT")
	private BigDecimal grantAmt;

	/**
	 * 产品名称
	 */
	@TableField("BUSINESS_NAME")
	private String businessName;

	/**
	 * 借据编号
	 */
	@TableField("IOU_CODE")
	private String iouCode;

	/**
	 * 介质识别号
	 */
	@TableField("IDENT_NUMBER")
	private String identNumber;

	/**
	 * 许可证日期
	 */
	@TableField("LICEDATE")
	private String liceDate;

	/**
	 * 许可证序号
	 */
	@TableField("SEQNO")
	private String seqNo;

	/**
	 * 流程审核状态
	 */
	@TableField("PROCESS_STATUS")
	private Integer processStatus;
	/**
	 * 发放审核历史数据迁移状态(0未提交；1生效)
	 */
	@TableField("HISTORY_STATE")
	private String historyState;

	/**
	 * 失效时间
	 */
	@TableField("END_DATE")
	private Date endDate;

	/**
	 * 发放审核通过时间
	 */
	@TableField("PASS_DATE")
	private Date passDate;
	
	/***
	 * 经办人部门名称
	 */
	@TableField(exist=false)
	private String deptName;
	/***
	 * 经办人姓名
	 */
	@TableField(exist=false)
	private String createByName;
	/***
	 * 币种中文
	 */
	@TableField(exist=false)
	private String currencyName;
	/**
	 * 说明：金额字符串，主要解决18位之后缺少精度问题
	 * 日期：2018/12/18
	 */
	@TableField(exist=false)
	private String grantAmtStr;

	public String getIdentNumber() {
		return identNumber;
	}

	public void setIdentNumber(String identNumber) {
		this.identNumber = identNumber;
	}

	public String getLiceDate() {
		return liceDate;
	}

	public void setLiceDate(String liceDate) {
		this.liceDate = liceDate;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getScopeBusinPeriod() {
		return scopeBusinPeriod;
	}

	public void setScopeBusinPeriod(String scopeBusinPeriod) {
		this.scopeBusinPeriod = scopeBusinPeriod;
	}

	public Integer getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(Integer gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public Integer getToierancePeriod() {
		return toierancePeriod;
	}

	public void setToierancePeriod(Integer toierancePeriod) {
		this.toierancePeriod = toierancePeriod;
	}

	public Integer getGrantStatus() {
		return grantStatus;
	}

	public void setGrantStatus(Integer grantStatus) {
		this.grantStatus = grantStatus;
	}

	public Long getProperInfo() {
		return properInfo;
	}

	public void setProperInfo(Long properInfo) {
		this.properInfo = properInfo;
	}
	
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	
	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getDescriptionProgramQuoqate() {
		return descriptionProgramQuoqate;
	}

	public void setDescriptionProgramQuoqate(String descriptionProgramQuoqate) {
		this.descriptionProgramQuoqate = descriptionProgramQuoqate;
	}

	public String getDescriptionRateRules() {
		return descriptionRateRules;
	}

	public void setDescriptionRateRules(String descriptionRateRules) {
		this.descriptionRateRules = descriptionRateRules;
	}

	public String getOriginalGrantCode() {
		return originalGrantCode;
	}

	public void setOriginalGrantCode(String originalGrantCode) {
		this.originalGrantCode = originalGrantCode;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getGrantAmt() {
		return grantAmt;
	}

	public void setGrantAmt(BigDecimal grantAmt) {
		this.grantAmt = grantAmt;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getIouCode() {
		return iouCode;
	}

	public void setIouCode(String iouCode) {
		this.iouCode = iouCode;
	}

	public String getHistoryState() {
		return historyState;
	}

	public void setHistoryState(String historyState) {
		this.historyState = historyState;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	public String getGrantAmtStr() {
		if(grantAmt!=null){
			DecimalFormat numberFormat = new DecimalFormat(",###.00");
			return numberFormat.format(grantAmt);
		}
		return "0";
	}

	public void setGrantAmtStr(String grantAmtStr) {
		this.grantAmtStr = grantAmtStr;
	}

	public BizDebtGrant() {
	}


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("grantCode", grantCode)
				.add("sendDate", sendDate)
				.add("scopeBusinPeriod", scopeBusinPeriod)
				.add("gracePeriod", gracePeriod)
				.add("toierancePeriod", toierancePeriod)
				.add("grantStatus", grantStatus)
				.add("properInfo", properInfo)
				.add("businessTypeName", businessTypeName)
				.add("institutionCode", institutionCode)
				.add("descriptionProgramQuoqate", descriptionProgramQuoqate)
				.add("descriptionRateRules", descriptionRateRules)
				.add("originalGrantCode", originalGrantCode)
				.add("proposer", proposer)
				.add("currency", currency)
				.add("grantAmt", grantAmt)
				.add("businessName", businessName)
				.add("iouCode", iouCode)
				.add("identNumber", identNumber)
				.add("liceDate", liceDate)
				.add("seqNo", seqNo)
				.add("processStatus", processStatus)
				.add("historyState", historyState)
				.add("endDate", endDate)
				.add("passDate", passDate)
				.add("deptName", deptName)
				.add("createByName", createByName)
				.add("currencyName", currencyName)
				.add("grantAmtStr", grantAmtStr)
				.toString();
	}
}
