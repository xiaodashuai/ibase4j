package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能：还款记录表
 * 
 * @author hannasong
 * @version 1.0
 */
@TableName("BIZ_REPAYMENT_REC")
public class BizRepaymentRec extends BaseModel implements Serializable {

    /**
     * 方案编号
     */
    @TableField("DEBT_CODE")
    private String debtCode;
    /**
     * 还款币种
     */
    @TableField("CURRENCY")
    private String currency;
    /**
     * 还款金额
     */
    @TableField("AMOUNT")
    private BigDecimal amount;
    /**
     * 产品种类
     */
    @TableField("BUSINESS_TYPES")
    private String businessTypes;
    /**
     * 产品名称
     */
    @TableField("BUSINESS_NAME")
    private String businessName;
    /**
     * 发放编码
     */
    @TableField("GRANT_CODE")
    private String grantCode;
    /**
     * 经办机构编码
     */
    @TableField("INSTITUTION_CODE")
    private String institutionCode;
    /**
     * 介质识别号
     */
    @TableField("IDENT_NUMBER")
    private String identNumber;
    /**
     * 协议编号
     */
    @TableField("PROTSENO")
    private String protseNo;
    /**
     * 协议子序号
     */
    @TableField("SUBSERNO")
    private String subserNo;
    /**
     * 明细序号
     */
    @TableField("LISTNO")
    private String listNo;
    /**
     * 借据编号
     */
    @TableField("IOU_CODE")
    private String iouCode;

    /**
     * 许可证日期
     */
    @TableField("LICEDATE")
    private String liceDate;
    /**
     * 许可证序号
     */
    @TableField("SEQNO")
    private String seqNo;
    /**
     * 核心还款系统日期
     */
    @TableField("WORKDATE")
    private String workdate;

    public String getDebtCode() {
        return debtCode == "" ? null : debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public String getCurrency() {
        return currency == "" ? null : currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBusinessTypes() {
        return businessTypes == "" ? null : businessTypes;
    }

    public void setBusinessTypes(String businessTypes) {
        this.businessTypes = businessTypes;
    }

    public String getBusinessName() {
        return businessName == "" ? null : businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getGrantCode() {
        return grantCode == "" ? null : grantCode;
    }

    public void setGrantCode(String grantCode) {
        this.grantCode = grantCode;
    }

    public String getInstitutionCode() {
        return institutionCode == "" ? null : institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getIdentNumber() {
        return identNumber == "" ? null : identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getProtseNo() {
        return protseNo == "" ? null : protseNo;
    }

    public void setProtseNo(String protseNo) {
        this.protseNo = protseNo;
    }

    public String getSubserNo() {
        return subserNo == "" ? null : subserNo;
    }

    public void setSubserNo(String subserNo) {
        this.subserNo = subserNo;
    }

    public String getListNo() {
        return listNo == "" ? null : listNo;
    }

    public void setListNo(String listNo) {
        this.listNo = listNo;
    }

    public String getIouCode() {
        return iouCode == "" ? null : iouCode;
    }

    public void setIouCode(String iouCode) {
        this.iouCode = iouCode;
    }

    public String getLiceDate() {
        return liceDate == "" ? null : liceDate;
    }

    public void setLiceDate(String liceDate) {
        this.liceDate = liceDate;
    }

    public String getSeqNo() {
        return seqNo == "" ? null : seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getWorkdate() {
        return workdate == "" ? null : workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizRepaymentRec)) return false;
        BizRepaymentRec that = (BizRepaymentRec) o;
        return Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getCurrency(), that.getCurrency()) &&
                Objects.equal(getAmount(), that.getAmount()) &&
                Objects.equal(getBusinessTypes(), that.getBusinessTypes()) &&
                Objects.equal(getBusinessName(), that.getBusinessName()) &&
                Objects.equal(getGrantCode(), that.getGrantCode()) &&
                Objects.equal(getInstitutionCode(), that.getInstitutionCode()) &&
                Objects.equal(getIdentNumber(), that.getIdentNumber()) &&
                Objects.equal(getProtseNo(), that.getProtseNo()) &&
                Objects.equal(getSubserNo(), that.getSubserNo()) &&
                Objects.equal(getListNo(), that.getListNo()) &&
                Objects.equal(getIouCode(), that.getIouCode()) &&
                Objects.equal(getLiceDate(), that.getLiceDate()) &&
                Objects.equal(getSeqNo(), that.getSeqNo()) &&
                Objects.equal(getWorkdate(), that.getWorkdate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDebtCode(), getCurrency(), getAmount(), getBusinessTypes(), getBusinessName(), getGrantCode(), getInstitutionCode(), getIdentNumber(), getProtseNo(), getSubserNo(), getListNo(), getIouCode(), getLiceDate(), getSeqNo(), getWorkdate());
    }


}
