package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizGrantTaskVo)) return false;
        BizGrantTaskVo that = (BizGrantTaskVo) o;
        return Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getGrantCode(), that.getGrantCode()) &&
                Objects.equal(getUserName(), that.getUserName()) &&
                Objects.equal(getUserNameStart(), that.getUserNameStart()) &&
                Objects.equal(getCurrency(), that.getCurrency()) &&
                Objects.equal(getCodeName(), that.getCodeName()) &&
                Objects.equal(getPaymentAmt(), that.getPaymentAmt()) &&
                Objects.equal(getScopeBusinPeriod(), that.getScopeBusinPeriod()) &&
                Objects.equal(getName_(), that.getName_()) &&
                Objects.equal(getDeptCode(), that.getDeptCode()) &&
                Objects.equal(getRoleIdNow(), that.getRoleIdNow()) &&
                Objects.equal(getRoleIdProvider(), that.getRoleIdProvider());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDebtCode(), getGrantCode(), getUserName(), getUserNameStart(), getCurrency(), getCodeName(), getPaymentAmt(), getScopeBusinPeriod(), getName_(), getDeptCode(), getRoleIdNow(), getRoleIdProvider());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("debtCode", debtCode)
                .add("grantCode", grantCode)
                .add("userName", userName)
                .add("userNameStart", userNameStart)
                .add("currency", currency)
                .add("codeName", codeName)
                .add("paymentAmt", paymentAmt)
                .add("scopeBusinPeriod", scopeBusinPeriod)
                .add("name_", name_)
                .add("deptCode", deptCode)
                .add("roleIdNow", roleIdNow)
                .add("roleIdProvider", roleIdProvider)
                .toString();
    }
}
