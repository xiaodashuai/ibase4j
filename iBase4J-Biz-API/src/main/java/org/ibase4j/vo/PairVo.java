/**
 * 
 */
package org.ibase4j.vo;

import com.baomidou.mybatisplus.annotations.TableField;

import java.util.Set;

/**
 * 描述：健值对
 * 
 * @author czm
 * @version 1.0
 */
public class PairVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	private String code;
	private String name;
	private String parentCode;
	private boolean children;
	private boolean checked;
	private Set<PairVo> chirenList;
	private String type;

	

	/**
	 * 债项方案id
	 */
	private String debtCode;
	
	/**
	 * 授信额度信息外键,用于用信主体的存取值
	 */
	@TableField(exist = false)
	private Long creditLinesId;
	/**
	 * 产品用信比例，用于用信主体的存取值
	 */
	@TableField(exist = false)
	private String creditRatio;

	/**
	 * 行业投向，用于风险分析的存取值
	 */
	private String industryTo;
	/**
	 * 背景国别，用于风险分析的存取值
	 */
	private String tbon;


	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getIndustryTo() {
		return industryTo;
	}

	public void setIndustryTo(String industryTo) {
		this.industryTo = industryTo;
	}

	public String getTbon() {
		return tbon;
	}

	public void setTbon(String tbon) {
		this.tbon = tbon;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

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

	public boolean isChildren() {
		return children;
	}

	public void setChildren(boolean children) {
		this.children = children;
	}

	public Set<PairVo> getChirenList() {
		return chirenList;
	}

	public void setChirenList(Set<PairVo> chirenList) {
		this.chirenList = chirenList;
	}

	public Long getCreditLinesId() {
		return creditLinesId;
	}

	public void setCreditLinesId(Long creditLinesId) {
		this.creditLinesId = creditLinesId;
	}

	public String getCreditRatio() {
		return creditRatio;
	}

	public void setCreditRatio(String creditRatio) {
		this.creditRatio = creditRatio;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PairVo other = (PairVo) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "PairVo [code=" + code + ", name=" + name + "]";
	}

}
