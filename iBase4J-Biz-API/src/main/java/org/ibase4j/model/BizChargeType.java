/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chargeCode == null) ? 0 : chargeCode.hashCode());
		result = prime * result + ((chargeName == null) ? 0 : chargeName.hashCode());
		result = prime * result + ((productTypesCode == null) ? 0 : productTypesCode.hashCode());
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
		BizChargeType other = (BizChargeType) obj;
		if (chargeCode == null) {
			if (other.chargeCode != null){
				return false;
			}
		} else if (!chargeCode.equals(other.chargeCode)){
			return false;
		}
		if (chargeName == null) {
			if (other.chargeName != null){
				return false;
			}
		} else if (!chargeName.equals(other.chargeName)){
			return false;
		}
		if (productTypesCode == null) {
			if (other.productTypesCode != null){
				return false;
			}
		} else if (!productTypesCode.equals(other.productTypesCode)){
			return false;
		}
		return true;
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
