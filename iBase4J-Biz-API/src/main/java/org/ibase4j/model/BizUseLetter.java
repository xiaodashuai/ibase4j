package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：产品业务表
 * 
 * @author xiaoshuiquan
 * @date 2018/05/26
 */

@TableName("BIZ_PRODUCT_BUSSINESS")
public class BizUseLetter extends BaseModel implements Serializable {

	/**
	 * 业务细类编号
	 */
	@TableField("BUSINESS_CLASS_CODE")
	private String bcCode;
	/**
	 * 产品生效日期
	 */
	@TableField("PRODUCT_EFFECTIVE_DATE")
	private Date pefDate;
	/**
	 * 产品失效日期
	 */
	@TableField("PRODUCT_EXPIRY_DATE")
	private Date pxDate;
	/**
	 * 产品循环标识
	 */
	@TableField("PRODUCT_CYCLE_IDENTIFICATION")
	private Number pci;
	/**
	 * 方案生效日期
	 */
	@TableField("PE_DATE")
	private Date peDate;
	/**
	 * 方案失效日期
	 */
	@TableField("SF_DATE")
	private Date sfDate;
	/**
	 * 产品额度类型
	 */
	@TableField("PL_TYPE")
	private String plType;
	/**
	 * 产品用信比例
	 */
	@TableField("PR_RATIO")
	private Number prRatio;
	/**
	 * 债项编号
	 */
	@TableField("DEBT_NUM")
	private Number debtNum;

	public String getBcCode() {
		return bcCode;
	}

	public void setBcCode(String bcCode) {
		this.bcCode = bcCode;
	}

	public Date getPefDate() {
		return pefDate;
	}

	public void setPefDate(Date pefDate) {
		this.pefDate = pefDate;
	}

	public Date getPxDate() {
		return pxDate;
	}

	public void setPxDate(Date pxDate) {
		this.pxDate = pxDate;
	}

	public Number getPci() {
		return pci;
	}

	public void setPci(Number pci) {
		this.pci = pci;
	}

	public Date getPeDate() {
		return peDate;
	}

	public void setPeDate(Date peDate) {
		this.peDate = peDate;
	}

	public Date getSfDate() {
		return sfDate;
	}

	public void setSfDate(Date sfDate) {
		this.sfDate = sfDate;
	}

	public String getPlType() {
		return plType;
	}

	public void setPlType(String plType) {
		this.plType = plType;
	}

	public Number getPrRatio() {
		return prRatio;
	}

	public void setPrRatio(Number prRatio) {
		this.prRatio = prRatio;
	}

	public Number getDebtNum() {
		return debtNum;
	}

	public void setDebtNum(Number debtNum) {
		this.debtNum = debtNum;
	}

	public BizUseLetter() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("bcCode", bcCode)
				.add("pefDate", pefDate)
				.add("pxDate", pxDate)
				.add("pci", pci)
				.add("peDate", peDate)
				.add("sfDate", sfDate)
				.add("plType", plType)
				.add("prRatio", prRatio)
				.add("debtNum", debtNum)
				.toString();
	}
}
