package org.ibase4j.vo;

import org.ibase4j.model.BizCust;
import org.ibase4j.model.BizFEC;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：摘要信息
 * 
 * @author xiaoshuiquan
 * @version 1.0
 */
public class SumInformationVo implements Serializable{

    /**
     * 用信主体
     */
    private List<BizCust> custList;

    /**
     * 利率
     */
    private List<BizFEC> fecList;
    /**
     * 产品组合
     */
    private String proName;
    /**
     * 债项方案总金额
     */
    private BigDecimal totalAmont;
    /**
     * 债项方案已批未放金额
     */
    private BigDecimal noFoundAmont;
    /**
     * 政策性分类
     */
    private Long classificPolicies;
    /**
     * 政策性描述
     */
    private String policiesDescribe;
    /**
     * 汇总期限
     */
    private Long summaryPeriod;
    /**
     * 汇总费率
     */
    private BigDecimal summaryRates;

    /**
     * 汇总费率最低值
     */
    private BigDecimal rateRangeMix;

    /**
     * 汇总费率最高值
     */
    private BigDecimal rateRangeMax;

    /**
     * 主币种
     */
    private String mpc;

    /**
     * 主币种中文
     */
    private String mpcName;

    /**
     * 方案辅助币种
     */
    private String aCurrrency;

    /**
     * 方案其他币种
     */
    private String oc;

    /**
     * 利率描述
     */
    private String aggregateRates;
    /**
     * 方案状态
     */
    private Integer solutionState;
    /**
     * 方案审核状态
     */
    private Integer enable;

    /**
     * 合同创建状态
     *
     */
    private String remark;
    /**
     * 返回信息编码
     *
     */
    private String errNo;
    /**
     * 返回信息编码中文
     *
     */
    private String errorMessage;

    /**
     * 交易状态
     *
     */
    private String transok;

    /**
     * 流程审核状态
     *
     */
    private Integer processStatus;

    /**
     * 介质识别号
     *
     */
    private String identNumber;



    public List<BizCust> getCustList() {
        return custList;
    }

    public void setCustList(List<BizCust> custList) {
        this.custList = custList;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public BigDecimal getTotalAmont() {
        return totalAmont;
    }

    public void setTotalAmont(BigDecimal totalAmont) {
        this.totalAmont = totalAmont;
    }

    public BigDecimal getNoFoundAmont() {
        return noFoundAmont;
    }

    public void setNoFoundAmont(BigDecimal noFoundAmont) {
        this.noFoundAmont = noFoundAmont;
    }

    public Long getClassificPolicies() {
        return classificPolicies;
    }

    public void setClassificPolicies(Long classificPolicies) {
        this.classificPolicies = classificPolicies;
    }

    public String getPoliciesDescribe() {
        return policiesDescribe;
    }

    public void setPoliciesDescribe(String policiesDescribe) {
        this.policiesDescribe = policiesDescribe;
    }

    public Long getSummaryPeriod() {
        return summaryPeriod;
    }

    public void setSummaryPeriod(Long summaryPeriod) {
        this.summaryPeriod = summaryPeriod;
    }

    public BigDecimal getSummaryRates() {
        return summaryRates;
    }

    public void setSummaryRates(BigDecimal summaryRates) {
        this.summaryRates = summaryRates;
    }

    public String getAggregateRates() {
        return aggregateRates;
    }

    public void setAggregateRates(String aggregateRates) {
        this.aggregateRates = aggregateRates;
    }


    public Integer getSolutionState() {
        return solutionState;
    }

    public void setSolutionState(Integer solutionState) {
        this.solutionState = solutionState;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTransok() {
        return transok;
    }

    public void setTransok(String transok) {
        this.transok = transok;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
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

    public String getMpc() {
        return mpc;
    }

    public void setMpc(String mpc) {
        this.mpc = mpc;
    }

    public String getaCurrrency() {
        return aCurrrency;
    }

    public void setaCurrrency(String aCurrrency) {
        this.aCurrrency = aCurrrency;
    }

    public String getOc() {
        return oc;
    }

    public void setOc(String oc) {
        this.oc = oc;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public String getMpcName() {
        return mpcName;
    }

    public void setMpcName(String mpcName) {
        this.mpcName = mpcName;
    }

    public List<BizFEC> getFecList() {
        return fecList;
    }

    public void setFecList(List<BizFEC> fecList) {
        this.fecList = fecList;
    }
}
