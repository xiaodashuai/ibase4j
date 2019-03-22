package org.ibase4j.vo;

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
}

