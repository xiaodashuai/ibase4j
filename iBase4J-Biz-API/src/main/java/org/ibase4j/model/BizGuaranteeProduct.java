package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：担保信息与业务品种关系表
 *
 * @author xiaoshuiquan
 * @date 2018/07/17
 */

@TableName("BIZ_GUARANTEE_PRODUCT")
@SuppressWarnings("serial")
public class BizGuaranteeProduct extends BaseModel implements Serializable {

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
     * 担保信息表外键
     */
    @TableField("GUARANTEE_INFO_ID")
    private Long guaranteeInfoId;

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

    public Long getGuaranteeInfoId() {
        return guaranteeInfoId;
    }

    public void setGuaranteeInfoId(Long guaranteeInfoId) {
        this.guaranteeInfoId = guaranteeInfoId;
    }


    public BizGuaranteeProduct() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("debtNum", debtNum)
                .add("productCode", productCode)
                .add("guaranteeInfoId", guaranteeInfoId)
                .toString();
    }
}
