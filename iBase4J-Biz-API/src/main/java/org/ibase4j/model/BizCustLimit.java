package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能：额度占用表
 * 
 * @author hannasong
 * @version 1.1
 */
@TableName("BIZ_CUST_LIMIT")
public class BizCustLimit extends BaseModel implements Serializable {

	/**
	 * 业务表名称
	 */
	@TableField("OBJTYP")
	private String objtyp;
	/**
	 * 业务表主键
	 */
	@TableField("OBJINR")
	private String objinr;
    /**
     * 客户号
     */
	@TableField("CUST_NO")
	private String custNo;
	/**
	 * 客户名称（中文）
	 */
	@TableField("CUST_NAME_CN")
	private String custNameCN;
	/**
	 * 客户表主键
	 */
    @TableField("PTYINR")
	private String ptyinr;
    /**
	 * 额度类型
	 */
    @TableField("AMOUNT_TYPE")
    private String amountType;
    /**
     * 授信号码
     */
    @TableField("CREDIT_NO")
    private String 	creditNo;
    /**
     * 授信额度名称
     */
    @TableField("CREDIT_LINE_NAME")
    private String creditLineName;
    /**
     * 币种
     */
    @TableField("CUR")
    private String cur;
    /**
     * 金额
     */
    @TableField("AMT")
    private BigDecimal amt;
    /**
     * TRN表的INR，标识该发生额来自哪一笔交易
     */
    @TableField("TRNINR")
    private Long trninr;
    /**
     * 信贷员/客户号
     */
    private long bankTellerId;

    /**
     * 业务编号
     */
    @TableField("DEBT_CODE")
    private String debtCode;
    /**
     * 发放条件编号
     */
    @TableField("GRANT_CODE")
    private String grantCode;
    /**
     * 修改次数
     */
    @TableField("CHANGE_NUM")
    private String changeNum;
    /**
     * 被担保人
     */
    @TableField("WARRANTEE_NO")
    private String warranteeNo;

    public String getObjtyp() {
        return objtyp == "" ? null : objtyp;
    }

    public void setObjtyp(String objtyp) {
        this.objtyp = objtyp;
    }

    public String getObjinr() {
        return objinr == "" ? null : objinr;
    }

    public void setObjinr(String objinr) {
        this.objinr = objinr;
    }

    public String getCustNo() {
        return custNo == "" ? null : custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCustNameCN() {
        return custNameCN == "" ? null : custNameCN;
    }

    public void setCustNameCN(String custNameCN) {
        this.custNameCN = custNameCN;
    }

    public String getPtyinr() {
        return ptyinr == "" ? null : ptyinr;
    }

    public void setPtyinr(String ptyinr) {
        this.ptyinr = ptyinr;
    }

    public String getAmountType() {
        return amountType == "" ? null : amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getCreditNo() {
        return creditNo == "" ? null : creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getCreditLineName() {
        return creditLineName == "" ? null : creditLineName;
    }

    public void setCreditLineName(String creditLineName) {
        this.creditLineName = creditLineName;
    }

    public String getCur() {
        return cur == "" ? null : cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Long getTrninr() {
        return trninr;
    }

    public void setTrninr(Long trninr) {
        this.trninr = trninr;
    }

    public long getBankTellerId() {
        return bankTellerId;
    }

    public void setBankTellerId(long bankTellerId) {
        this.bankTellerId = bankTellerId;
    }

    public String getDebtCode() {
        return debtCode == "" ? null : debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public String getGrantCode() {
        return grantCode == "" ? null : grantCode;
    }

    public void setGrantCode(String grantCode) {
        this.grantCode = grantCode;
    }

    public String getChangeNum() {
        return changeNum == "" ? null : changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public String getWarranteeNo() {
        return warranteeNo == "" ? null : warranteeNo;
    }

    public void setWarranteeNo(String warranteeNo) {
        this.warranteeNo = warranteeNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizCustLimit)) return false;
        BizCustLimit that = (BizCustLimit) o;
        return getBankTellerId() == that.getBankTellerId() &&
                Objects.equal(getObjtyp(), that.getObjtyp()) &&
                Objects.equal(getObjinr(), that.getObjinr()) &&
                Objects.equal(getCustNo(), that.getCustNo()) &&
                Objects.equal(getCustNameCN(), that.getCustNameCN()) &&
                Objects.equal(getPtyinr(), that.getPtyinr()) &&
                Objects.equal(getAmountType(), that.getAmountType()) &&
                Objects.equal(getCreditNo(), that.getCreditNo()) &&
                Objects.equal(getCreditLineName(), that.getCreditLineName()) &&
                Objects.equal(getCur(), that.getCur()) &&
                Objects.equal(getAmt(), that.getAmt()) &&
                Objects.equal(getTrninr(), that.getTrninr()) &&
                Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getGrantCode(), that.getGrantCode()) &&
                Objects.equal(getChangeNum(), that.getChangeNum()) &&
                Objects.equal(getWarranteeNo(), that.getWarranteeNo());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getObjtyp(), getObjinr(), getCustNo(), getCustNameCN(), getPtyinr(), getAmountType(), getCreditNo(), getCreditLineName(), getCur(), getAmt(), getTrninr(), getBankTellerId(), getDebtCode(), getGrantCode(), getChangeNum(), getWarranteeNo());
    }


}
