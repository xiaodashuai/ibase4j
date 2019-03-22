/**
 * 
 */
package org.ibase4j.vo;

import java.io.Serializable;

/**
 * 描述：产品种类详情
 * 
 * @author czm
 * @version 1.0
 * @date 2018/7/13
 */
public class ProductVo implements Serializable {
	private String objType;//产品类型表名
	private String businessType;//产品编码
	private String businessName;//产品名称
	private String debtCode;//业务编号
	private Long properInfo;//产品专有信息外键
	private Integer numLimit;//办理笔数限制（1不限制；2限制）
	private Integer totalNum;//限制笔数
	private Integer usedNum;//发放的次数
	
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public Long getProperInfo() {
		return properInfo;
	}

	public void setProperInfo(Long properInfo) {
		this.properInfo = properInfo;
	}
	
	public Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}
	
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	
	public Integer getNumLimit() {
		return numLimit;
	}

	public void setNumLimit(Integer numLimit) {
		this.numLimit = numLimit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((debtCode == null) ? 0 : debtCode.hashCode());
		result = prime * result + ((objType == null) ? 0 : objType.hashCode());
		result = prime * result + ((properInfo == null) ? 0 : properInfo.hashCode());
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
		ProductVo other = (ProductVo) obj;
		if (businessType == null) {
			if (other.businessType != null){
				return false;
			}
		} else if (!businessType.equals(other.businessType)){
			return false;
		}
		if (debtCode == null) {
			if (other.debtCode != null){
				return false;
			}
		} else if (!debtCode.equals(other.debtCode)){
			return false;
		}
		if (objType == null) {
			if (other.objType != null){
				return false;
			}
		} else if (!objType.equals(other.objType)){
			return false;
		}
		if (properInfo == null) {
			if (other.properInfo != null){
				return false;
			}
		} else if (!properInfo.equals(other.properInfo)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProductVo [objType=" + objType + ", businessType=" + businessType + ", debtCode=" + debtCode
				+ ", properInfo=" + properInfo + "]";
	}

}
