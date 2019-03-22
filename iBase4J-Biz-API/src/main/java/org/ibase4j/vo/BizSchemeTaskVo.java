package org.ibase4j.vo;

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
}
