package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;
import org.ibase4j.vo.ProductVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Set;

/**
 * 描述：债项概要
 * 
 * @author xiaoshuiquan
 * @date 2018/05/26
 */

@TableName("BIZ_DEBT_MAIN")
public class BizDebtSummary extends BaseModel implements Serializable {

	/**
	 * 债项方案id
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 项目名称
	 */
	@TableField("PROJECT_NAME")
	private String projectName;
	/**
	 * 柜员id
	 */
	@TableField("BANK_TELL_ID")
	private Long bankTellerId;
	/**
	 * 流程发起时间
	 */
	@TableField("WF_TIME")
	private Date processInitiatTime;
	/**
	 * 效期循环规则
	 */
	@TableField("VALIDITY_CYCLE_GAUGE")
	private Long validityCcycleGauge;
	/**
	 * 方案生效日期
	 */
	@TableField("PG_EFFECTIVE_DATE")
	private Date pgEffectivDate;
	/**
	 * 方案失效日期
	 */
	@TableField("PG_EXPI_DATE")
	private Date pgExpiDate;
	/**
	 * 方案循环标志
	 */
	@TableField("LOOP_FLAG")
	private Long ls;
	/**
	 * 产品规则
	 */
	@TableField("RULE_TYPE")
	private Long ruleType;
	/**
	 * 可办理笔数限制
	 */
	@TableField("LTNOPA")
	private Long ltnopa;
	/**
	 * 可办理笔数
	 */
	@TableField("TDWLN")
	private Long tdwln;
	/**
	 * 方案主币种
	 */
	@TableField("M_CURRENCY")
	private String mpc;
	/**
	 * 方案金额
	 */
	@TableField("SOLUTION_AMT")
	private BigDecimal solutionAmount;
	
	/**
	 * 方案辅助币种(0无1其他可选币种2等值其他币种)
	 */
	@TableField("A_CURRENCY")
	private String aCurrrency;
	/**
	 * 其他可选币种
	 */
	@TableField("O_CURRENCY")
	private String oc;
	/**
	 * 方案业务期限范围
	 */
	@TableField("SCOPE_BUSIN_PERIOD")
	private Long dopo;
	/**
	 * 方案费率类型
	 */
	@TableField("PROG_RATE_TYPE")
	private Long progRateType;
	/**
	 * 方案费率形式
	 */
	@TableField("SCHEME_RATE_FORM")
	private Long schemeRateForm;
	/**
	 * 方案费率范围最低值
	 */
	@TableField("RATE_RANGE_MIX")
	private BigDecimal rateRangeMix;
	/**
	 * 方案费率范围最高值
	 */
	@TableField("RATE_RANGE_MAX")
	private BigDecimal rateRangeMax;
	/**
	 * 方案费率
	 */
	@TableField("PACKAGE_RATE")
	private BigDecimal packageRate;
	/**
	 * 是否经审批
	 */
	@TableField("APPROVE")
	private Long approve;
	/**
	 * 费率审批机构
	 */
	@TableField("RATE_APPROVAL_ORG")
	private Long raaa;
	/**
	 * 是否存在方案费率折扣
	 */
	@TableField("WHETHER_RATE_DISCOUNT")
	private Long whetherRateDiscount;
	/**
	 * 方案费率折扣
	 */
	@TableField("PROGRAM_RATE_DISCOUNT")
	private BigDecimal programRateDiscount;
	/**
	 * 方案综合报价描述
	 */
	@TableField("DESCRIPTION_PROGRAM_QUOQATE")
	private String descriptionProgramQuoqate;

	/**	 * 利率规则描述
	 */
	@TableField("DESCRIPTION_RATE_RULES")
	private String descriptionRateRules;
	/**
	 * 交易背景概况
	 */
	@TableField("BRIF_BACKGROUND")
	private String brifBackground;
	/**
	 * 交易背景
	 */
	@TableField("TRAD_BACKGROUND")
	private String tradBackground;
	/**
	 * 业务背景简述
	 */
	@TableField("BUSINESS_BACKGROUND_BRIEF")
	private String businessBackgroundBrief;
	/**
	 * 货物/服务情况简述
	 */
	@TableField("GOODS_SKETCH")
	private String goodsSketch;
	/**
	 * 政策性属性描述
	 */
	@TableField("POLICY_DESCRIPTION")
	private String policyDescription;
	/**
	 * 是否是政策性
	 */
	@TableField("POLICY")
	private String policy;
	/**
	 * 申请人
	 */
	@TableField("PROPOSER")
	private String proposer;
	/**
	 * 申请人客户号
	 */
	@TableField("PROPOSER_NUM")
	private String proposerNum;
	/**
	 * 是否单笔单批
	 */
	@TableField("SINGLE_BATCH")
	private Long singleBtch;
	/**
	 * 经办机构编码
	 */
	@TableField("INSTITUTION_CODE")
	private String institutionCode;
	/**
	 * 变更前的债项方案id
	 */
	@TableField("DEBT_TRN_CODE")
	private String debtTrnCode;
	/**
	 * 说明：追加介质识别号
	 * 作者：于连奇
	 * 日期：2018/10/10
	 */
	@TableField("IDENT_NUMBER")
	private String identNumber;
	/**
	 * 说明：追加信息码
	 * 作者：于连奇
	 * 日期：2018/10/11
	 */
	@TableField("ERR_NO")
	private String errNo;
	/**
	 * 说明：追加交易状态
	 * 作者：于连奇
	 * 日期：2018/10/22
	 */
	@TableField("TRANSOK")
	private String transok;
	/**
	 * 说明：追加审核流程状态
	 * 作者：于连奇
	 * 日期：2018/10/22
	 */
	@TableField("PROCESS_STATUS")
	private Integer processStatus;
	/**
	 * 说明：历史迁移数据状态
	 * 作者：肖水泉
	 * 日期：2018/10/27
	 */
	@TableField("HISTORY_STATE")
	private String historyState;

	/**
	 * 说明：追加方案状态
	 * 作者：陈志明
	 * 日期：2018/7/12
	 * 1、系统根据该债项方案的审批状态，自动显示；
	 * 2、方案状态：可发放/过期。状态类型，参见方案状态及方案审核状态
	 * 3、灰显，不可修改
	 */
	@TableField("SOLUTION_STATE")
	private Integer solutionState;
	/**
	 * 说明：追加产品名称组合
	 * 作者：陈志明
	 * 日期：2018/7/12
	 */
	@TableField(exist=false)
	private Set<ProductVo> productSet;
	
	/**
	 * 说明：方案金额字符串
	 * 作者：陈志明
	 * 日期：2018/12/18
	 */
	@TableField(exist=false)
	private String solutionAmountStr;
	/**
	 * 说明：所属机构
	 * 作者：肖水泉
	 * 日期：2018/10/22
	 */
	@TableField(exist=false)
	private String deptName;

	public String getHistoryState() {
		return historyState;
	}

	public void setHistoryState(String historyState) {
		this.historyState = historyState;
	}

	public String getDebtTrnCode() {
		return debtTrnCode;
	}

	public void setDebtTrnCode(String debtTrnCode) {
		this.debtTrnCode = debtTrnCode;
	}

	public String getDescriptionProgramQuoqate() {
		return descriptionProgramQuoqate;
	}

	public void setDescriptionProgramQuoqate(String descriptionProgramQuoqate) {
		this.descriptionProgramQuoqate = descriptionProgramQuoqate;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getProposerNum() {
		return proposerNum;
	}

	public void setProposerNum(String proposerNum) {
		this.proposerNum = proposerNum;
	}

	public Long getSingleBtch() {
		return singleBtch;
	}

	public void setSingleBtch(Long singleBtch) {
		this.singleBtch = singleBtch;
	}

	public Long getLtnopa() {
		return ltnopa;
	}

	public void setLtnopa(Long ltnopa) {
		this.ltnopa = ltnopa;
	}

	public Long getTdwln() {
		return tdwln;
	}

	public void setTdwln(Long tdwln) {
		this.tdwln = tdwln;
	}

	public String getMpc() {
		return mpc;
	}

	public void setMpc(String mpc) {
		this.mpc = mpc;
	}

	public String getOc() {
		return oc;
	}

	public void setOc(String oc) {
		this.oc = oc;
	}

	public BigDecimal getSolutionAmount() {
		return solutionAmount;
	}

	public void setSolutionAmount(BigDecimal solutionAmount) {
		this.solutionAmount = solutionAmount;
	}

	public Long getDopo() {
		return dopo;
	}

	public void setDopo(Long dopo) {
		this.dopo = dopo;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}



	public String getPolicyDescription() {
		return policyDescription;
	}

	public void setPolicyDescription(String policyDescription) {
		this.policyDescription = policyDescription;
	}

	public Long getWhetherRateDiscount() {
		return whetherRateDiscount;
	}

	public void setWhetherRateDiscount(Long whetherRateDiscount) {
		this.whetherRateDiscount = whetherRateDiscount;
	}


	public Long getRaaa() {
		return raaa;
	}

	public void setRaaa(Long raaa) {
		this.raaa = raaa;
	}

	public BigDecimal getRateRangeMix() {
		return rateRangeMix;
	}

	public void setRateRangeMix(BigDecimal rateRangeMix) {
		this.rateRangeMix = rateRangeMix;
	}

	public BigDecimal getRateRangeMax() {
		return rateRangeMax;
	}

	public void setRateRangeMax(BigDecimal rateRangeMax) {
		this.rateRangeMax = rateRangeMax;
	}

	public BigDecimal getPackageRate() {
		return packageRate;
	}

	public void setPackageRate(BigDecimal packageRate) {
		this.packageRate = packageRate;
	}

	public BigDecimal getProgramRateDiscount() {
		return programRateDiscount;
	}

	public void setProgramRateDiscount(BigDecimal programRateDiscount) {
		this.programRateDiscount = programRateDiscount;
	}

	public Long getProgRateType() {
		return progRateType;
	}

	public void setProgRateType(Long progRateType) {
		this.progRateType = progRateType;
	}

	public Date getPgEffectivDate() {
		return pgEffectivDate;
	}

	public void setPgEffectivDate(Date pgEffectivDate) {
		this.pgEffectivDate = pgEffectivDate;
	}

	public Date getPgExpiDate() {
		return pgExpiDate;
	}

	public void setPgExpiDate(Date pgExpiDate) {
		this.pgExpiDate = pgExpiDate;
	}

	public Long getLs() {
		return ls;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getBankTellerId() {
		return bankTellerId;
	}

	public void setBankTellerId(Long bankTellerId) {
		this.bankTellerId = bankTellerId;
	}

	public Date getProcessInitiatTime() {
		return processInitiatTime;
	}

	public void setProcessInitiatTime(Date processInitiatTime) {
		this.processInitiatTime = processInitiatTime;
	}

	public Long getValidityCcycleGauge() {
		return validityCcycleGauge;
	}

	public void setValidityCcycleGauge(Long validityCcycleGauge) {
		this.validityCcycleGauge = validityCcycleGauge;
	}

	public void setLs(Long ls) {
		this.ls = ls;
	}

	public Long getRuleType() {
		return ruleType;
	}

	public void setRuleType(Long ruleType) {
		this.ruleType = ruleType;
	}

	public String getaCurrrency() {
		return aCurrrency;
	}

	public void setaCurrrency(String aCurrrency) {
		this.aCurrrency = aCurrrency;
	}

	public Long getSchemeRateForm() {
		return schemeRateForm;
	}

	public void setSchemeRateForm(Long schemeRateForm) {
		this.schemeRateForm = schemeRateForm;
	}

	public Long getApprove() {
		return approve;
	}

	public void setApprove(Long approve) {
		this.approve = approve;
	}

	public String getDescriptionRateRules() {
		return descriptionRateRules;
	}

	public void setDescriptionRateRules(String descriptionRateRules) {
		this.descriptionRateRules = descriptionRateRules;
	}

	public String getBrifBackground() {
		return brifBackground;
	}

	public void setBrifBackground(String brifBackground) {
		this.brifBackground = brifBackground;
	}

	public String getTradBackground() {
		return tradBackground;
	}

	public void setTradBackground(String tradBackground) {
		this.tradBackground = tradBackground;
	}

	public String getBusinessBackgroundBrief() {
		return businessBackgroundBrief;
	}

	public void setBusinessBackgroundBrief(String businessBackgroundBrief) {
		this.businessBackgroundBrief = businessBackgroundBrief;
	}

	public String getGoodsSketch() {
		return goodsSketch;
	}

	public void setGoodsSketch(String goodsSketch) {
		this.goodsSketch = goodsSketch;
	}

	public Integer getSolutionState() {
		return solutionState;
	}

	public void setSolutionState(Integer solutionState) {
		this.solutionState = solutionState;
	}

	public Set<ProductVo> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<ProductVo> productSet) {
		this.productSet = productSet;
	}


	public String getIdentNumber() {
		return identNumber;
	}

	public void setIdentNumber(String identNumber) {
		this.identNumber = identNumber;
	}

	public String getErrNo() {
		return errNo;
	}

	public void setErrNo(String errNo) {
		this.errNo = errNo;
	}

	public String getTransok() {
		return transok;
	}

	public void setTransok(String transok) {
		this.transok = transok;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public String getSolutionAmountStr() {
		if(solutionAmount!=null){
			DecimalFormat numberFormat = new DecimalFormat(",###.00");
			return numberFormat.format(solutionAmount);
		}
		return "0";
	}

	public void setSolutionAmountStr(String solutionAmountStr) {
		this.solutionAmountStr = solutionAmountStr;
	}

    public BizDebtSummary() {
    }

    @Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("projectName", projectName)
				.add("bankTellerId", bankTellerId)
				.add("processInitiatTime", processInitiatTime)
				.add("validityCcycleGauge", validityCcycleGauge)
				.add("pgEffectivDate", pgEffectivDate)
				.add("pgExpiDate", pgExpiDate)
				.add("ls", ls)
				.add("ruleType", ruleType)
				.add("ltnopa", ltnopa)
				.add("tdwln", tdwln)
				.add("mpc", mpc)
				.add("solutionAmount", solutionAmount)
				.add("aCurrrency", aCurrrency)
				.add("oc", oc)
				.add("dopo", dopo)
				.add("progRateType", progRateType)
				.add("schemeRateForm", schemeRateForm)
				.add("rateRangeMix", rateRangeMix)
				.add("rateRangeMax", rateRangeMax)
				.add("packageRate", packageRate)
				.add("approve", approve)
				.add("raaa", raaa)
				.add("whetherRateDiscount", whetherRateDiscount)
				.add("programRateDiscount", programRateDiscount)
				.add("descriptionProgramQuoqate", descriptionProgramQuoqate)
				.add("descriptionRateRules", descriptionRateRules)
				.add("brifBackground", brifBackground)
				.add("tradBackground", tradBackground)
				.add("businessBackgroundBrief", businessBackgroundBrief)
				.add("goodsSketch", goodsSketch)
				.add("policyDescription", policyDescription)
				.add("policy", policy)
				.add("proposer", proposer)
				.add("proposerNum", proposerNum)
				.add("singleBtch", singleBtch)
				.add("institutionCode", institutionCode)
				.add("debtTrnCode", debtTrnCode)
				.add("identNumber", identNumber)
				.add("errNo", errNo)
				.add("transok", transok)
				.add("processStatus", processStatus)
				.add("historyState", historyState)
				.add("solutionState", solutionState)
				.add("productSet", productSet)
				.add("solutionAmountStr", solutionAmountStr)
				.add("deptName", deptName)
				.toString();
	}
}