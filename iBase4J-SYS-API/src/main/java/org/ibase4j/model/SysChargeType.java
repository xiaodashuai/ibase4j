package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;
//费用类型
@TableName("biz_charge_type")
@SuppressWarnings("serial")
public class SysChargeType extends BaseModel {
	//费用代码
	@TableField("CHARGE_CODE")
	private String chargeCode;
	// 费用名称
	@TableField("CHARGE_NAME")
	private String chargeName;
	//所属产品类别代码
	@TableField("PRODUCT_TYPES_CODE")
	private String productTyepsCode;
	//所属产品类别名称
	@TableField(exist = false)
	private String productTypesName;
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
	public String getProductTyepsCode() {
		return productTyepsCode;
	}
	public void setProductTyepsCode(String productTyepsCode) {
		this.productTyepsCode = productTyepsCode;
	}
	public String getProductTypesName() {
		return productTypesName;
	}
	public void setProductTypesName(String productTypesName) {
		this.productTypesName = productTypesName;
	}
	@Override
	public String toString() {
		return "SysChargeType [chargeCode=" + chargeCode + ", chargeName=" + chargeName + ", productTyepsCode="
				+ productTyepsCode + ", productTypesName=" + productTypesName + ", toString()=" + super.toString()
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chargeCode == null) ? 0 : chargeCode.hashCode());
		result = prime * result + ((chargeName == null) ? 0 : chargeName.hashCode());
		result = prime * result + ((productTyepsCode == null) ? 0 : productTyepsCode.hashCode());
		result = prime * result + ((productTypesName == null) ? 0 : productTypesName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysChargeType other = (SysChargeType) obj;
		if (chargeCode == null) {
			if (other.chargeCode != null)
				return false;
		} else if (!chargeCode.equals(other.chargeCode))
			return false;
		if (chargeName == null) {
			if (other.chargeName != null)
				return false;
		} else if (!chargeName.equals(other.chargeName))
			return false;
		if (productTyepsCode == null) {
			if (other.productTyepsCode != null)
				return false;
		} else if (!productTyepsCode.equals(other.productTyepsCode))
			return false;
		if (productTypesName == null) {
			if (other.productTypesName != null)
				return false;
		} else if (!productTypesName.equals(other.productTypesName))
			return false;
		return true;
	}
	
}
