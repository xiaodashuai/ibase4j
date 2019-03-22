/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能： 发放流程中的担保信息 
 * 日期：2018/7/6
 * @author czm
 */
@TableName("BIZ_GUARANTEE_RESULT")
public class BizGuaranteeResult extends BaseModel implements Serializable {
	/**
	 * 担保类型
	 */
	@TableField("GUARAN_TYPE")
	private String guaranType;
	/**
	 * 发放编码
	 */
	@TableField("GRANT_CODE")
	private String grantCode;
	/**
	 * 担保份额划分类型
	 */
	@TableField("GUARAN_PORT_TYPE")
	private String guaranPortType;
	/**
	 * 可明确划分使用本担保方式的份额
	 */
	@TableField("CLEAR_RATIO")
	private BigDecimal clearRatio;
	/**
	 * 可明确划分使用本担保方式的金额
	 */
	@TableField("CLEAR_RATIO_AMT")
	private BigDecimal clearRatioAmt;
	/**
	 * 不可明确划分使用本担保方式的金额
	 */
	@TableField("NOT_CLEAR_AMT")
	private BigDecimal notClearAmt;
	/**
	 * 押品状态
	 */
	@TableField("PLEDGE_STATE")
	private String pledgeState;
	/**
	 * 押品名称
	 */
	@TableField("PLEDGE_NAME")
	private String pledgeName;
	/**
	 * 抵押担保品编号
	 */
	@TableField("PLEDGE_NO")
	private String pledgeNo;
	/**
	 * 授信方案编号
	 */
	@TableField("SCHEME_NO")
	private String schemeNo;
	/**
	 * 押品类型(1抵押类2质押类)
	 */
	@TableField("PLEDGE_TYPE")
	private String pledgeType;
	/**
	 * 担保合同编号
	 */
	@TableField("GUARANTEE_CONTRACT_NO")
	private String guaranteeContractNo;
	/**
	 * 业务（租金保理）合同编号
	 */
	@TableField("BNS_CONTRACT_NO")
	private String bnsContractNo;


	/**
	 * 担保人客户编号
	 */
	@TableField("GUARANTOR_CUST_ID")
	private String  guarantorCustId;

	/**
	 * 修改次数
	 */
	@TableField("CHANGE_NUM")
	private int changeNum;

	/**
	 * 担保方式
	 */
	@TableField("TYPE_POINT")
	private String  typePoint;


	public String getGuaranType() {
		return guaranType;
	}

	public void setGuaranType(String guaranType) {
		this.guaranType = guaranType;
	}

	public String getGuaranPortType() {
		return guaranPortType;
	}

	public void setGuaranPortType(String guaranPortType) {
		this.guaranPortType = guaranPortType;
	}

	public BigDecimal getClearRatio() {
		return clearRatio;
	}

	public void setClearRatio(BigDecimal clearRatio) {
		this.clearRatio = clearRatio;
	}

	public BigDecimal getClearRatioAmt() {
		return clearRatioAmt;
	}

	public void setClearRatioAmt(BigDecimal clearRatioAmt) {
		this.clearRatioAmt = clearRatioAmt;
	}

	public BigDecimal getNotClearAmt() {
		return notClearAmt;
	}

	public void setNotClearAmt(BigDecimal notClearAmt) {
		this.notClearAmt = notClearAmt;
	}

	public String getPledgeState() {
		return pledgeState;
	}

	public void setPledgeState(String pledgeState) {
		this.pledgeState = pledgeState;
	}

	public String getPledgeName() {
		return pledgeName;
	}

	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getGuaranteeContractNo() {
		return guaranteeContractNo;
	}

	public void setGuaranteeContractNo(String guaranteeContractNo) {
		this.guaranteeContractNo = guaranteeContractNo;
	}

	public String getBnsContractNo() {
		return bnsContractNo;
	}

	public void setBnsContractNo(String bnsContractNo) {
		this.bnsContractNo = bnsContractNo;
	}

	public String getPledgeType() {
		return pledgeType;
	}

	public void setPledgeType(String pledgeType) {
		this.pledgeType = pledgeType;
	}

	public String getPledgeNo() {
		return pledgeNo;
	}

	public void setPledgeNo(String pledgeNo) {
		this.pledgeNo = pledgeNo;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public String getGuarantorCustId() {
		return guarantorCustId;
	}

	public void setGuarantorCustId(String guarantorCustId) {
		this.guarantorCustId = guarantorCustId;
	}


	public int getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(int changeNum) {
		this.changeNum = changeNum;
	}

	public String getTypePoint() {
		return typePoint == "" ? null : typePoint;
	}

	public void setTypePoint(String typePoint) {
		this.typePoint = typePoint;
	}

	public BizGuaranteeResult() {
	}

	@Override
	public String toString() {
		return "BizGuaranteeResult{" +
				"guaranType='" + guaranType + '\'' +
				", grantCode='" + grantCode + '\'' +
				", guaranPortType='" + guaranPortType + '\'' +
				", clearRatio=" + clearRatio +
				", clearRatioAmt=" + clearRatioAmt +
				", notClearAmt=" + notClearAmt +
				", pledgeState='" + pledgeState + '\'' +
				", pledgeName='" + pledgeName + '\'' +
				", pledgeNo='" + pledgeNo + '\'' +
				", schemeNo='" + schemeNo + '\'' +
				", pledgeType='" + pledgeType + '\'' +
				", guaranteeContractNo='" + guaranteeContractNo + '\'' +
				", bnsContractNo='" + bnsContractNo + '\'' +
				", guarantorCustId='" + guarantorCustId + '\'' +
				", changeNum='" + changeNum + '\'' +
				", typePoint='" + typePoint + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizGuaranteeResult)) return false;
		BizGuaranteeResult that = (BizGuaranteeResult) o;
		return Objects.equal(getGuaranType(), that.getGuaranType()) &&
				Objects.equal(getGrantCode(), that.getGrantCode()) &&
				Objects.equal(getGuaranPortType(), that.getGuaranPortType()) &&
				Objects.equal(getClearRatio(), that.getClearRatio()) &&
				Objects.equal(getClearRatioAmt(), that.getClearRatioAmt()) &&
				Objects.equal(getNotClearAmt(), that.getNotClearAmt()) &&
				Objects.equal(getPledgeState(), that.getPledgeState()) &&
				Objects.equal(getPledgeName(), that.getPledgeName()) &&
				Objects.equal(getPledgeNo(), that.getPledgeNo()) &&
				Objects.equal(getSchemeNo(), that.getSchemeNo()) &&
				Objects.equal(getPledgeType(), that.getPledgeType()) &&
				Objects.equal(getGuaranteeContractNo(), that.getGuaranteeContractNo()) &&
				Objects.equal(getGuarantorCustId(), that.getGuarantorCustId())&&
				Objects.equal(getChangeNum(), that.getChangeNum())&&
				Objects.equal(getTypePoint(), that.getTypePoint()
				);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getGuaranType(), getGrantCode(), getGuaranPortType(), getClearRatio(), getClearRatioAmt(), getNotClearAmt(), getPledgeState(), getPledgeName(), getPledgeNo(), getSchemeNo(), getPledgeType(), getGuaranteeContractNo(), getBnsContractNo(),getGuarantorCustId(), getChangeNum(), getTypePoint());
	}
}
