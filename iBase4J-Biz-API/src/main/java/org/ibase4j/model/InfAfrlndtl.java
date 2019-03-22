package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款附表明细文件表
 * @author hannasong
 * @version 1.0
 */
@TableName("INF_AFRLNDTL")
public class InfAfrlndtl extends BaseModel implements Serializable {

    @TableField("ZONENO")
    private String zoneno;

    @TableField("PROTSENO")
    private String protseno;

    @TableField("SUBSERNO")
    private String subserno;

    @TableField("WORKDATE")
    private String workdate;

    @TableField("LISTNO")
    private String listno;

    @TableField("LGLOCKNO")
    private String lglockno;

    @TableField("CURRTYPE")
    private String currtype;

    @TableField("CASHEXF")
    private String cashexf;

    @TableField("TERMNUM")
    private String termnum;

    @TableField("INTRATE")
    private String intrate;

    @TableField("RLVRATE")
    private String rlvrate;

    @TableField("OVRRATE")
    private String ovrrate;

    @TableField("INCIRATE")
    private String incirate;

    @TableField("OFCIRATE")
    private String ofcirate;

    @TableField("TAXRATE")
    private String taxrate;

    @TableField("CURRINT")
    private String currint;

    @TableField("RLVINT")
    private String rlvint;

    @TableField("RLVIBAL")
    private String rlvibal;

    @TableField("OVRAMT")
    private String ovramt;

    @TableField("OVRBAL")
    private String ovrbal;

    @TableField("OVRAMTIT")
    private String ovramtit;

    @TableField("INAMT")
    private String inamt;

    @TableField("INCURBAL")
    private String incurbal;

    @TableField("INAMTIT")
    private String inamtit;

    @TableField("OFAMT")
    private String ofamt;

    @TableField("OFCURBAL")
    private String ofcurbal;

    @TableField("OFAMTIT")
    private String ofamtit;

    @TableField("TAXSUM")
    private String taxsum;

    @TableField("TAXAMT")
    private String taxamt;

    @TableField("TAXBAL")
    private String taxbal;

    @TableField("NTAXAMT")
    private String ntaxamt;

    @TableField("NTAXBAL")
    private String ntaxbal;

    @TableField("DRDOACTN")
    private String drdoactn;

    @TableField("DRDOACSN")
    private String drdoacsn;

    @TableField("DRDOACTA")
    private String drdoacta;

    @TableField("PAYACTN")
    private String payactn;

    @TableField("PAYACSN")
    private String payacsn;

    @TableField("PAYACTA")
    private String payacta;

    public String getZoneno() {
        return zoneno == "" ? null : zoneno;
    }

    public String getProtseno() {
        return protseno == "" ? null : protseno;
    }

    public String getSubserno() {
        return subserno == "" ? null : subserno;
    }

    public String getWorkdate() {
        return workdate == "" ? null : workdate;
    }

    public String getListno() {
        return listno == "" ? null : listno;
    }

    public String getLglockno() {
        return lglockno == "" ? null : lglockno;
    }

    public String getCurrtype() {
        return currtype == "" ? null : currtype;
    }

    public String getCashexf() {
        return cashexf == "" ? null : cashexf;
    }

    public String getTermnum() {
        return termnum == "" ? null : termnum;
    }

    public BigDecimal getIntrate() {
        return intrate == "" ? null : new BigDecimal(intrate);
    }

    public BigDecimal getRlvrate() {
        return rlvrate == "" ? null : new BigDecimal(rlvrate);
    }

    public BigDecimal getOvrrate() {
        return ovrrate == "" ? null : new BigDecimal(ovrrate);
    }

    public BigDecimal getIncirate() {
        return incirate == "" ? null : new BigDecimal(incirate);
    }

    public BigDecimal getOfcirate() {
        return ofcirate == "" ? null : new BigDecimal(ofcirate);
    }

    public BigDecimal getTaxrate() {
        return taxrate == "" ? null : new BigDecimal(taxrate);
    }

    public BigDecimal getCurrint() {
        return currint == "" ? null : new BigDecimal(currint);
    }

    public BigDecimal getRlvint() {
        return rlvint == "" ? null : new BigDecimal(rlvint);
    }

    public BigDecimal getRlvibal() {
        return rlvibal == "" ? null : new BigDecimal(rlvibal);
    }

    public BigDecimal getOvramt() {
        return ovramt == "" ? null : new BigDecimal(ovramt);
    }

    public BigDecimal getOvrbal() {
        return ovrbal == "" ? null : new BigDecimal(ovrbal);
    }

    public BigDecimal getOvramtit() {
        return ovramtit == "" ? null : new BigDecimal(ovramtit);
    }

    public BigDecimal getInamt() {
        return inamt == "" ? null : new BigDecimal(inamt);
    }

    public BigDecimal getIncurbal() {
        return incurbal == "" ? null : new BigDecimal(incurbal);
    }

    public BigDecimal getInamtit() {
        return inamtit == "" ? null : new BigDecimal(inamtit);
    }

    public BigDecimal getOfamt() {
        return ofamt == "" ? null : new BigDecimal(ofamt);
    }

    public BigDecimal getOfcurbal() {
        return ofcurbal == "" ? null : new BigDecimal(ofcurbal);
    }

    public BigDecimal getOfamtit() {
        return ofamtit == "" ? null : new BigDecimal(ofamtit);
    }

    public BigDecimal getTaxsum() {
        return taxsum == "" ? null : new BigDecimal(taxsum);
    }

    public BigDecimal getTaxamt() {
        return taxamt == "" ? null : new BigDecimal(taxamt);
    }

    public BigDecimal getTaxbal() {
        return taxbal == "" ? null : new BigDecimal(taxbal);
    }

    public BigDecimal getNtaxamt() {
        return ntaxamt == "" ? null : new BigDecimal(ntaxamt);
    }

    public BigDecimal getNtaxbal() {
        return ntaxbal == "" ? null : new BigDecimal(ntaxbal);
    }

    public String getDrdoactn() {
        return drdoactn == "" ? null : drdoactn;
    }

    public String getDrdoacsn() {
        return drdoacsn == "" ? null : drdoacsn;
    }

    public BigDecimal getDrdoacta() {
        return drdoacta == "" ? null : new BigDecimal(drdoacta);
    }

    public String getPayactn() {
        return payactn == "" ? null : payactn;
    }

    public String getPayacsn() {
        return payacsn == "" ? null : payacsn;
    }

    public BigDecimal getPayacta() {
        return payacta == "" ? null : new BigDecimal(payacta);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("zoneno", zoneno)
                .add("protseno", protseno)
                .add("subserno", subserno)
                .add("workdate", workdate)
                .add("listno", listno)
                .add("lglockno", lglockno)
                .add("currtype", currtype)
                .add("cashexf", cashexf)
                .add("termnum", termnum)
                .add("intrate", intrate)
                .add("rlvrate", rlvrate)
                .add("ovrrate", ovrrate)
                .add("incirate", incirate)
                .add("ofcirate", ofcirate)
                .add("taxrate", taxrate)
                .add("currint", currint)
                .add("rlvint", rlvint)
                .add("rlvibal", rlvibal)
                .add("ovramt", ovramt)
                .add("ovrbal", ovrbal)
                .add("ovramtit", ovramtit)
                .add("inamt", inamt)
                .add("incurbal", incurbal)
                .add("inamtit", inamtit)
                .add("ofamt", ofamt)
                .add("ofcurbal", ofcurbal)
                .add("ofamtit", ofamtit)
                .add("taxsum", taxsum)
                .add("taxamt", taxamt)
                .add("taxbal", taxbal)
                .add("ntaxamt", ntaxamt)
                .add("ntaxbal", ntaxbal)
                .add("drdoactn", drdoactn)
                .add("drdoacsn", drdoacsn)
                .add("drdoacta", drdoacta)
                .add("payactn", payactn)
                .add("payacsn", payacsn)
                .add("payacta", payacta)
                .toString();
    }
}
