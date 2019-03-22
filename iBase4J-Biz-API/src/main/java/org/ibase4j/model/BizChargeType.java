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

/**
 * 功能：收费类型
 * 日期：2018/7/17
 * @author czm 
 * @version 1.0
 */
@TableName("BIZ_CHARGE_TYPE")
@SuppressWarnings("serial")
public class BizChargeType extends BaseModel implements Serializable {
	/**
	 * 业务品种ID
	 */
	@TableField("PRODUCT_TYPES_CODE")
	private String productTypesCode;
	/**
	 * 费用代码
	 */
	@TableField("CHARGE_CODE")
	private String chargeCode;
	/**
	 * 费用名称
	 */
	@TableField("CHARGE_NAME")
	private String chargeName;

	
	public String getProductTypesCode() {
		return productTypesCode;
	}

	public void setProductTypesCode(String productTypesCode) {
		this.productTypesCode = productTypesCode;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizChargeType)) return false;
		BizChargeType that = (BizChargeType) o;
		return Objects.equal(getProductTypesCode(), that.getProductTypesCode()) &&
				Objects.equal(getChargeCode(), that.getChargeCode()) &&
				Objects.equal(getChargeName(), that.getChargeName());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getProductTypesCode(), getChargeCode(), getChargeName());
	}

	public BizChargeType() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("productTypesCode", productTypesCode)
				.add("chargeCode", chargeCode)
				.add("chargeName", chargeName)
				.toString();
	}
}
