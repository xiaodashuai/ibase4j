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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizProductTypesDept)) return false;
		BizProductTypesDept that = (BizProductTypesDept) o;
		return Objects.equal(getDeptCode(), that.getDeptCode()) &&
				Objects.equal(getProdQuotaLmt(), that.getProdQuotaLmt()) &&
				Objects.equal(getProductTypes(), that.getProductTypes());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDeptCode(), getProdQuotaLmt(), getProductTypes());
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
