package org.ibase4j.vo;

import com.matrix.engine.foundation.domain.TaskVO;

import java.math.BigDecimal;

public class BizGrantTaskVo extends TaskVO {
    //发放编号
    private String debtCode;
    //发放编号
    private String grantCode;
    //发放申请人
    private String userName;
    //流程启动人
    private String userNameStart;
    //发放币种
    private String currency;
    //币种中文名
    private String codeName;
    //发放金额
    private Double paymentAmt;
    //业务期限
    private String scopeBusinPeriod;
    //产品名称
    private String name_;
    //当前岗位
    private String deptCode;
    //roleIdNow
    private String roleIdNow;
    //roleIdProvider
    private String roleIdProvider;



    public String getGrantCode() {
        return grantCode;
    }

    public void setGrantCode(String grantCode) {
        this.grantCode = grantCode;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getScopeBusinPeriod() {
        return scopeBusinPeriod;
    }

    public void setScopeBusinPeriod(String scopeBusinPeriod) {
        this.scopeBusinPeriod = scopeBusinPeriod;
    }



    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }


    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
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

    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }


    public Double getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(Double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }
}
