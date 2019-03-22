package org.ibase4j.vo;

import org.ibase4j.model.BizCustomer;
import org.ibase4j.model.BizGuaranteeContract;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：判断角色是否被选中
 * 
 * @author lianwenhao
 * @version 1.0
 */
public class CeshiVo implements Serializable{
	private Long rfLessor;//文件名
	private Long lesse;//文件名
	private String projectName;//文件名
    private String repaymentType;
    //经办人
  	private String attn;
  	//经办人机构
  	private String attnOrg;
    //是否政策性属性
    private String policy;
    //政策性属性描述
    private String policyDescription;
    /**
     * 方案综合报价描述
     */
    private String descriptionProgramQuoqate;

    /**
     * 方案利率规则描述
     */
    private String descriptionRateRules;
    //借据编号
    private String iouCode;

    /**
     * 行业投向
     */
    private String industryInvestment;

    /**
     * 背景国别
     */
    private String backgroundNationality;

    private List<BizCustomer> custUserLetter;

    private List<BizGuaranteeContract> bizGuaranteeContractList;

    public Long getRfLessor() {
        return rfLessor;
    }

    public void setRfLessor(Long rfLessor) {
        this.rfLessor = rfLessor;
    }

    public Long getLesse() {
        return lesse;
    }

    public void setLesse(Long lesse) {
        this.lesse = lesse;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

	public String getAttn() {
		return attn;
	}

	public void setAttn(String attn) {
		this.attn = attn;
	}

	public String getAttnOrg() {
		return attnOrg;
	}

	public void setAttnOrg(String attnOrg) {
		this.attnOrg = attnOrg;
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

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getPolicyDescription() {
        return policyDescription;
    }

    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    public String getIouCode() {
        return iouCode;
    }

    public void setIouCode(String iouCode) {
        this.iouCode = iouCode;
    }

    public List<BizCustomer> getCustUserLetter() {
        return custUserLetter;
    }

    public void setCustUserLetter(List<BizCustomer> custUserLetter) {
        this.custUserLetter = custUserLetter;
    }

    public List<BizGuaranteeContract> getBizGuaranteeContractList() {
        return bizGuaranteeContractList;
    }

    public void setBizGuaranteeContractList(List<BizGuaranteeContract> bizGuaranteeContractList) {
        this.bizGuaranteeContractList = bizGuaranteeContractList;
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
}
