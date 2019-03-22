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
 * 功能：业务类型表 日期：2018/7/17
 * 
 * @author czm
 * @version 1.0
 */
@TableName("BIZ_GUARANTEE_TYPE")
@SuppressWarnings("serial")
public class BizGuaranteeType extends BaseModel implements Serializable {
	/**
	 * 代码
	 */
	@TableField("CODE_")
	private String code;
	/**
	 * 名称
	 */
	@TableField("NAME_")
	private String name;
	/**
	 * 父代码
	 */
	@TableField("PARENT_CODE")
	private String parentCode;
	/**
	 * 代码
	 */
	@TableField("TYPE_")
	private String type;
	/**
	 * 是否有子类（1有子类0无子类）
	 */
	@TableField("CHILDREN")
	private Integer children;
	/**
	 * 冗余字段1
	 */
	@TableField("SRV1")
	private String srv1;
	/**
	 * 冗余字段1
	 */
	@TableField("SRV2")
	private String srv2;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}

	public String getSrv1() {
		return srv1;
	}

	public void setSrv1(String srv1) {
		this.srv1 = srv1;
	}

	public String getSrv2() {
		return srv2;
	}

	public void setSrv2(String srv2) {
		this.srv2 = srv2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizGuaranteeType)) return false;
		BizGuaranteeType that = (BizGuaranteeType) o;
		return Objects.equal(getCode(), that.getCode()) &&
				Objects.equal(getName(), that.getName()) &&
				Objects.equal(getParentCode(), that.getParentCode()) &&
				Objects.equal(getType(), that.getType()) &&
				Objects.equal(getChildren(), that.getChildren()) &&
				Objects.equal(getSrv1(), that.getSrv1()) &&
				Objects.equal(getSrv2(), that.getSrv2());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getCode(), getName(), getParentCode(), getType(), getChildren(), getSrv1(), getSrv2());
	}

	public BizGuaranteeType() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("code", code)
				.add("name", name)
				.add("parentCode", parentCode)
				.add("type", type)
				.add("children", children)
				.add("srv1", srv1)
				.add("srv2", srv2)
				.toString();
	}
}
