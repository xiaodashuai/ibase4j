package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @Description: 64004接口参数vo类
 * @Author: dj
 * @CreateDate: 2018-10-24
 */
public class RepaymentVo implements java.io.Serializable,Comparable {

    // 还款日
    private String gbdate;
    // 计息截止日
    private String endintd;
    // 还款本金
    private String gbamount;
    // 归还标志
    private String intrtnf;

    public String getGbdate() {
        return gbdate;
    }

    public void setGbdate(String gbdate) {
        this.gbdate = gbdate;
    }

    public String getEndintd() {
        return endintd;
    }

    public void setEndintd(String endintd) {
        this.endintd = endintd;
    }

    public String getGbamount() {
        return gbamount;
    }

    public void setGbamount(String gbamount) {
        this.gbamount = gbamount;
    }

    public String getIntrtnf() {
        return intrtnf;
    }

    public void setIntrtnf(String intrtnf) {
        this.intrtnf = intrtnf;
    }
    @Override
    public int compareTo(Object o) {
        RepaymentVo repaymentVo = (RepaymentVo)o;
        return gbdate.compareTo(repaymentVo.gbdate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepaymentVo)) return false;
        RepaymentVo that = (RepaymentVo) o;
        return Objects.equal(getGbdate(), that.getGbdate()) &&
                Objects.equal(getEndintd(), that.getEndintd()) &&
                Objects.equal(getGbamount(), that.getGbamount()) &&
                Objects.equal(getIntrtnf(), that.getIntrtnf());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getGbdate(), getEndintd(), getGbamount(), getIntrtnf());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gbdate", gbdate)
                .add("endintd", endintd)
                .add("gbamount", gbamount)
                .add("intrtnf", intrtnf)
                .toString();
    }
}

