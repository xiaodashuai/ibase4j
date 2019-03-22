package org.ibase4j.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BizDebtInfo implements Serializable {
    private String id;
    private String debtCode;
    private String projectNam;
    private String proposer;
    private String deptName;
    private String historyState;
    private String mpc;
    private BigDecimal solutionAmount;
    private Long solutionState;
    private String userName;
    private Date creatTime;
    private Date updateTime;
    private Date pgExpiDate;

    public Date getPgExpiDate() {
        return pgExpiDate;
    }

    public void setPgExpiDate(Date pgExpiDate) {
        this.pgExpiDate = pgExpiDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getId() {
        return id;
    }

    public String getHistoryState() {
        return historyState;
    }

    public void setHistoryState(String historyState) {
        this.historyState = historyState;
    }

    public void setId(String id) {
        this.id = id;

    }
    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public String getProjectNam() {
        return projectNam;
    }

    public void setProjectNam(String projectNam) {
        this.projectNam = projectNam;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMpc() {
        return mpc;
    }

    public void setMpc(String mpc) {
        this.mpc = mpc;
    }

    public BigDecimal getSolutionAmount() {
        return solutionAmount;
    }

    public void setSolutionAmount(BigDecimal solutionAmount) {
        this.solutionAmount = solutionAmount;
    }

    public Long getSolutionState() {
        return solutionState;
    }

    public void setSolutionState(Long solutionState) {
        this.solutionState = solutionState;
    }
}
