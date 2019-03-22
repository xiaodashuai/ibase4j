package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：远期结售汇
 * 
 * @author xiaoshuiquan
 * @date 2018/6/20
 */

@TableName("BIZ_DELIVERABLE_FORWARDS")
public class BizFutForeExcSett extends BaseModel implements Serializable {

	/**
	 * 业务编号
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 产品种类
	 */
	@TableField("BUSINESS_TYPES")
	private String businessTypes;
	/**
	 * 业务描述
	 */
	@TableField("BUSINESS_REMARK")
	private String businessRemark;
	/**
	 * 业务价差
	 */
	@TableField("BNS_DIFF_PRICE")
	private Long bnsDiffPrice;
	/**
	 * 业务价差情况
	 */
	@TableField("BNS_DIFF_PRICE_REMARK")
	private String bnsDiffPriceRemark;
	/**
	 * 申请人名称
	 */
	@TableField("PROPOSER")
	private Long proposer;
	/**
	 * 远期期限
	 */
	@TableField("FORWARD_PERIOD")
	private Long forwaroPeriod;

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(String businessTypes) {
		this.businessTypes = businessTypes;
	}

	public String getBusinessRemark() {
		return businessRemark;
	}

	public void setBusinessRemark(String businessRemark) {
		this.businessRemark = businessRemark;
	}

	public Long getBnsDiffPrice() {
		return bnsDiffPrice;
	}

	public void setBnsDiffPrice(Long bnsDiffPrice) {
		this.bnsDiffPrice = bnsDiffPrice;
	}

	public String getBnsDiffPriceRemark() {
		return bnsDiffPriceRemark;
	}

	public void setBnsDiffPriceRemark(String bnsDiffPriceRemark) {
		this.bnsDiffPriceRemark = bnsDiffPriceRemark;
	}

	public Long getProposer() {
		return proposer;
	}

	public void setProposer(Long proposer) {
		this.proposer = proposer;
	}

	public Long getForwaroPeriod() {
		return forwaroPeriod;
	}

	public void setForwaroPeriod(Long forwaroPeriod) {
		this.forwaroPeriod = forwaroPeriod;
	}

	public BizFutForeExcSett() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("businessTypes", businessTypes)
				.add("businessRemark", businessRemark)
				.add("bnsDiffPrice", bnsDiffPrice)
				.add("bnsDiffPriceRemark", bnsDiffPriceRemark)
				.add("proposer", proposer)
				.add("forwaroPeriod", forwaroPeriod)
				.toString();
	}
}