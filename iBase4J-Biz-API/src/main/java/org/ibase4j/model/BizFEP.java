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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((feecod == null) ? 0 : feecod.hashCode());
		result = prime * result + ((objInr == null) ? 0 : objInr.hashCode());
		result = prime * result + ((objTyp == null) ? 0 : objTyp.hashCode());
		result = prime * result + ((rateType == null) ? 0 : rateType.hashCode());
		result = prime * result + ((rateValue == null) ? 0 : rateValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		BizFEP other = (BizFEP) obj;
		if (feecod == null) {
			if (other.feecod != null){
				return false;
			}
		} else if (!feecod.equals(other.feecod)){
			return false;
		}
		if (objInr == null) {
			if (other.objInr != null){
				return false;
			}
		} else if (!objInr.equals(other.objInr)){
			return false;
		}
		if (objTyp == null) {
			if (other.objTyp != null){
				return false;
			}
		} else if (!objTyp.equals(other.objTyp)){
			return false;
		}
		if (rateType == null) {
			if (other.rateType != null){
				return false;
			}
		} else if (!rateType.equals(other.rateType)){
			return false;
		}
		if (rateValue == null) {
			if (other.rateValue != null){
				return false;
			}
		} else if (!rateValue.equals(other.rateValue)){
			return false;
		}
		return true;
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
