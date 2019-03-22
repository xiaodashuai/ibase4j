package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：担保合同与抵质押物表
 *
 * @author xiaoshuiquan
 * @date 2018/08/26
 */

@TableName("BIZ_CONTRACT_COLLATERAL")
@SuppressWarnings("serial")
public class BizBetInformation extends BaseModel implements Serializable {


    /**
     * 债项方案id
     */
    @TableField("DEBT_CODE")
    private String debtCode;
    /**
     * 担保合同编号
     */
    @TableField("GUAR_NO")
    private String guarNo;
    /**
     * 押品编号
     */
    @TableField("PLED_NO")
    private String pledNo;
    /**
     * 押品信息描述
     */
    @TableField("PRODUCT_INFORMATION")
    private String productInformation;


    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public String getGuarNo() {
        return guarNo;
    }

    public void setGuarNo(String guarNo) {
        this.guarNo = guarNo;
    }

    public String getPledNo() {
        return pledNo;
    }

    public void setPledNo(String pledNo) {
        this.pledNo = pledNo;
    }

    public String getProductInformation() {
        return productInformation;
    }

    public void setProductInformation(String productInformation) {
        this.productInformation = productInformation;
    }

    public BizBetInformation() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("debtCode", debtCode)
                .add("guarNo", guarNo)
                .add("pledNo", pledNo)
                .add("productInformation", productInformation)
                .toString();
    }
}
