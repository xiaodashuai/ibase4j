/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能： 收息收费表 
 * 日期：2018/7/6
 * @author czm
 */
@TableName("BIZ_FEP")
public class BizFEP extends BaseModel implements Serializable {
	/**
	 * 费用代码
	 */
	@TableField("FEECOD")
	private String feecod;
	/**
	 * 对象表名称
	 */
	@TableField("OBJTYP")
	private String objTyp;
	/**
	 * 对象表INR
	 */
	@TableField("OBJINR")
	private Long objInr;
	/**
	 * 费率类型
	 */
	@TableField("RATE_TYPE")
	private String rateType;
	/**
	 * 费率值
	 */
	@TableField("RATE_VALUE")
	private BigDecimal rateValue;

	public String getFeecod() {
		return feecod;
	}

	public void setFeecod(String feecod) {
		this.feecod = feecod;
	}

	public String getObjTyp() {
		return objTyp;
	}

	public void setObjTyp(String objTyp) {
		this.objTyp = objTyp;
	}

	public Long getObjInr() {
		return objInr;
	}

	public void setObjInr(Long objInr) {
		this.objInr = objInr;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public BigDecimal getRateValue() {
		return rateValue;
	}

	public void setRateValue(BigDecimal rateValue) {
		this.rateValue = rateValue;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizFEP)) return false;
		BizFEP bizFEP = (BizFEP) o;
		return Objects.equal(getFeecod(), bizFEP.getFeecod()) &&
				Objects.equal(getObjTyp(), bizFEP.getObjTyp()) &&
				Objects.equal(getObjInr(), bizFEP.getObjInr()) &&
				Objects.equal(getRateType(), bizFEP.getRateType()) &&
				Objects.equal(getRateValue(), bizFEP.getRateValue());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getFeecod(), getObjTyp(), getObjInr(), getRateType(), getRateValue());
	}

	public BizFEP() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("feecod", feecod)
				.add("objTyp", objTyp)
				.add("objInr", objInr)
				.add("rateType", rateType)
				.add("rateValue", rateValue)
				.toString();
	}
}
