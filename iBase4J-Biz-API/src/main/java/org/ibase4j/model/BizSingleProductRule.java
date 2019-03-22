package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("BIZ_SIGLE_PRODUCT_RULE")
@SuppressWarnings("serial")
public class BizSingleProductRule extends BaseModel implements Serializable {

    /**
     * 业务编号
     */
    @TableField("DEBT_CODE")
    private String debtCode;
    /**
     * 产品专有信息表外键
     */
    //@TableField("PROPER_INFO")
    //private Long productInfo;
    /**
     * 产品类型表名
     */
    @TableField("OBJ_TYPE")
    private String objType;
    /**
     * 业务品种（细类）
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;
        /**
     * 可办理笔数限制
     */
    //@TableField("NUM_LIMIT")
    //private Long ltnopa;
    /**
     * 可办理笔数
     */
    //@TableField("DEAL_NUM")
    //private Long tdwln;
    /**
     * 产品主币种
     */
    //@TableField("PRODUCT_CURRENCY")
    //private String productCurrency;
    /**
     * 产品金额
     */
    //@TableField("PRODUCT_AMT")
    //private Long aop;
    /**
     * 产品辅助币种
     */
    //@TableField("AIDED_CURRENCY")
    //private Long aidedCurrency;
    /**
     * 其他可选币种
     */
    //@TableField("CURRENCIES_AVAILABLE")
    //private Long currenciesAvailable;
    /**
     * 产品业务期限范围
     */
    //@TableField("SCOPE_BUSI_PERIOD")
    //private Long psp;
    /**
     * 产品费率类型
     */
    //@TableField("PROD_RATE_TYPE")
    //private Long prodRateType;
    /**
     * 产品费率形式
     */
    //@TableField("PROD_RATE_FORM")
    //private Long prodRateForm;
    /**
     * 产品费率
     */
    //@TableField("PROD_RATE")
    //private Long tpRate;
    /**
     * 产品费率范围最低值
     */
    //@TableField("RATE_RANGE_MIN")
    //private Long rateRangeMin;
    /**
     * 产品费率范围最高值
     */
    //@TableField("RATE_RANGE_MAX")
    //private Long rateRangeMax;
    /**
     * 是否经审批
     */
    //@TableField("APPROVED_FLAG")
    //private Long bteaa;
    /**
     * 审批机构
     */
    //@TableField("APPROVED_ORG")
    //private Long eaaa;
    /**
     * 是否存在产品费率折扣
     */
    //@TableField("PRODUCT_RATE_DISCOUNT")
    //private Long productRateDiscount;
    /**
     * 产品费率折扣
     */
    //@TableField("RATE_DISCOUNT")
    //private Long rateDiscount;
    /**
     * 利率规则描述
     */
    //@TableField("INTEREST_RATE_RULES")
    //private String interestRateRules;
    /**
     * 产品生效日期
     */
    //@TableField("PROD_EFFE_DATE")
    //private Date ptEffectiveDate;
    /**
     * 产品失效日期
     */
    //@TableField("PROD_EXPI_DATE")
    //private Date ptExpiDate;
    /**
     * 产品循环标志
     */
    //@TableField("PROD_LOOP")
    //private Long ptLs;
    /**
     * 方案生效日期
     */
    //@TableField("SCHE_EFFE_DATE")
    //private Date pgEffectiveDate;
    /**
     * 方案失效日期
     */
    //@TableField("SCHE_EXPI_DATE")
    //private Date pgExpiDate;
    /**
     * 背景国别
     */
    @TableField("BACKGROUND_NATIONALITY")
    private String backgroundNationality;
    /**
     * 行业投向
     */
    @TableField("INDUSTRY_INVESTMENT")
    private String industryInvestment;

    public String getIndustryInvestment() {
        return industryInvestment;
    }

    public void setIndustryInvestment(String industryInvestment) {
        this.industryInvestment = industryInvestment;
    }

   /* public Long getAidedCurrency() {
        return aidedCurrency;
    }

    public void setAidedCurrency(Long aidedCurrency) {
        this.aidedCurrency = aidedCurrency;
    }

    public Long getCurrenciesAvailable() {
        return currenciesAvailable;
    }

    public void setCurrenciesAvailable(Long currenciesAvailable) {
        this.currenciesAvailable = currenciesAvailable;
    }

    public Long getProdRateForm() {
        return prodRateForm;
    }

    public void setProdRateForm(Long prodRateForm) {
        this.prodRateForm = prodRateForm;
    }*/


    //public String getInterestRateRules() {
    //    return interestRateRules;
    //}
    //
    //public void setInterestRateRules(String interestRateRules) {
    //    this.interestRateRules = interestRateRules;
    //}

   /* public Long getEaaa() {
        return eaaa;
    }

    public void setEaaa(Long eaaa) {
        this.eaaa = eaaa;
    }

    public Long getProductRateDiscount() {
        return productRateDiscount;
    }

    public void setProductRateDiscount(Long productRateDiscount) {
        this.productRateDiscount = productRateDiscount;
    }

    public Long getRateDiscount() {
        return rateDiscount;
    }

    public void setRateDiscount(Long rateDiscount) {
        this.rateDiscount = rateDiscount;
    }*/

    public String getBackgroundNationality() {
        return backgroundNationality;
    }

    public void setBackgroundNationality(String backgroundNationality) {
        this.backgroundNationality = backgroundNationality;
    }

    //public Long getProdRateType() {
    //    return prodRateType;
    //}
    //
    //public void setProdRateType(Long prodRateType) {
    //    this.prodRateType = prodRateType;
    //}

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    //public Long getProductInfo() {
    //    return productInfo;
    //}
    //
    //public void setProductInfo(Long productInfo) {
    //    this.productInfo = productInfo;
    //}

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

   /* public String getProductCurrency() {
        return productCurrency;
    }

    public void setProductCurrency(String productCurrency) {
        this.productCurrency = productCurrency;
    }

    public Long getAop() {
        return aop;
    }

    public void setAop(Long aop) {
        this.aop = aop;
    }

    public Long getLtnopa() {
        return ltnopa;
    }

    public void setLtnopa(Long ltnopa) {
        this.ltnopa = ltnopa;
    }

    public Long getTdwln() {
        return tdwln;
    }

    public void setTdwln(Long tdwln) {
        this.tdwln = tdwln;
    }

    public Long getPsp() {
        return psp;
    }

    public void setPsp(Long psp) {
        this.psp = psp;
    }

    public Long getRateRangeMin() {
        return rateRangeMin;
    }

    public void setRateRangeMin(Long rateRangeMin) {
        this.rateRangeMin = rateRangeMin;
    }

    public Long getRateRangeMax() {
        return rateRangeMax;
    }

    public void setRateRangeMax(Long rateRangeMax) {
        this.rateRangeMax = rateRangeMax;
    }

    public Long getTpRate() {
        return tpRate;
    }

    public void setTpRate(Long tpRate) {
        this.tpRate = tpRate;
    }

    public Long getBteaa() {
        return bteaa;
    }

    public void setBteaa(Long bteaa) {
        this.bteaa = bteaa;
    }


    public Date getPtEffectiveDate() {
        return ptEffectiveDate;
    }

    public void setPtEffectiveDate(Date ptEffectiveDate) {
        this.ptEffectiveDate = ptEffectiveDate;
    }

    public Date getPtExpiDate() {
        return ptExpiDate;
    }

    public void setPtExpiDate(Date ptExpiDate) {
        this.ptExpiDate = ptExpiDate;
    }

    public Long getPtLs() {
        return ptLs;
    }

    public void setPtLs(Long ptLs) {
        this.ptLs = ptLs;
    }

    public Date getPgEffectiveDate() {
        return pgEffectiveDate;
    }

    public void setPgEffectiveDate(Date pgEffectiveDate) {
        this.pgEffectiveDate = pgEffectiveDate;
    }

    public Date getPgExpiDate() {
        return pgExpiDate;
    }

    public void setPgExpiDate(Date pgExpiDate) {
        this.pgExpiDate = pgExpiDate;
    }*/

    public BizSingleProductRule() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("debtCode", debtCode)
                .add("objType", objType)
                .add("businessType", businessType)
                .add("backgroundNationality", backgroundNationality)
                .add("industryInvestment", industryInvestment)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizSingleProductRule)) return false;
        BizSingleProductRule that = (BizSingleProductRule) o;
        return Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getObjType(), that.getObjType()) &&
                Objects.equal(getBusinessType(), that.getBusinessType()) &&
                Objects.equal(getBackgroundNationality(), that.getBackgroundNationality()) &&
                Objects.equal(getIndustryInvestment(), that.getIndustryInvestment());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDebtCode(), getObjType(), getBusinessType(), getBackgroundNationality(), getIndustryInvestment());
    }
}
