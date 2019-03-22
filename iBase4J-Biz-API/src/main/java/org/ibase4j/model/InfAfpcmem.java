package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贷款合同文件表
 * 
 * @author hannasong
 * @version 1.0
 */
@TableName("INF_AFPCMEM")
public class InfAfpcmem extends BaseModel implements Serializable {

    @TableField("CRZONENO")
    private String  crzoneno;
    @TableField("MEDIUMID")
    private String  mediumid;
    @TableField("PROTSENO")
    private String  protseno;
    @TableField("STATUS")
    private String  status;
    @TableField("SCURFLAG")
    private String  scurflag;
    @TableField("CURRTYPE")
    private String  currtype;
    @TableField("PROALIAS")
    private String  proalias;
    @TableField("PRTYCODE")
    private String  prtycode;
    @TableField("PRSERNO")
    private String  prserno;
    @TableField("XDMEMNO")
    private String  xdmemno;
    @TableField("ASSPFLAG")
    private String  asspflag;
    @TableField("CEUSEF")
    private String  ceusef;
    @TableField("CORPERF")
    private String  corperf;
    @TableField("LOANNATU")
    private String  loannatu;
    @TableField("ASSUREMD")
    private String  assuremd;
    @TableField("GRLNFLAG")
    private String  grlnflag;
    @TableField("LNTYPE")
    private String  lntype;
    @TableField("LOANNAME")
    private String  loanname;
    @TableField("SBJCODE1")
    private String  sbjcode1;
    @TableField("SBJCODE2")
    private String  sbjcode2;
    @TableField("SBJCODE3")
    private String  sbjcode3;
    @TableField("SBJCODE4")
    private String  sbjcode4;
    @TableField("SBJCODE5")
    private String  sbjcode5;
    @TableField("SBJCODE6")
    private String  sbjcode6;
    @TableField("SBJCODE7")
    private String  sbjcode7;
    @TableField("SBJCODE8")
    private String  sbjcode8;
    @TableField("GRPACCF")
    private String  grpaccf;
    @TableField("GRPMODE")
    private String  grpmode;
    @TableField("AUTDEPF")
    private String  autdepf;
    @TableField("AUTWITHF")
    private String  autwithf;
    @TableField("OLPRFLAG")
    private String  olprflag;
    @TableField("TATPRF")
    private String  tatprf;
    @TableField("LNCOLLF")
    private String  lncollf;
    @TableField("CRDATE")
    private String  crdate;
    @TableField("PRAMOUNT")
    private String  pramount;
    @TableField("CURLNAMT")
    private String  curlnamt;
    @TableField("TOTHKAMT")
    private String  tothkamt;
    @TableField("TOTFFAMT")
    private String  totffamt;
    @TableField("TOTFFNUM")
    private String  totffnum;
    @TableField("PROEDATE")
    private String  proedate;
    @TableField("PRMADATE")
    private String  prmadate;
    @TableField("PRAFLAG")
    private String  praflag;
    @TableField("PRACCF")
    private String  praccf;
    @TableField("PROACCNO")
    private String  proaccno;
    @TableField("PRACCSN")
    private String  praccsn;
    @TableField("GAGENO")
    private String  gageno;
    @TableField("GAGESUM")
    private String  gagesum;
    @TableField("EXPDATE")
    private String  expdate;
    @TableField("EXFLAG")
    private String  exflag;
    @TableField("EXQUOTA")
    private String  exquota;
    @TableField("ADZONENO")
    private String  adzoneno;
    @TableField("PRCRDATE")
    private String  prcrdate;
    @TableField("CRBRNO")
    private String  crbrno;
    @TableField("CTELLERN")
    private String  ctellern;
    @TableField("PRENDATE")
    private String  prendate;
    @TableField("ENDZONENO")
    private String  endzoneno;
    @TableField("ENDBRNO")
    private String  endbrno;
    @TableField("ETELLERN")
    private String  etellern;
    @TableField("LADJDATE")
    private String  ladjdate;
    @TableField("LTITDATE")
    private String  ltitdate;
    @TableField("LTRDDATE")
    private String  ltrddate;
    @TableField("LCTRDDAT")
    private String  lctrddat;
    @TableField("PHYBRNO")
    private String  phybrno;
    @TableField("ACTBRNO")
    private String  actbrno;
    @TableField("LGLOCKNO")
    private String  lglockno;
    @TableField("CMFLAG")
    private String  cmflag;
    @TableField("MAXSERNO")
    private String  maxserno;
    @TableField("USETYPE")
    private String  usetype;
    @TableField("FXCODE1")
    private String  fxcode1;
    @TableField("FXCODE2")
    private String  fxcode2;
    @TableField("FXCODE3")
    private String  fxcode3;
    @TableField("INTPRIF")
    private String  intprif;
    @TableField("NGBSQ")
    private String  ngbsq;
    @TableField("OGBSQ")
    private String  ogbsq;
    @TableField("CALINTF")
    private String  calintf;
    @TableField("OVCALINF")
    private String  ovcalinf;
    @TableField("INCALINF")
    private String  incalinf;
    @TableField("OCALINF")
    private String  ocalinf;
    @TableField("FXCODE4")
    private String  fxcode4;
    @TableField("FXCODE5")
    private String  fxcode5;
    @TableField("FXCODE6")
    private String  fxcode6;
    @TableField("GBTYPE")
    private String  gbtype;
    @TableField("DEFLAG")
    private String  deflag;
    @TableField("DEACCF")
    private String  deaccf;
    @TableField("DEACCNO")
    private String  deaccno;
    @TableField("DEACCSN")
    private String  deaccsn;
    @TableField("TOLRDAYS")
    private String  tolrdays;

    public String getCrzoneno() {
        return crzoneno == "" ? null : crzoneno;
    }

    public void setMediumid(String mediumid) {
        this.mediumid = mediumid;
    }

    public String getMediumid() {
        return mediumid == "" ? null : mediumid;
    }

    public String getProtseno() {
        return protseno == "" ? null : protseno;
    }

    public String getStatus() {
        return status == "" ? null : status;
    }

    public String getScurflag() {
        return scurflag == "" ? null : scurflag;
    }

    public String getCurrtype() {
        return currtype == "" ? null : currtype;
    }

    public String getProalias() {
        return proalias == "" ? null : proalias;
    }

    public String getPrtycode() {
        return prtycode == "" ? null : prtycode;
    }

    public String getPrserno() {
        return prserno == "" ? null : prserno;
    }

    public String getXdmemno() {
        return xdmemno == "" ? null : xdmemno;
    }

    public String getAsspflag() {
        return asspflag == "" ? null : asspflag;
    }

    public String getCeusef() {
        return ceusef == "" ? null : ceusef;
    }

    public String getCorperf() {
        return corperf == "" ? null : corperf;
    }

    public String getLoannatu() {
        return loannatu == "" ? null : loannatu;
    }

    public String getAssuremd() {
        return assuremd == "" ? null : assuremd;
    }

    public String getGrlnflag() {
        return grlnflag == "" ? null : grlnflag;
    }

    public String getLntype() {
        return lntype == "" ? null : lntype;
    }

    public String getLoanname() {
        return loanname == "" ? null : loanname;
    }

    public String getSbjcode1() {
        return sbjcode1 == "" ? null : sbjcode1;
    }

    public String getSbjcode2() {
        return sbjcode2 == "" ? null : sbjcode2;
    }

    public String getSbjcode3() {
        return sbjcode3 == "" ? null : sbjcode3;
    }

    public String getSbjcode4() {
        return sbjcode4 == "" ? null : sbjcode4;
    }

    public String getSbjcode5() {
        return sbjcode5 == "" ? null : sbjcode5;
    }

    public String getSbjcode6() {
        return sbjcode6 == "" ? null : sbjcode6;
    }

    public String getSbjcode7() {
        return sbjcode7 == "" ? null : sbjcode7;
    }

    public String getSbjcode8() {
        return sbjcode8 == "" ? null : sbjcode8;
    }

    public String getGrpaccf() {
        return grpaccf == "" ? null : grpaccf;
    }

    public String getGrpmode() {
        return grpmode == "" ? null : grpmode;
    }

    public String getAutdepf() {
        return autdepf == "" ? null : autdepf;
    }

    public String getAutwithf() {
        return autwithf == "" ? null : autwithf;
    }

    public String getOlprflag() {
        return olprflag == "" ? null : olprflag;
    }

    public String getTatprf() {
        return tatprf == "" ? null : tatprf;
    }

    public String getLncollf() {
        return lncollf == "" ? null : lncollf;
    }

    public String getCrdate() {
        return crdate == "" ? null : crdate;
    }

    public String getPramount() {
        return pramount == "" ? null : pramount;
    }

    public String getCurlnamt() {
        return curlnamt == "" ? null : curlnamt;
    }

    public BigDecimal getTothkamt() {
        return tothkamt == "" ? null : new BigDecimal(tothkamt);
    }

    public String getTotffamt() {
        return totffamt == "" ? null : totffamt;
    }

    public String getTotffnum() {
        return totffnum == "" ? null : totffnum;
    }

    public String getProedate() {
        return proedate == "" ? null : proedate;
    }

    public String getPrmadate() {
        return prmadate == "" ? null : prmadate;
    }

    public String getPraflag() {
        return praflag == "" ? null : praflag;
    }

    public String getPraccf() {
        return praccf == "" ? null : praccf;
    }

    public String getProaccno() {
        return proaccno == "" ? null : proaccno;
    }

    public String getPraccsn() {
        return praccsn == "" ? null : praccsn;
    }

    public String getGageno() {
        return gageno == "" ? null : gageno;
    }

    public String getGagesum() {
        return gagesum == "" ? null : gagesum;
    }

    public String getExpdate() {
        return expdate == "" ? null : expdate;
    }

    public String getExflag() {
        return exflag == "" ? null : exflag;
    }

    public String getExquota() {
        return exquota == "" ? null : exquota;
    }

    public String getAdzoneno() {
        return adzoneno == "" ? null : adzoneno;
    }

    public String getPrcrdate() {
        return prcrdate == "" ? null : prcrdate;
    }

    public String getCrbrno() {
        return crbrno == "" ? null : crbrno;
    }

    public String getCtellern() {
        return ctellern == "" ? null : ctellern;
    }

    public String getPrendate() {
        return prendate == "" ? null : prendate;
    }

    public String getEndzoneno() {
        return endzoneno == "" ? null : endzoneno;
    }

    public String getEndbrno() {
        return endbrno == "" ? null : endbrno;
    }

    public String getEtellern() {
        return etellern == "" ? null : etellern;
    }

    public String getLadjdate() {
        return ladjdate == "" ? null : ladjdate;
    }

    public String getLtitdate() {
        return ltitdate == "" ? null : ltitdate;
    }

    public String getLtrddate() {
        return ltrddate == "" ? null : ltrddate;
    }

    public String getLctrddat() {
        return lctrddat == "" ? null : lctrddat;
    }

    public String getPhybrno() {
        return phybrno == "" ? null : phybrno;
    }

    public String getActbrno() {
        return actbrno == "" ? null : actbrno;
    }

    public String getLglockno() {
        return lglockno == "" ? null : lglockno;
    }

    public String getCmflag() {
        return cmflag == "" ? null : cmflag;
    }

    public String getMaxserno() {
        return maxserno == "" ? null : maxserno;
    }

    public String getUsetype() {
        return usetype == "" ? null : usetype;
    }

    public String getFxcode1() {
        return fxcode1 == "" ? null : fxcode1;
    }

    public String getFxcode2() {
        return fxcode2 == "" ? null : fxcode2;
    }

    public String getFxcode3() {
        return fxcode3 == "" ? null : fxcode3;
    }

    public String getIntprif() {
        return intprif == "" ? null : intprif;
    }

    public String getNgbsq() {
        return ngbsq == "" ? null : ngbsq;
    }

    public String getOgbsq() {
        return ogbsq == "" ? null : ogbsq;
    }

    public String getCalintf() {
        return calintf == "" ? null : calintf;
    }

    public String getOvcalinf() {
        return ovcalinf == "" ? null : ovcalinf;
    }

    public String getIncalinf() {
        return incalinf == "" ? null : incalinf;
    }

    public String getOcalinf() {
        return ocalinf == "" ? null : ocalinf;
    }

    public String getFxcode4() {
        return fxcode4 == "" ? null : fxcode4;
    }

    public String getFxcode5() {
        return fxcode5 == "" ? null : fxcode5;
    }

    public String getFxcode6() {
        return fxcode6 == "" ? null : fxcode6;
    }

    public String getGbtype() {
        return gbtype == "" ? null : gbtype;
    }

    public String getDeflag() {
        return deflag == "" ? null : deflag;
    }

    public String getDeaccf() {
        return deaccf == "" ? null : deaccf;
    }

    public String getDeaccno() {
        return deaccno == "" ? null : deaccno;
    }

    public String getDeaccsn() {
        return deaccsn == "" ? null : deaccsn;
    }

    public String getTolrdays() {
        return tolrdays == "" ? null : tolrdays;
    }
}
