package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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
    private Integer verNum;

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

    public Integer getVerNum() {
        return verNum;
    }

    public void setVerNum(Integer verNum) {
        this.verNum = verNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizDebtInfo)) return false;
        BizDebtInfo that = (BizDebtInfo) o;
        return Objects.equal(getId(), that.getId()) &&
                Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getProjectNam(), that.getProjectNam()) &&
                Objects.equal(getProposer(), that.getProposer()) &&
                Objects.equal(getDeptName(), that.getDeptName()) &&
                Objects.equal(getHistoryState(), that.getHistoryState()) &&
                Objects.equal(getMpc(), that.getMpc()) &&
                Objects.equal(getSolutionAmount(), that.getSolutionAmount()) &&
                Objects.equal(getSolutionState(), that.getSolutionState()) &&
                Objects.equal(getUserName(), that.getUserName()) &&
                Objects.equal(getCreatTime(), that.getCreatTime()) &&
                Objects.equal(getUpdateTime(), that.getUpdateTime()) &&
                Objects.equal(getPgExpiDate(), that.getPgExpiDate()) &&
                Objects.equal(getVerNum(), that.getVerNum());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getDebtCode(), getProjectNam(), getProposer(), getDeptName(), getHistoryState(), getMpc(), getSolutionAmount(), getSolutionState(), getUserName(), getCreatTime(), getUpdateTime(), getPgExpiDate(), getVerNum());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("debtCode", debtCode)
                .add("projectNam", projectNam)
                .add("proposer", proposer)
                .add("deptName", deptName)
                .add("historyState", historyState)
                .add("mpc", mpc)
                .add("solutionAmount", solutionAmount)
                .add("solutionState", solutionState)
                .add("userName", userName)
                .add("creatTime", creatTime)
                .add("updateTime", updateTime)
                .add("pgExpiDate", pgExpiDate)
                .add("verNum", verNum)
                .toString();
    }
}
