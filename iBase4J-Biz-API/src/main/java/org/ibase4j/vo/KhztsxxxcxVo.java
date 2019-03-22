package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class KhztsxxxcxVo implements Serializable {
    private String creditLineName;
    private String creditNo;
    private String totalAmount;
    private String usedAmount;
    private String availableBalance;
    private String startDate;
    private String maturityDate;
    private String amountType;

    public KhztsxxxcxVo() {
    }

    public String getCreditLineName() {
        return creditLineName;
    }

    public void setCreditLineName(String creditLineName) {
        this.creditLineName = creditLineName;
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(String usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("creditLineName", creditLineName)
                .add("creditNo", creditNo)
                .add("totalAmount", totalAmount)
                .add("usedAmount", usedAmount)
                .add("availableBalance", availableBalance)
                .add("startDate", startDate)
                .add("maturityDate", maturityDate)
                .add("amountType", amountType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KhztsxxxcxVo)) return false;
        KhztsxxxcxVo that = (KhztsxxxcxVo) o;
        return Objects.equal(getCreditLineName(), that.getCreditLineName()) &&
                Objects.equal(getCreditNo(), that.getCreditNo()) &&
                Objects.equal(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equal(getUsedAmount(), that.getUsedAmount()) &&
                Objects.equal(getAvailableBalance(), that.getAvailableBalance()) &&
                Objects.equal(getStartDate(), that.getStartDate()) &&
                Objects.equal(getMaturityDate(), that.getMaturityDate()) &&
                Objects.equal(getAmountType(), that.getAmountType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCreditLineName(), getCreditNo(), getTotalAmount(), getUsedAmount(), getAvailableBalance(), getStartDate(), getMaturityDate(), getAmountType());
    }
}
