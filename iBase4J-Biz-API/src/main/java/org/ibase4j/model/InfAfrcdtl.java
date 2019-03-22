package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 核销类账户明细表
 * 
 * @author hannasong
 * @version 1.0
 */
@TableName("INF_AFRCDTL")
public class InfAfrcdtl extends BaseModel implements Serializable {

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

    @TableField("PRINFLAG")
    private String prinflag;

    @TableField("CMPRFLAG")
    private String cmprflag;

    @TableField("LGLOCKNO")
    private String lglockno;

    @TableField("TRXDATE")
    private String trxdate;

    @TableField("TRXTIME")
    private String trxtime;

    @TableField("TIMESTMP")
    private String timestmp;

    @TableField("POSTNDAT")
    private String postndat;

    @TableField("POSTSDAT")
    private String postsdat;

    @TableField("POSTTIME")
    private String posttime;

    @TableField("TRXSQNB")
    private String trxsqnb;

    @TableField("PTRXSQNB")
    private String ptrxsqnb;

    @TableField("TRXSQNS")
    private String trxsqns;

    @TableField("TRXCODE")
    private String trxcode;

    @TableField("VALUEDAT")
    private String valuedat;

    @TableField("CURRTYPE")
    private String currtype;

    @TableField("CASHEXF")
    private String cashexf;

    @TableField("CATRFLAG")
    private String catrflag;

    @TableField("DRCRF")
    private String drcrf;

    /**
     * 放款金额
     */
    @TableField("AMOUNT")
    private String amount;

    @TableField("UPDBAL")
    private String updbal;

    @TableField("NOTETYPE")
    private String notetype;

    @TableField("NOTES")
    private String notes;

    @TableField("REVTRANF")
    private String revtranf;

    @TableField("UPDTRANF")
    private String updtranf;

    @TableField("POSTTYPE")
    private String posttype;

    @TableField("LISTTYPE")
    private String listtype;

    @TableField("RECIPBKN")
    private String recipbkn;

    @TableField("RECIPBKA")
    private String recipbka;

    @TableField("RECIPACT")
    private String recipact;

    @TableField("RECIPACS")
    private String recipacs;

    @TableField("RECIPACN")
    private String recipacn;

    @TableField("EXCHRAT")
    private String exchrat;

    @TableField("FORECURR")
    private String forecurr;

    @TableField("FOREAMT")
    private String foreamt;

    @TableField("VOUHTYPE")
    private String vouhtype;

    @TableField("VOUHNO")
    private String vouhno;

    @TableField("MEDIUMID")
    private String mediumid;

    @TableField("MEDIUMNO")
    private String mediumno;

    @TableField("MEDSENO")
    private String medseno;

    @TableField("CHNLTYPE")
    private String chnltype;

    @TableField("CHNLNO")
    private String chnlno;

    @TableField("PRODCODE")
    private String prodcode;

    @TableField("PRODNO")
    private String prodno;

    @TableField("PRODGPNO")
    private String prodgpno;

    @TableField("TRXZNO")
    private String trxzno;

    @TableField("TPHYBRNO")
    private String tphybrno;

    @TableField("PHYBRNO")
    private String phybrno;

    @TableField("TELLERNO")
    private String tellerno;

    @TableField("AUTHTLNO")
    private String authtlno;

    @TableField("AUTHNO")
    private String authno;

    @TableField("TERMID")
    private String termid;

    @TableField("TRXPLCS")
    private String trxplcs;

    @TableField("STATCODE")
    private String statcode;

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

    public String getPrinflag() {
        return prinflag == "" ? null : prinflag;
    }

    public String getCmprflag() {
        return cmprflag == "" ? null : cmprflag;
    }

    public String getLglockno() {
        return lglockno == "" ? null : lglockno;
    }

    public String getTrxdate() {
        return trxdate == "" ? null : trxdate;
    }

    public String getTrxtime() {
        return trxtime == "" ? null : trxtime;
    }

    public String getTimestmp() {
        return timestmp == "" ? null : timestmp;
    }

    public String getPostndat() {
        return postndat == "" ? null : postndat;
    }

    public String getPostsdat() {
        return postsdat == "" ? null : postsdat;
    }

    public String getPosttime() {
        return posttime == "" ? null : posttime;
    }

    public String getTrxsqnb() {
        return trxsqnb == "" ? null : trxsqnb;
    }

    public String getPtrxsqnb() {
        return ptrxsqnb == "" ? null : ptrxsqnb;
    }

    public String getTrxsqns() {
        return trxsqns == "" ? null : trxsqns;
    }

    public String getTrxcode() {
        return trxcode == "" ? null : trxcode;
    }

    public String getValuedat() {
        return valuedat == "" ? null : valuedat;
    }

    public String getCurrtype() {
        return currtype == "" ? null : currtype;
    }

    public String getCashexf() {
        return cashexf == "" ? null : cashexf;
    }

    public String getCatrflag() {
        return catrflag == "" ? null : catrflag;
    }

    public String getDrcrf() {
        return drcrf == "" ? null : drcrf;
    }

    public BigDecimal getAmount() {
        return amount == "" ? null : new BigDecimal(amount);
    }

    public String getUpdbal() {
        return updbal == "" ? null : updbal;
    }

    public String getNotetype() {
        return notetype == "" ? null : notetype;
    }

    public String getNotes() {
        return notes == "" ? null : notes;
    }

    public String getRevtranf() {
        return revtranf == "" ? null : revtranf;
    }

    public String getUpdtranf() {
        return updtranf == "" ? null : updtranf;
    }

    public String getPosttype() {
        return posttype == "" ? null : posttype;
    }

    public String getListtype() {
        return listtype == "" ? null : listtype;
    }

    public String getRecipbkn() {
        return recipbkn == "" ? null : recipbkn;
    }

    public String getRecipbka() {
        return recipbka == "" ? null : recipbka;
    }

    public String getRecipact() {
        return recipact == "" ? null : recipact;
    }

    public String getRecipacs() {
        return recipacs == "" ? null : recipacs;
    }

    public String getRecipacn() {
        return recipacn == "" ? null : recipacn;
    }

    public String getExchrat() {
        return exchrat == "" ? null : exchrat;
    }

    public String getForecurr() {
        return forecurr == "" ? null : forecurr;
    }

    public String getForeamt() {
        return foreamt == "" ? null : foreamt;
    }

    public String getVouhtype() {
        return vouhtype == "" ? null : vouhtype;
    }

    public String getVouhno() {
        return vouhno == "" ? null : vouhno;
    }

    public String getMediumid() {
        return mediumid == "" ? null : mediumid;
    }

    public String getMediumno() {
        return mediumno == "" ? null : mediumno;
    }

    public String getMedseno() {
        return medseno == "" ? null : medseno;
    }

    public String getChnltype() {
        return chnltype == "" ? null : chnltype;
    }

    public String getChnlno() {
        return chnlno == "" ? null : chnlno;
    }

    public String getProdcode() {
        return prodcode == "" ? null : prodcode;
    }

    public String getProdno() {
        return prodno == "" ? null : prodno;
    }

    public String getProdgpno() {
        return prodgpno == "" ? null : prodgpno;
    }

    public String getTrxzno() {
        return trxzno == "" ? null : trxzno;
    }

    public String getTphybrno() {
        return tphybrno == "" ? null : tphybrno;
    }

    public String getPhybrno() {
        return phybrno == "" ? null : phybrno;
    }

    public String getTellerno() {
        return tellerno == "" ? null : tellerno;
    }

    public String getAuthtlno() {
        return authtlno == "" ? null : authtlno;
    }

    public String getAuthno() {
        return authno == "" ? null : authno;
    }

    public String getTermid() {
        return termid == "" ? null : termid;
    }

    public String getTrxplcs() {
        return trxplcs == "" ? null : trxplcs;
    }

    public String getStatcode() {
        return statcode == "" ? null : statcode;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("zoneno", zoneno)
                .add("protseno", protseno)
                .add("subserno", subserno)
                .add("workdate", workdate)
                .add("listno", listno)
                .add("prinflag", prinflag)
                .add("cmprflag", cmprflag)
                .add("lglockno", lglockno)
                .add("trxdate", trxdate)
                .add("trxtime", trxtime)
                .add("timestmp", timestmp)
                .add("postndat", postndat)
                .add("postsdat", postsdat)
                .add("posttime", posttime)
                .add("trxsqnb", trxsqnb)
                .add("ptrxsqnb", ptrxsqnb)
                .add("trxsqns", trxsqns)
                .add("trxcode", trxcode)
                .add("valuedat", valuedat)
                .add("currtype", currtype)
                .add("cashexf", cashexf)
                .add("catrflag", catrflag)
                .add("drcrf", drcrf)
                .add("amount", amount)
                .add("updbal", updbal)
                .add("notetype", notetype)
                .add("notes", notes)
                .add("revtranf", revtranf)
                .add("updtranf", updtranf)
                .add("posttype", posttype)
                .add("listtype", listtype)
                .add("recipbkn", recipbkn)
                .add("recipbka", recipbka)
                .add("recipact", recipact)
                .add("recipacs", recipacs)
                .add("recipacn", recipacn)
                .add("exchrat", exchrat)
                .add("forecurr", forecurr)
                .add("foreamt", foreamt)
                .add("vouhtype", vouhtype)
                .add("vouhno", vouhno)
                .add("mediumid", mediumid)
                .add("mediumno", mediumno)
                .add("medseno", medseno)
                .add("chnltype", chnltype)
                .add("chnlno", chnlno)
                .add("prodcode", prodcode)
                .add("prodno", prodno)
                .add("prodgpno", prodgpno)
                .add("trxzno", trxzno)
                .add("tphybrno", tphybrno)
                .add("phybrno", phybrno)
                .add("tellerno", tellerno)
                .add("authtlno", authtlno)
                .add("authno", authno)
                .add("termid", termid)
                .add("trxplcs", trxplcs)
                .add("statcode", statcode)
                .toString();
    }
}
