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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentCode == null) ? 0 : parentCode.hashCode());
		result = prime * result + ((srv1 == null) ? 0 : srv1.hashCode());
		result = prime * result + ((srv2 == null) ? 0 : srv2.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		BizGuaranteeType other = (BizGuaranteeType) obj;
		if (children == null) {
			if (other.children != null){
				return false;
			}
		} else if (!children.equals(other.children)){
			return false;
		}
		if (code == null) {
			if (other.code != null){
				return false;
			}
		} else if (!code.equals(other.code)){
			return false;
		}
		if (name == null) {
			if (other.name != null){
				return false;
			}
		} else if (!name.equals(other.name)){
			return false;
		}
		if (parentCode == null) {
			if (other.parentCode != null){
				return false;
			}
		} else if (!parentCode.equals(other.parentCode)){
			return false;
		}
		if (srv1 == null) {
			if (other.srv1 != null){
				return false;
			}
		} else if (!srv1.equals(other.srv1)){
			return false;
		}
		if (srv2 == null) {
			if (other.srv2 != null){
				return false;
			}
		} else if (!srv2.equals(other.srv2)){
			return false;
		}
		if (type == null) {
			if (other.type != null){
				return false;
			}
		} else if (!type.equals(other.type)){
			return false;
		}
		return true;
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
