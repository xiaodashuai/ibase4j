package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：行业投向与业务品种关系表
 *
 * @author xiaoshuiquan
 * @date 2018/07/17
 */

@TableName("BIZ_INDUSTRY_INVESTMENT_PRODUCT")
@SuppressWarnings("serial")
public class BizIndustryInvestmentProduct extends BaseModel implements Serializable {

    /**
     * 业务编号
     */
    @TableField("DEBT_CODE")
    private Long debtNum;
    /**
     * 产品细类编号
     */
    @TableField("PRODUCT_CODE")
    private Long productCode;
    /**
     * 行业投向表外键
     */
    @TableField("INDUSTRY_INVESTMENT_ID")
    private Long industryInvestmentId;

    public Long getDebtNum() {
        return debtNum;
    }

    public void setDebtNum(Long debtNum) {
        this.debtNum = debtNum;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public Long getIndustryInvestmentId() {
        return industryInvestmentId;
    }

    public void setIndustryInvestmentId(Long industryInvestmentId) {
        this.industryInvestmentId = industryInvestmentId;
    }

    public BizIndustryInvestmentProduct() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("debtNum", debtNum)
                .add("productCode", productCode)
                .add("industryInvestmentId", industryInvestmentId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizIndustryInvestmentProduct)) return false;
        BizIndustryInvestmentProduct that = (BizIndustryInvestmentProduct) o;
        return Objects.equal(getDebtNum(), that.getDebtNum()) &&
                Objects.equal(getProductCode(), that.getProductCode()) &&
                Objects.equal(getIndustryInvestmentId(), that.getIndustryInvestmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDebtNum(), getProductCode(), getIndustryInvestmentId());
    }
}
