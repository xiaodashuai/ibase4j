package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款借据信息文件表
 * 
 * @author hannasong
 * @version 1.0
 */
@TableName("INF_AELNLON")
public class InfAelnlon extends BaseModel implements Serializable {

    @TableField("ZONENO")
    private String zoneno;
    @TableField("ACTBRNO")
    private String actbrno;
    @TableField("PHYBRNO")
    private String phybrno;
    @TableField("ACCNO")
    private String accno;
    @TableField("LOANNO")
    private String loanno;
    @TableField("LOANSQNO")
    private String loansqno;
    @TableField("CURRTYPE")
    private String currtype;
    @TableField("STATUS")
    private String status;
    @TableField("ACCNAME")
    private String accname;
    @TableField("FILLER10")
    private String filler10;
    @TableField("SUBCODE1")
    private String subcode1;
    @TableField("SUBCODE2")
    private String subcode2;
    @TableField("SBJCODE3")
    private String sbjcode3;
    @TableField("SBJCODE4")
    private String sbjcode4;
    @TableField("INFOCODE")
    private String infocode;
    @TableField("LNMEMNO")
    private String lnmemno;
    @TableField("LOANTYPE")
    private String loantype;
    @TableField("LOANNATU")
    private String loannatu;
    @TableField("LOANBAL")
    private String loanbal;
    @TableField("VALUEDAY")
    private String valueday;
    @TableField("MATUDATE")
    private String matudate;
    @TableField("EXHMATUD")
    private String exhmatud;
    @TableField("RATEVALD")
    private String ratevald;
    @TableField("RATECHGD")
    private String ratechgd;
    @TableField("LSTCINTD")
    private String lstcintd;
    @TableField("LSTTRAND")
    private String lsttrand;
    @TableField("LSTBAL")
    private String lstbal;
    /**
     *正常本金余额
     */
    @TableField("BALANCE1")
    private String balance1;
    @TableField("BALANCE2")
    private String balance2;
    @TableField("BALANCE3")
    private String balance3;
    @TableField("BALANCE4")
    private String balance4;
    @TableField("CALINTCL1")
    private String calintcl1;
    @TableField("CALINTCY1")
    private String calintcy1;
    @TableField("CALINTCL2")
    private String calintcl2;
    @TableField("CALINTCY2")
    private String calintcy2;
    @TableField("RATEINCM1")
    private String rateincm1;
    @TableField("RATEINCM2")
    private String rateincm2;
    @TableField("RATEINCM3")
    private String rateincm3;
    @TableField("FLOARATE")
    private String floarate;
    @TableField("EXHFRATE")
    private String exhfrate;
    @TableField("FINFRATE")
    private String finfrate;
    @TableField("RATECODE")
    private String ratecode;
    @TableField("EXHRCODE")
    private String exhrcode;
    @TableField("FINRCODE")
    private String finrcode;
    @TableField("CHGRCODE")
    private String chgrcode;
    @TableField("DRINT")
    private String drint;
    @TableField("DRALTINT")
    private String draltint;
    @TableField("DRADJINT")
    private String dradjint;
    @TableField("DRACM")
    private String dracm;
    @TableField("DEPDEPAT")
    private String depdepat;
    @TableField("DEPACCNO")
    private String depaccno;
    @TableField("BANKCODE")
    private String bankcode;
    @TableField("OPENTLNO")
    private String opentlno;
    @TableField("CLOSTLNO")
    private String clostlno;
    @TableField("OPENDATE")
    private String opendate;
    @TableField("CLOSDATE")
    private String closdate;
    @TableField("CLITLSTD")
    private String clitlstd;
    @TableField("LSTMODFD")
    private String lstmodfd;
    @TableField("CINO")
    private String cino;
    @TableField("TRINACCF")
    private String trinaccf;
    @TableField("TRINACCNO")
    private String trinaccno;
    @TableField("EXTRADATE")
    private String extradate;
    @TableField("FIELD1")
    private String field1;
    @TableField("FIELD2")
    private String field2;
    @TableField("COLNFLAG")
    private String colnflag;
    @TableField("BDLNFLAG")
    private String bdlnflag;
    @TableField("FDINT")
    private String fdint;
    @TableField("FDCALINT")
    private String fdcalint;
    @TableField("FDRATECD")
    private String fdratecd;
    @TableField("FDRATDOT")
    private String fdratdot;
    @TableField("FDFINRCD")
    private String fdfinrcd;
    @TableField("FDFINDOT")
    private String fdfindot;
    @TableField("CREDIT")
    private String credit;
    @TableField("LOANTERM")
    private String loanterm;
    @TableField("BRTNFLAG")
    private String brtnflag;

    public String getZoneno() {
        return zoneno == "" ? null : zoneno;
    }

    public String getActbrno() {
        return actbrno == "" ? null : actbrno;
    }

    public String getPhybrno() {
        return phybrno == "" ? null : phybrno;
    }

    public String getAccno() {
        return accno == "" ? null : accno;
    }

    public String getLoanno() {
        return loanno == "" ? null : loanno;
    }

    public String getLoansqno() {
        return loansqno == "" ? null : loansqno;
    }

    public String getCurrtype() {
        return currtype == "" ? null : currtype;
    }

    public String getStatus() {
        return status == "" ? null : status;
    }

    public String getAccname() {
        return accname == "" ? null : accname;
    }

    public String getFiller10() {
        return filler10 == "" ? null : filler10;
    }

    public String getSubcode1() {
        return subcode1 == "" ? null : subcode1;
    }

    public String getSubcode2() {
        return subcode2 == "" ? null : subcode2;
    }

    public String getSbjcode3() {
        return sbjcode3 == "" ? null : sbjcode3;
    }

    public String getSbjcode4() {
        return sbjcode4 == "" ? null : sbjcode4;
    }

    public String getInfocode() {
        return infocode == "" ? null : infocode;
    }

    public String getLnmemno() {
        return lnmemno == "" ? null : lnmemno;
    }

    public String getLoantype() {
        return loantype == "" ? null : loantype;
    }

    public String getLoannatu() {
        return loannatu == "" ? null : loannatu;
    }

    public String getLoanbal() {
        return loanbal == "" ? null : loanbal;
    }

    public String getValueday() {
        return valueday == "" ? null : valueday;
    }

    public String getMatudate() {
        return matudate == "" ? null : matudate;
    }

    public String getExhmatud() {
        return exhmatud == "" ? null : exhmatud;
    }

    public String getRatevald() {
        return ratevald == "" ? null : ratevald;
    }

    public String getRatechgd() {
        return ratechgd == "" ? null : ratechgd;
    }

    public String getLstcintd() {
        return lstcintd == "" ? null : lstcintd;
    }

    public String getLsttrand() {
        return lsttrand == "" ? null : lsttrand;
    }

    public String getLstbal() {
        return lstbal == "" ? null : lstbal;
    }

    public BigDecimal getBalance1() {
        return balance1 == "" ? null : new BigDecimal(balance1);
    }

    public String getBalance2() {
        return balance2 == "" ? null : balance2;
    }

    public String getBalance3() {
        return balance3 == "" ? null : balance3;
    }

    public String getBalance4() {
        return balance4 == "" ? null : balance4;
    }

    public String getCalintcl1() {
        return calintcl1 == "" ? null : calintcl1;
    }

    public String getCalintcy1() {
        return calintcy1 == "" ? null : calintcy1;
    }

    public String getCalintcl2() {
        return calintcl2 == "" ? null : calintcl2;
    }

    public String getCalintcy2() {
        return calintcy2 == "" ? null : calintcy2;
    }

    public String getRateincm1() {
        return rateincm1 == "" ? null : rateincm1;
    }

    public String getRateincm2() {
        return rateincm2 == "" ? null : rateincm2;
    }

    public String getRateincm3() {
        return rateincm3 == "" ? null : rateincm3;
    }

    public String getFloarate() {
        return floarate == "" ? null : floarate;
    }

    public String getExhfrate() {
        return exhfrate == "" ? null : exhfrate;
    }

    public String getFinfrate() {
        return finfrate == "" ? null : finfrate;
    }

    public String getRatecode() {
        return ratecode == "" ? null : ratecode;
    }

    public String getExhrcode() {
        return exhrcode == "" ? null : exhrcode;
    }

    public String getFinrcode() {
        return finrcode == "" ? null : finrcode;
    }

    public String getChgrcode() {
        return chgrcode == "" ? null : chgrcode;
    }

    public String getDrint() {
        return drint == "" ? null : drint;
    }

    public String getDraltint() {
        return draltint == "" ? null : draltint;
    }

    public String getDradjint() {
        return dradjint == "" ? null : dradjint;
    }

    public String getDracm() {
        return dracm == "" ? null : dracm;
    }

    public String getDepdepat() {
        return depdepat == "" ? null : depdepat;
    }

    public String getDepaccno() {
        return depaccno == "" ? null : depaccno;
    }

    public String getBankcode() {
        return bankcode == "" ? null : bankcode;
    }

    public String getOpentlno() {
        return opentlno == "" ? null : opentlno;
    }

    public String getClostlno() {
        return clostlno == "" ? null : clostlno;
    }

    public String getOpendate() {
        return opendate == "" ? null : opendate;
    }

    public String getClosdate() {
        return closdate == "" ? null : closdate;
    }

    public String getClitlstd() {
        return clitlstd == "" ? null : clitlstd;
    }

    public String getLstmodfd() {
        return lstmodfd == "" ? null : lstmodfd;
    }

    public String getCino() {
        return cino == "" ? null : cino;
    }

    public String getTrinaccf() {
        return trinaccf == "" ? null : trinaccf;
    }

    public String getTrinaccno() {
        return trinaccno == "" ? null : trinaccno;
    }

    public String getExtradate() {
        return extradate == "" ? null : extradate;
    }

    public String getField1() {
        return field1 == "" ? null : field1;
    }

    public String getField2() {
        return field2 == "" ? null : field2;
    }

    public String getColnflag() {
        return colnflag == "" ? null : colnflag;
    }

    public String getBdlnflag() {
        return bdlnflag == "" ? null : bdlnflag;
    }

    public String getFdint() {
        return fdint == "" ? null : fdint;
    }

    public String getFdcalint() {
        return fdcalint == "" ? null : fdcalint;
    }

    public String getFdratecd() {
        return fdratecd == "" ? null : fdratecd;
    }

    public String getFdratdot() {
        return fdratdot == "" ? null : fdratdot;
    }

    public String getFdfinrcd() {
        return fdfinrcd == "" ? null : fdfinrcd;
    }

    public String getFdfindot() {
        return fdfindot == "" ? null : fdfindot;
    }

    public String getCredit() {
        return credit == "" ? null : credit;
    }

    public String getLoanterm() {
        return loanterm == "" ? null : loanterm;
    }

    public String getBrtnflag() {
        return brtnflag == "" ? null : brtnflag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfAelnlon)) return false;
        InfAelnlon that = (InfAelnlon) o;
        return Objects.equal(getZoneno(), that.getZoneno()) &&
                Objects.equal(getActbrno(), that.getActbrno()) &&
                Objects.equal(getPhybrno(), that.getPhybrno()) &&
                Objects.equal(getAccno(), that.getAccno()) &&
                Objects.equal(getLoanno(), that.getLoanno()) &&
                Objects.equal(getLoansqno(), that.getLoansqno()) &&
                Objects.equal(getCurrtype(), that.getCurrtype()) &&
                Objects.equal(getStatus(), that.getStatus()) &&
                Objects.equal(getAccname(), that.getAccname()) &&
                Objects.equal(getFiller10(), that.getFiller10()) &&
                Objects.equal(getSubcode1(), that.getSubcode1()) &&
                Objects.equal(getSubcode2(), that.getSubcode2()) &&
                Objects.equal(getSbjcode3(), that.getSbjcode3()) &&
                Objects.equal(getSbjcode4(), that.getSbjcode4()) &&
                Objects.equal(getInfocode(), that.getInfocode()) &&
                Objects.equal(getLnmemno(), that.getLnmemno()) &&
                Objects.equal(getLoantype(), that.getLoantype()) &&
                Objects.equal(getLoannatu(), that.getLoannatu()) &&
                Objects.equal(getLoanbal(), that.getLoanbal()) &&
                Objects.equal(getValueday(), that.getValueday()) &&
                Objects.equal(getMatudate(), that.getMatudate()) &&
                Objects.equal(getExhmatud(), that.getExhmatud()) &&
                Objects.equal(getRatevald(), that.getRatevald()) &&
                Objects.equal(getRatechgd(), that.getRatechgd()) &&
                Objects.equal(getLstcintd(), that.getLstcintd()) &&
                Objects.equal(getLsttrand(), that.getLsttrand()) &&
                Objects.equal(getLstbal(), that.getLstbal()) &&
                Objects.equal(getBalance1(), that.getBalance1()) &&
                Objects.equal(getBalance2(), that.getBalance2()) &&
                Objects.equal(getBalance3(), that.getBalance3()) &&
                Objects.equal(getBalance4(), that.getBalance4()) &&
                Objects.equal(getCalintcl1(), that.getCalintcl1()) &&
                Objects.equal(getCalintcy1(), that.getCalintcy1()) &&
                Objects.equal(getCalintcl2(), that.getCalintcl2()) &&
                Objects.equal(getCalintcy2(), that.getCalintcy2()) &&
                Objects.equal(getRateincm1(), that.getRateincm1()) &&
                Objects.equal(getRateincm2(), that.getRateincm2()) &&
                Objects.equal(getRateincm3(), that.getRateincm3()) &&
                Objects.equal(getFloarate(), that.getFloarate()) &&
                Objects.equal(getExhfrate(), that.getExhfrate()) &&
                Objects.equal(getFinfrate(), that.getFinfrate()) &&
                Objects.equal(getRatecode(), that.getRatecode()) &&
                Objects.equal(getExhrcode(), that.getExhrcode()) &&
                Objects.equal(getFinrcode(), that.getFinrcode()) &&
                Objects.equal(getChgrcode(), that.getChgrcode()) &&
                Objects.equal(getDrint(), that.getDrint()) &&
                Objects.equal(getDraltint(), that.getDraltint()) &&
                Objects.equal(getDradjint(), that.getDradjint()) &&
                Objects.equal(getDracm(), that.getDracm()) &&
                Objects.equal(getDepdepat(), that.getDepdepat()) &&
                Objects.equal(getDepaccno(), that.getDepaccno()) &&
                Objects.equal(getBankcode(), that.getBankcode()) &&
                Objects.equal(getOpentlno(), that.getOpentlno()) &&
                Objects.equal(getClostlno(), that.getClostlno()) &&
                Objects.equal(getOpendate(), that.getOpendate()) &&
                Objects.equal(getClosdate(), that.getClosdate()) &&
                Objects.equal(getClitlstd(), that.getClitlstd()) &&
                Objects.equal(getLstmodfd(), that.getLstmodfd()) &&
                Objects.equal(getCino(), that.getCino()) &&
                Objects.equal(getTrinaccf(), that.getTrinaccf()) &&
                Objects.equal(getTrinaccno(), that.getTrinaccno()) &&
                Objects.equal(getExtradate(), that.getExtradate()) &&
                Objects.equal(getField1(), that.getField1()) &&
                Objects.equal(getField2(), that.getField2()) &&
                Objects.equal(getColnflag(), that.getColnflag()) &&
                Objects.equal(getBdlnflag(), that.getBdlnflag()) &&
                Objects.equal(getFdint(), that.getFdint()) &&
                Objects.equal(getFdcalint(), that.getFdcalint()) &&
                Objects.equal(getFdratecd(), that.getFdratecd()) &&
                Objects.equal(getFdratdot(), that.getFdratdot()) &&
                Objects.equal(getFdfinrcd(), that.getFdfinrcd()) &&
                Objects.equal(getFdfindot(), that.getFdfindot()) &&
                Objects.equal(getCredit(), that.getCredit()) &&
                Objects.equal(getLoanterm(), that.getLoanterm()) &&
                Objects.equal(getBrtnflag(), that.getBrtnflag());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getZoneno(), getActbrno(), getPhybrno(), getAccno(), getLoanno(), getLoansqno(), getCurrtype(), getStatus(), getAccname(), getFiller10(), getSubcode1(), getSubcode2(), getSbjcode3(), getSbjcode4(), getInfocode(), getLnmemno(), getLoantype(), getLoannatu(), getLoanbal(), getValueday(), getMatudate(), getExhmatud(), getRatevald(), getRatechgd(), getLstcintd(), getLsttrand(), getLstbal(), getBalance1(), getBalance2(), getBalance3(), getBalance4(), getCalintcl1(), getCalintcy1(), getCalintcl2(), getCalintcy2(), getRateincm1(), getRateincm2(), getRateincm3(), getFloarate(), getExhfrate(), getFinfrate(), getRatecode(), getExhrcode(), getFinrcode(), getChgrcode(), getDrint(), getDraltint(), getDradjint(), getDracm(), getDepdepat(), getDepaccno(), getBankcode(), getOpentlno(), getClostlno(), getOpendate(), getClosdate(), getClitlstd(), getLstmodfd(), getCino(), getTrinaccf(), getTrinaccno(), getExtradate(), getField1(), getField2(), getColnflag(), getBdlnflag(), getFdint(), getFdcalint(), getFdratecd(), getFdratdot(), getFdfinrcd(), getFdfindot(), getCredit(), getLoanterm(), getBrtnflag());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("zoneno", zoneno)
                .add("actbrno", actbrno)
                .add("phybrno", phybrno)
                .add("accno", accno)
                .add("loanno", loanno)
                .add("loansqno", loansqno)
                .add("currtype", currtype)
                .add("status", status)
                .add("accname", accname)
                .add("filler10", filler10)
                .add("subcode1", subcode1)
                .add("subcode2", subcode2)
                .add("sbjcode3", sbjcode3)
                .add("sbjcode4", sbjcode4)
                .add("infocode", infocode)
                .add("lnmemno", lnmemno)
                .add("loantype", loantype)
                .add("loannatu", loannatu)
                .add("loanbal", loanbal)
                .add("valueday", valueday)
                .add("matudate", matudate)
                .add("exhmatud", exhmatud)
                .add("ratevald", ratevald)
                .add("ratechgd", ratechgd)
                .add("lstcintd", lstcintd)
                .add("lsttrand", lsttrand)
                .add("lstbal", lstbal)
                .add("balance1", balance1)
                .add("balance2", balance2)
                .add("balance3", balance3)
                .add("balance4", balance4)
                .add("calintcl1", calintcl1)
                .add("calintcy1", calintcy1)
                .add("calintcl2", calintcl2)
                .add("calintcy2", calintcy2)
                .add("rateincm1", rateincm1)
                .add("rateincm2", rateincm2)
                .add("rateincm3", rateincm3)
                .add("floarate", floarate)
                .add("exhfrate", exhfrate)
                .add("finfrate", finfrate)
                .add("ratecode", ratecode)
                .add("exhrcode", exhrcode)
                .add("finrcode", finrcode)
                .add("chgrcode", chgrcode)
                .add("drint", drint)
                .add("draltint", draltint)
                .add("dradjint", dradjint)
                .add("dracm", dracm)
                .add("depdepat", depdepat)
                .add("depaccno", depaccno)
                .add("bankcode", bankcode)
                .add("opentlno", opentlno)
                .add("clostlno", clostlno)
                .add("opendate", opendate)
                .add("closdate", closdate)
                .add("clitlstd", clitlstd)
                .add("lstmodfd", lstmodfd)
                .add("cino", cino)
                .add("trinaccf", trinaccf)
                .add("trinaccno", trinaccno)
                .add("extradate", extradate)
                .add("field1", field1)
                .add("field2", field2)
                .add("colnflag", colnflag)
                .add("bdlnflag", bdlnflag)
                .add("fdint", fdint)
                .add("fdcalint", fdcalint)
                .add("fdratecd", fdratecd)
                .add("fdratdot", fdratdot)
                .add("fdfinrcd", fdfinrcd)
                .add("fdfindot", fdfindot)
                .add("credit", credit)
                .add("loanterm", loanterm)
                .add("brtnflag", brtnflag)
                .toString();
    }
}
