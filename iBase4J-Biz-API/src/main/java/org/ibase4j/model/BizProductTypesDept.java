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
 * 描述：产品种类（品种）与机构的关系表<br/>
 * 时间：2018-04-02
 * 
 * @author czm
 * @version 1.0
 */
@TableName("BIZ_PRODUCT_TYPES_ORG")
@SuppressWarnings("serial")
public class BizProductTypesDept extends BaseModel implements Serializable {
	@TableField("DEPT_CODE") // 部门编码（外键）
	private String deptCode;
	@TableField("PROD_QUOTA_LMT") // 产品额度限制
	private String prodQuotaLmt;
	@TableField("PRODUCT_TYPE_CODE") // 种类编码（外键）
	private String productTypes;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getProdQuotaLmt() {
		return prodQuotaLmt;
	}

	public void setProdQuotaLmt(String prodQuotaLmt) {
		this.prodQuotaLmt = prodQuotaLmt;
	}

	public String getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String productTypes) {
		this.productTypes = productTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((prodQuotaLmt == null) ? 0 : prodQuotaLmt.hashCode());
		result = prime * result + ((productTypes == null) ? 0 : productTypes.hashCode());
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
		BizProductTypesDept other = (BizProductTypesDept) obj;
		if (deptCode == null) {
			if (other.deptCode != null){
				return false;
			}
		} else if (!deptCode.equals(other.deptCode)){
			return false;
		}
		if (prodQuotaLmt == null) {
			if (other.prodQuotaLmt != null){
				return false;
			}
		} else if (!prodQuotaLmt.equals(other.prodQuotaLmt)){
			return false;
		}
		if (productTypes == null) {
			if (other.productTypes != null){
				return false;
			}
		} else if (!productTypes.equals(other.productTypes)){
			return false;
		}
		return true;
	}

	public BizProductTypesDept() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("deptCode", deptCode)
				.add("prodQuotaLmt", prodQuotaLmt)
				.add("productTypes", productTypes)
				.toString();
	}
}
