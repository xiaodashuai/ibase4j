package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;
import org.ibase4j.core.util.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷款还款计划文件接口表
 *
 * @author hannasong
 * @version 1.0
 * @date 2019/02/22
 */
@TableName("INF_AFWKPLN")
public class InfAfwkpln extends BaseModel implements Serializable {

    @TableField("ZONENO1")
    private String zoneno1;
    @TableField("PROTSENO")
    private String protseno;
    @TableField("SUBSERNO")
    private String subserno;
    @TableField("STATUS")
    private String status;
    @TableField("CURRTYPE")
    private String currtype;
    /**
     * 还款日期
     */
    @TableField("GBDATE")
    private String gbdate;
    /**
     * 应还本金
     */
    @TableField("GBAMOUNT")
    private String gbamount;
    @TableField("GBINT")
    private String gbint;
    @TableField("GBTAMT")
    private String gbtamt;
    @TableField("CRDATE")
    private String crdate;
    @TableField("ZONENO2")
    private String zoneno2;
    @TableField("BRNO")
    private String brno;
    @TableField("TELLERNO")
    private String tellerno;

    public String getZoneno1() {
        return zoneno1 == "" ? null : zoneno1;
    }

    public String getProtseno() {
        return protseno == "" ? null : protseno;
    }

    public String getSubserno() {
        return subserno == "" ? null : subserno;
    }

    public String getStatus() {
        return status == "" ? null : status;
    }

    public String getCurrtype() {
        return currtype == "" ? null : currtype;
    }

    public Date getGbdate() {
        return gbdate == "" ? null : DateUtil.stringToDate(gbdate);
    }

    public BigDecimal getGbamount() {
        return gbamount == "" ? null : new BigDecimal(gbamount);
    }

    public String getGbint() {
        return gbint == "" ? null : gbint;
    }

    public String getGbtamt() {
        return gbtamt == "" ? null : gbtamt;
    }

    public String getCrdate() {
        return crdate == "" ? null : crdate;
    }

    public String getZoneno2() {
        return zoneno2 == "" ? null : zoneno2;
    }

    public String getBrno() {
        return brno == "" ? null : brno;
    }

    public String getTellerno() {
        return tellerno == "" ? null : tellerno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfAfwkpln)) return false;
        InfAfwkpln that = (InfAfwkpln) o;
        return Objects.equal(getZoneno1(), that.getZoneno1()) &&
                Objects.equal(getProtseno(), that.getProtseno()) &&
                Objects.equal(getSubserno(), that.getSubserno()) &&
                Objects.equal(getStatus(), that.getStatus()) &&
                Objects.equal(getCurrtype(), that.getCurrtype()) &&
                Objects.equal(getGbdate(), that.getGbdate()) &&
                Objects.equal(getGbamount(), that.getGbamount()) &&
                Objects.equal(getGbint(), that.getGbint()) &&
                Objects.equal(getGbtamt(), that.getGbtamt()) &&
                Objects.equal(getCrdate(), that.getCrdate()) &&
                Objects.equal(getZoneno2(), that.getZoneno2()) &&
                Objects.equal(getBrno(), that.getBrno()) &&
                Objects.equal(getTellerno(), that.getTellerno());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getZoneno1(), getProtseno(), getSubserno(), getStatus(), getCurrtype(), getGbdate(), getGbamount(), getGbint(), getGbtamt(), getCrdate(), getZoneno2(), getBrno(), getTellerno());
    }


}
