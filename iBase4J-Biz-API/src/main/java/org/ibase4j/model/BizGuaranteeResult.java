/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
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

	public BizGuaranteeResult() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("guaranType", guaranType)
				.add("grantCode", grantCode)
				.add("guaranPortType", guaranPortType)
				.add("clearRatio", clearRatio)
				.add("clearRatioAmt", clearRatioAmt)
				.add("notClearAmt", notClearAmt)
				.add("pledgeState", pledgeState)
				.add("pledgeName", pledgeName)
				.add("pledgeNo", pledgeNo)
				.add("schemeNo", schemeNo)
				.add("pledgeType", pledgeType)
				.add("guaranteeContractNo", guaranteeContractNo)
				.add("bnsContractNo", bnsContractNo)
				.toString();
	}
}
