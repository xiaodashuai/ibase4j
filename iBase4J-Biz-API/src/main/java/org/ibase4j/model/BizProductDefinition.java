package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
}
