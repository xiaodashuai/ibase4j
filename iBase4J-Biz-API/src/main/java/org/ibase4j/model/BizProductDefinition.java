package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * @Description: 产品定义
 * @Author: dj
 * @CreateDate: 2019-01-09
 */
@TableName("BIZ_PRODUCT_DEFINITION")
@SuppressWarnings("serial")
public class BizProductDefinition extends BaseModel implements Serializable{

    /**
     * 地区号
     */
    @TableField("ZONE_NO")
    private String zoneNo;

    /**
     * 产品种类
     */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /**
     * 币种（字母）
     */
    @TableField("CURRENCY_CHAR")
    private String currencyChar;

    /**
     * 币种（数字）
     */
    @TableField("CURRENCY_NUM")
    private String currencyNum;

    /**
     * 产品组合 1 单一产品 2 组合产品
     */
    @TableField("PRODUCT_MIX")
    private String productMix;

    /**
     * 产品序号
     */
    @TableField("PRODUCT_SEQNUM")
    private String productSeqnum;

    /**
     * 产品名称
     */
    @TableField("product_name")
    private String productName;

    public String getZoneNo() {
        return zoneNo;
    }

    public void setZoneNo(String zoneNo) {
        this.zoneNo = zoneNo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCurrencyChar() {
        return currencyChar;
    }

    public void setCurrencyChar(String currencyChar) {
        this.currencyChar = currencyChar;
    }

    public String getCurrencyNum() {
        return currencyNum;
    }

    public void setCurrencyNum(String currencyNum) {
        this.currencyNum = currencyNum;
    }

    public String getProductMix() {
        return productMix;
    }

    public void setProductMix(String productMix) {
        this.productMix = productMix;
    }

    public String getProductSeqnum() {
        return productSeqnum;
    }

    public void setProductSeqnum(String productSeqnum) {
        this.productSeqnum = productSeqnum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizProductDefinition)) return false;
        BizProductDefinition that = (BizProductDefinition) o;
        return Objects.equal(getZoneNo(), that.getZoneNo()) &&
                Objects.equal(getProductType(), that.getProductType()) &&
                Objects.equal(getCurrencyChar(), that.getCurrencyChar()) &&
                Objects.equal(getCurrencyNum(), that.getCurrencyNum()) &&
                Objects.equal(getProductMix(), that.getProductMix()) &&
                Objects.equal(getProductSeqnum(), that.getProductSeqnum()) &&
                Objects.equal(getProductName(), that.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getZoneNo(), getProductType(), getCurrencyChar(), getCurrencyNum(), getProductMix(), getProductSeqnum(), getProductName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("zoneNo", zoneNo)
                .add("productType", productType)
                .add("currencyChar", currencyChar)
                .add("currencyNum", currencyNum)
                .add("productMix", productMix)
                .add("productSeqnum", productSeqnum)
                .add("productName", productName)
                .toString();
    }
}
