package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.matrix.engine.foundation.domain.TaskVO;

import java.math.BigDecimal;

public class BizSchemeTaskVo extends TaskVO {
    //项目名称
    private String projectName;
    //方案编号
    private String debtCode;
    //方案主币种
    private String mainCurrency;
    //主币种中文名
    private String mainCodeName;
    //方案申请人名称
    private String proposer;
    //方案金额
    private Double solutionAmt;
    //角色Id
    private Long roleCode;
    //角色名字
    private String roleName;
    //流程启动人
    private String userNameStart;
    //roleIdNow
    private String roleIdNow;
    //roleIdProvider
    private String roleIdProvider;
    //添加版本号
    private Integer verNum;


    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }


    public String getMainCurrency() {
        return mainCurrency;
    }

    public void setMainCurrency(String mainCurrency) {
        this.mainCurrency = mainCurrency;
    }

    public String getMainCodeName() {
        return mainCodeName;
    }

    public void setMainCodeName(String mainCodeName) {
        this.mainCodeName = mainCodeName;
    }

    public Long getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Long roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleIdNow() {
        return roleIdNow;
    }

    public void setRoleIdNow(String roleIdNow) {
        this.roleIdNow = roleIdNow;
    }

    public String getRoleIdProvider() {
        return roleIdProvider;
    }

    public void setRoleIdProvider(String roleIdProvider) {
        this.roleIdProvider = roleIdProvider;
    }

    public String getUserNameStart() {
        return userNameStart;
    }

    public void setUserNameStart(String userNameStart) {
        this.userNameStart = userNameStart;
    }

    public Double getSolutionAmt() {
        return solutionAmt;
    }

    public void setSolutionAmt(Double solutionAmt) {
        this.solutionAmt = solutionAmt;
    }

    public Integer getVerNum() {
        return verNum;
    }

    public void setVerNum(Integer verNum) {
        this.verNum = verNum;
    }
    public String getVerNumStr() {
        return String.format("%0" + 3 + "d", verNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizSchemeTaskVo)) return false;
        BizSchemeTaskVo that = (BizSchemeTaskVo) o;
        return Objects.equal(getProjectName(), that.getProjectName()) &&
                Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getMainCurrency(), that.getMainCurrency()) &&
                Objects.equal(getMainCodeName(), that.getMainCodeName()) &&
                Objects.equal(getProposer(), that.getProposer()) &&
                Objects.equal(getSolutionAmt(), that.getSolutionAmt()) &&
                Objects.equal(getRoleCode(), that.getRoleCode()) &&
                Objects.equal(getRoleName(), that.getRoleName()) &&
                Objects.equal(getUserNameStart(), that.getUserNameStart()) &&
                Objects.equal(getRoleIdNow(), that.getRoleIdNow()) &&
                Objects.equal(getRoleIdProvider(), that.getRoleIdProvider()) &&
                Objects.equal(getVerNum(), that.getVerNum());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProjectName(), getDebtCode(), getMainCurrency(), getMainCodeName(), getProposer(), getSolutionAmt(), getRoleCode(), getRoleName(), getUserNameStart(), getRoleIdNow(), getRoleIdProvider(), getVerNum());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("projectName", projectName)
                .add("debtCode", debtCode)
                .add("mainCurrency", mainCurrency)
                .add("mainCodeName", mainCodeName)
                .add("proposer", proposer)
                .add("solutionAmt", solutionAmt)
                .add("roleCode", roleCode)
                .add("roleName", roleName)
                .add("userNameStart", userNameStart)
                .add("roleIdNow", roleIdNow)
                .add("roleIdProvider", roleIdProvider)
                .add("verNum", verNum)
                .toString();
    }
}
