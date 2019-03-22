package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述：政策属性信息息
 *
 * @author xiaoshuiquan
 * @date 2018/07/17
 */

@TableName("BIZ_INTEREST_RATE")
@SuppressWarnings("serial")
public class BizInterestRate extends BaseModel implements Serializable {

	/**
	 * 业务编号
	 */
	@TableField("DEBT_CODE")
	private String debtNum;
    /**
     * 发放编码
     */
    @TableField("GRANT_CODE")
    private String grantCode;
	/**
	 * 贸金业务政策性属性
	 */
	@TableField("TRADE_FINANCE_BUSINESS")
	private Long traneFinanceBusiness;
	/**
	 * 政策性贷款业务状态
	 */
	@TableField("POLICY_LENDING_STATUS")
	private Long policyLendingStatus;
	/**
	 * 政策性属性描述
	 */
	@TableField("POLICY_DESCRIPTION")
	private String policyDescription;
	/**
	 * 是否已有信贷贷款编号
	 */
	@TableField("LOAN_NUMBER_AVAILABLE")
	private Long loanNumberAvailable;
	/**
	 * 信贷贷款合同编号
	 */
	@TableField("CREDIT_LOAN_CONTRACT_NUM")
	private String creditLoanNum;

	/**
	 * 贷款性质
	 */
	@TableField("NATURE_LOAN")
	private Long naturnLoan;
	/**
	 * 支农投向
	 */
	@TableField("COMPARE")
	private String compare;
	/**
	 * 是否“两自一高”
	 */
	@TableField("TWO_HEAD_TALLER")
	private Long twoHeadTaller;
	/**
	 * 是否国家特定贷款
	 */
	@TableField("CONTRY_SPECIFIC_LOANS")
	private Long contrySpecificLoans;
	/**
	 * 是否中小企业贷款
	 */
	@TableField("MEDIUM_ENTERPRISE_LOANS")
	private Long mediumEnterpriseLoans;
	/**
	 * 是否船舶融资
	 */
	@TableField("SHIP_FINANCE")
	private Long shipFinance;
	/**
	 * 产业结构调整类型
	 */
	@TableField("TYOES_INDUSTRIAL")
	private Long tyoesIndustrial;
	/**
	 * 工业转型升级标识
	 */
	@TableField("INDUSTRIAL_TRANSFORMATION")
	private Long industrialTransformation;
	/**
	 * 战略新兴产业类型
	 */
	@TableField("STRATEGIC_EMERG")
	private Long strategicEmerg;
	/**
	 * 文化产品标识
	 */
	@TableField("CURTURAL_PRODUCT")
	private Long curturalProduct;
	/**
	 * 是否精准扶贫贷款
	 */
	@TableField("ALLEVIATION_LOAN")
	private Long alleviationLoan;

	/**
	 * 贸金业务政策性属性
	 */
	@TableField("TRADE_INTEREST_RATE")
	private Integer tradeInterestRate;

	/**
	 * 政策性贷款业务状态
	 */
	@TableField("POLICY_ATTRIBUTE_STATE")
	private Integer policyAttributeState;

	/**
	 * 是否已有信贷贷款编号
	 */
	@TableField("CREDIT_LOAN_NO")
	private Integer creditLoanNo;

	/**
	 * 项目名称
	 */
	@TableField("PROJECT_NAME")
	private String projectName;

	/**
	 * 产业精准扶贫贷款带动人数/项目精准扶贫贷款服务人数
	 */
	@TableField("LOAN_SERVICE_PEOPLE_NUM")
	private Long loanServicePeopleNum;

	/**
	 * 精准扶贫贷款资金来源
	 */
	@TableField("LOAN_FUND_RESOURCE")
	private String loanFunResource;

	/**
	 * 精准扶贫主体性质
	 */
	@TableField("POVERTY_PROPERTY")
	private String povertyProperty;
	/**
	 * 精准扶贫项目所在地区
	 */
	@TableField("POVERTY_ADDRESS")
	private String povertyAddress;

	/**
	 * 精准扶贫项目类别
	 */
	@TableField("POVERTY_SORT")
	private String povertySort;

	/**
	 * 商务合同编码
	 */
	@TableField("BUSINESS_CONTRACT_CODE")
	private String businessContractCode;

	/**
	 * 商务合同签订日期
	 */
	@TableField("BUSINESS_CONTRACT_DATE")
	private Date businessContractDate;

	/**
	 * 商务合同金额
	 */
	@TableField("BUSINESS_CONTRACT_AMOUNT")
	private BigDecimal businessContractAmount;

	/**
	 * 产品类型
	 */
	@TableField("BUSINESS_TYPE")
	private String businessType;

	/**
	 * 创新业务类型
	 */
	@TableField("INNOVATIVE_BUSINESS_TYPE")
	private String innovativeBusinessType;

	/**
	 * 是否境外投向
	 */
	@TableField("IS_OVERSEAS_TO")
	private String inOverseasTo;

	/**
	 * 是否421
	 */
	@TableField("IS_421")
	private Long is421;

	/**
	 * 贷款领域
	 */
	@TableField("LOAN_DOMAIN")
	private String loanDomain;

	/**
	 * 进出口货物及服务
	 */
	@TableField("IMPOERT_EXPORT_GOODS_SERVICE")
	private String importExportGoodsService;

	/**
	 * 对外投资贷款分类
	 */
	@TableField("LOAN_CKASSIFICATION")
	private String investmentLoanCkassifcation;

	/**
	 * 是否银团
	 */
	@TableField("IS_SYNDICATED")
	private Integer isSyndicated;

	/**
	 * 是否银团代理行
	 */
	@TableField("IS_SYNDICATED_AGENCY")
	private Integer isSyndicatedAgency;

	/**
	 * 我行银团地位
	 */
	@TableField("SYNDICATED_STATUS")
	private Long syndicatedStatus;

	/**
	 * 行业投向
	 */
	@TableField("INDUSTRY_INVESTMENT")
	private String industryInvestment;

	/**
	 * 背景国别
	 */
	@TableField("BACKGROUND_NATIONALITY")
	private String backgroundNationality;

	/**
	 * 中国制造2025及战略新兴产业分类
	 */
	@TableField("EMERGING_INDUSTRY_CLASSIFY")
	private String emergingIndustryClassify;

	/**
	 * 政策性属性分类
	 */
	@TableField("POLICY_ATTRIBUTRE_CLASSIFY")
	private String policyAttributeClassify;

	public String getDebtNum() {
		return debtNum;
	}

	public void setDebtNum(String debtNum) {
		this.debtNum = debtNum;
	}

	public Long getTraneFinanceBusiness() {
		return traneFinanceBusiness;
	}

	public void setTraneFinanceBusiness(Long traneFinanceBusiness) {
		this.traneFinanceBusiness = traneFinanceBusiness;
	}

	public Long getPolicyLendingStatus() {
		return policyLendingStatus;
	}

	public void setPolicyLendingStatus(Long policyLendingStatus) {
		this.policyLendingStatus = policyLendingStatus;
	}

	public String getPolicyDescription() {
		return policyDescription;
	}

	public void setPolicyDescription(String policyDescription) {
		this.policyDescription = policyDescription;
	}

	public Long getLoanNumberAvailable() {
		return loanNumberAvailable;
	}

	public void setLoanNumberAvailable(Long loanNumberAvailable) {
		this.loanNumberAvailable = loanNumberAvailable;
	}

	public String getCreditLoanNum() {
		return creditLoanNum;
	}

	public void setCreditLoanNum(String creditLoanNum) {
		this.creditLoanNum = creditLoanNum;
	}

	public Long getNaturnLoan() {
		return naturnLoan;
	}

	public void setNaturnLoan(Long naturnLoan) {
		this.naturnLoan = naturnLoan;
	}

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public Long getTwoHeadTaller() {
		return twoHeadTaller;
	}

	public void setTwoHeadTaller(Long twoHeadTaller) {
		this.twoHeadTaller = twoHeadTaller;
	}

	public Long getContrySpecificLoans() {
		return contrySpecificLoans;
	}

	public void setContrySpecificLoans(Long contrySpecificLoans) {
		this.contrySpecificLoans = contrySpecificLoans;
	}

	public Long getMediumEnterpriseLoans() {
		return mediumEnterpriseLoans;
	}

	public void setMediumEnterpriseLoans(Long mediumEnterpriseLoans) {
		this.mediumEnterpriseLoans = mediumEnterpriseLoans;
	}

	public Long getShipFinance() {
		return shipFinance;
	}

	public void setShipFinance(Long shipFinance) {
		this.shipFinance = shipFinance;
	}

	public Long getTyoesIndustrial() {
		return tyoesIndustrial;
	}

	public void setTyoesIndustrial(Long tyoesIndustrial) {
		this.tyoesIndustrial = tyoesIndustrial;
	}

	public Long getIndustrialTransformation() {
		return industrialTransformation;
	}

	public void setIndustrialTransformation(Long industrialTransformation) {
		this.industrialTransformation = industrialTransformation;
	}

	public Long getStrategicEmerg() {
		return strategicEmerg;
	}

	public void setStrategicEmerg(Long strategicEmerg) {
		this.strategicEmerg = strategicEmerg;
	}

	public Long getCurturalProduct() {
		return curturalProduct;
	}

	public void setCurturalProduct(Long curturalProduct) {
		this.curturalProduct = curturalProduct;
	}

	public Long getAlleviationLoan() {
		return alleviationLoan;
	}

	public void setAlleviationLoan(Long alleviationLoan) {
		this.alleviationLoan = alleviationLoan;
	}

	public Integer getTradeInterestRate() {
		return tradeInterestRate;
	}

	public void setTradeInterestRate(Integer tradeInterestRate) {
		this.tradeInterestRate = tradeInterestRate;
	}

	public Integer getPolicyAttributeState() {
		return policyAttributeState;
	}

	public void setPolicyAttributeState(Integer policyAttributeState) {
		this.policyAttributeState = policyAttributeState;
	}

	public Integer getCreditLoanNo() {
		return creditLoanNo;
	}

	public void setCreditLoanNo(Integer creditLoanNo) {
		this.creditLoanNo = creditLoanNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getLoanServicePeopleNum() {
		return loanServicePeopleNum;
	}

	public void setLoanServicePeopleNum(Long loanServicePeopleNum) {
		this.loanServicePeopleNum = loanServicePeopleNum;
	}

	public String getLoanFunResource() {
		return loanFunResource;
	}

	public void setLoanFunResource(String loanFunResource) {
		this.loanFunResource = loanFunResource;
	}

	public String getPovertyProperty() {
		return povertyProperty;
	}

	public void setPovertyProperty(String povertyProperty) {
		this.povertyProperty = povertyProperty;
	}

	public String getPovertyAddress() {
		return povertyAddress;
	}

	public void setPovertyAddress(String povertyAddress) {
		this.povertyAddress = povertyAddress;
	}

	public String getPovertySort() {
		return povertySort;
	}

	public void setPovertySort(String povertySort) {
		this.povertySort = povertySort;
	}

	public String getBusinessContractCode() {
		return businessContractCode;
	}

	public void setBusinessContractCode(String businessContractCode) {
		this.businessContractCode = businessContractCode;
	}

	public Date getBusinessContractDate() {
		return businessContractDate;
	}

	public void setBusinessContractDate(Date businessContractDate) {
		this.businessContractDate = businessContractDate;
	}

	public BigDecimal getBusinessContractAmount() {
		return businessContractAmount;
	}

	public void setBusinessContractAmount(BigDecimal businessContractAmount) {
		this.businessContractAmount = businessContractAmount;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getInnovativeBusinessType() {
		return innovativeBusinessType;
	}

	public void setInnovativeBusinessType(String innovativeBusinessType) {
		this.innovativeBusinessType = innovativeBusinessType;
	}

	public String getInOverseasTo() {
		return inOverseasTo;
	}

	public void setInOverseasTo(String inOverseasTo) {
		this.inOverseasTo = inOverseasTo;
	}

	public Long getIs421() {
		return is421;
	}

	public void setIs421(Long is421) {
		this.is421 = is421;
	}

	public String getLoanDomain() {
		return loanDomain;
	}

	public void setLoanDomain(String loanDomain) {
		this.loanDomain = loanDomain;
	}

	public String getImportExportGoodsService() {
		return importExportGoodsService;
	}

	public void setImportExportGoodsService(String importExportGoodsService) {
		this.importExportGoodsService = importExportGoodsService;
	}

	public String getInvestmentLoanCkassifcation() {
		return investmentLoanCkassifcation;
	}

	public void setInvestmentLoanCkassifcation(String investmentLoanCkassifcation) {
		this.investmentLoanCkassifcation = investmentLoanCkassifcation;
	}

	public Integer getIsSyndicated() {
		return isSyndicated;
	}

	public void setIsSyndicated(Integer isSyndicated) {
		this.isSyndicated = isSyndicated;
	}

	public Integer getIsSyndicatedAgency() {
		return isSyndicatedAgency;
	}

	public void setIsSyndicatedAgency(Integer isSyndicatedAgency) {
		this.isSyndicatedAgency = isSyndicatedAgency;
	}

	public Long getSyndicatedStatus() {
		return syndicatedStatus;
	}

	public void setSyndicatedStatus(Long syndicatedStatus) {
		this.syndicatedStatus = syndicatedStatus;
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

    public String getEmergingIndustryClassify() {
		return emergingIndustryClassify;
	}

	public void setEmergingIndustryClassify(String emergingIndustryClassify) {
		this.emergingIndustryClassify = emergingIndustryClassify;
	}

	public String getGrantCode() {
        return grantCode;
    }

    public void setGrantCode(String grantCode) {
        this.grantCode = grantCode;
    }

	public String getPolicyAttributeClassify() {
		return policyAttributeClassify;
	}

	public void setPolicyAttributeClassify(String policyAttributeClassify) {
		this.policyAttributeClassify = policyAttributeClassify;
	}

	public BizInterestRate() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtNum", debtNum)
				.add("grantCode", grantCode)
				.add("traneFinanceBusiness", traneFinanceBusiness)
				.add("policyLendingStatus", policyLendingStatus)
				.add("policyDescription", policyDescription)
				.add("loanNumberAvailable", loanNumberAvailable)
				.add("creditLoanNum", creditLoanNum)
				.add("naturnLoan", naturnLoan)
				.add("compare", compare)
				.add("twoHeadTaller", twoHeadTaller)
				.add("contrySpecificLoans", contrySpecificLoans)
				.add("mediumEnterpriseLoans", mediumEnterpriseLoans)
				.add("shipFinance", shipFinance)
				.add("tyoesIndustrial", tyoesIndustrial)
				.add("industrialTransformation", industrialTransformation)
				.add("strategicEmerg", strategicEmerg)
				.add("curturalProduct", curturalProduct)
				.add("alleviationLoan", alleviationLoan)
				.add("tradeInterestRate", tradeInterestRate)
				.add("policyAttributeState", policyAttributeState)
				.add("creditLoanNo", creditLoanNo)
				.add("projectName", projectName)
				.add("loanServicePeopleNum", loanServicePeopleNum)
				.add("loanFunResource", loanFunResource)
				.add("povertyProperty", povertyProperty)
				.add("povertyAddress", povertyAddress)
				.add("povertySort", povertySort)
				.add("businessContractCode", businessContractCode)
				.add("businessContractDate", businessContractDate)
				.add("businessContractAmount", businessContractAmount)
				.add("businessType", businessType)
				.add("innovativeBusinessType", innovativeBusinessType)
				.add("inOverseasTo", inOverseasTo)
				.add("is421", is421)
				.add("loanDomain", loanDomain)
				.add("importExportGoodsService", importExportGoodsService)
				.add("investmentLoanCkassifcation", investmentLoanCkassifcation)
				.add("isSyndicated", isSyndicated)
				.add("isSyndicatedAgency", isSyndicatedAgency)
				.add("syndicatedStatus", syndicatedStatus)
				.add("industryInvestment", industryInvestment)
				.add("backgroundNationality", backgroundNationality)
				.add("emergingIndustryClassify", emergingIndustryClassify)
				.add("policyAttributeClassify", policyAttributeClassify)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizInterestRate)) return false;
		BizInterestRate that = (BizInterestRate) o;
		return Objects.equal(getDebtNum(), that.getDebtNum()) &&
				Objects.equal(getGrantCode(), that.getGrantCode()) &&
				Objects.equal(getTraneFinanceBusiness(), that.getTraneFinanceBusiness()) &&
				Objects.equal(getPolicyLendingStatus(), that.getPolicyLendingStatus()) &&
				Objects.equal(getPolicyDescription(), that.getPolicyDescription()) &&
				Objects.equal(getLoanNumberAvailable(), that.getLoanNumberAvailable()) &&
				Objects.equal(getCreditLoanNum(), that.getCreditLoanNum()) &&
				Objects.equal(getNaturnLoan(), that.getNaturnLoan()) &&
				Objects.equal(getCompare(), that.getCompare()) &&
				Objects.equal(getTwoHeadTaller(), that.getTwoHeadTaller()) &&
				Objects.equal(getContrySpecificLoans(), that.getContrySpecificLoans()) &&
				Objects.equal(getMediumEnterpriseLoans(), that.getMediumEnterpriseLoans()) &&
				Objects.equal(getShipFinance(), that.getShipFinance()) &&
				Objects.equal(getTyoesIndustrial(), that.getTyoesIndustrial()) &&
				Objects.equal(getIndustrialTransformation(), that.getIndustrialTransformation()) &&
				Objects.equal(getStrategicEmerg(), that.getStrategicEmerg()) &&
				Objects.equal(getCurturalProduct(), that.getCurturalProduct()) &&
				Objects.equal(getAlleviationLoan(), that.getAlleviationLoan()) &&
				Objects.equal(getTradeInterestRate(), that.getTradeInterestRate()) &&
				Objects.equal(getPolicyAttributeState(), that.getPolicyAttributeState()) &&
				Objects.equal(getCreditLoanNo(), that.getCreditLoanNo()) &&
				Objects.equal(getProjectName(), that.getProjectName()) &&
				Objects.equal(getLoanServicePeopleNum(), that.getLoanServicePeopleNum()) &&
				Objects.equal(getLoanFunResource(), that.getLoanFunResource()) &&
				Objects.equal(getPovertyProperty(), that.getPovertyProperty()) &&
				Objects.equal(getPovertyAddress(), that.getPovertyAddress()) &&
				Objects.equal(getPovertySort(), that.getPovertySort()) &&
				Objects.equal(getBusinessContractCode(), that.getBusinessContractCode()) &&
				Objects.equal(getBusinessContractDate(), that.getBusinessContractDate()) &&
				Objects.equal(getBusinessContractAmount(), that.getBusinessContractAmount()) &&
				Objects.equal(getBusinessType(), that.getBusinessType()) &&
				Objects.equal(getInnovativeBusinessType(), that.getInnovativeBusinessType()) &&
				Objects.equal(getInOverseasTo(), that.getInOverseasTo()) &&
				Objects.equal(getIs421(), that.getIs421()) &&
				Objects.equal(getLoanDomain(), that.getLoanDomain()) &&
				Objects.equal(getImportExportGoodsService(), that.getImportExportGoodsService()) &&
				Objects.equal(getInvestmentLoanCkassifcation(), that.getInvestmentLoanCkassifcation()) &&
				Objects.equal(getIsSyndicated(), that.getIsSyndicated()) &&
				Objects.equal(getIsSyndicatedAgency(), that.getIsSyndicatedAgency()) &&
				Objects.equal(getSyndicatedStatus(), that.getSyndicatedStatus()) &&
				Objects.equal(getIndustryInvestment(), that.getIndustryInvestment()) &&
				Objects.equal(getBackgroundNationality(), that.getBackgroundNationality()) &&
				Objects.equal(getEmergingIndustryClassify(), that.getEmergingIndustryClassify()) &&
				Objects.equal(getPolicyAttributeClassify(), that.getPolicyAttributeClassify());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDebtNum(), getGrantCode(), getTraneFinanceBusiness(), getPolicyLendingStatus(), getPolicyDescription(), getLoanNumberAvailable(), getCreditLoanNum(), getNaturnLoan(), getCompare(), getTwoHeadTaller(), getContrySpecificLoans(), getMediumEnterpriseLoans(), getShipFinance(), getTyoesIndustrial(), getIndustrialTransformation(), getStrategicEmerg(), getCurturalProduct(), getAlleviationLoan(), getTradeInterestRate(), getPolicyAttributeState(), getCreditLoanNo(), getProjectName(), getLoanServicePeopleNum(), getLoanFunResource(), getPovertyProperty(), getPovertyAddress(), getPovertySort(), getBusinessContractCode(), getBusinessContractDate(), getBusinessContractAmount(), getBusinessType(), getInnovativeBusinessType(), getInOverseasTo(), getIs421(), getLoanDomain(), getImportExportGoodsService(), getInvestmentLoanCkassifcation(), getIsSyndicated(), getIsSyndicatedAgency(), getSyndicatedStatus(), getIndustryInvestment(), getBackgroundNationality(), getEmergingIndustryClassify(), getPolicyAttributeClassify());
	}
}
