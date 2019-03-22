/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

/**
 * <p>
 * 货币单位管理
 * </p>
 * 
 * @author czm
 * @version 1.0
 * @since 2017-03-12
 */
@TableName("SYS_CURRENCY")
public class SysCurrency extends BaseModel {
	private static final long serialVersionUID = 1L;
	@TableField("COUNTRY_CODE") // 使用的国家编码
	private String countryCode;
	@TableField("MON_NO") // 货币序号
	private String monNo;
	@TableField("MON_CODE") // 货币代码
	private String monCode;
	@TableField("CODE_NAME") // 国家货币名称
	private String codeName;

	/*
	判断是否勾选辅助币种
	xiaoshuiquan，2018.8.1
	*/
    @TableField(exist=false)
	private Boolean checked;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMonNo() {
		return monNo;
	}

	public void setMonNo(String monNo) {
		this.monNo = monNo;
	}

	public String getMonCode() {
		return monCode;
	}

	public void setMonCode(String monCode) {
		this.monCode = monCode;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeName == null) ? 0 : codeName.hashCode());
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((monCode == null) ? 0 : monCode.hashCode());
		result = prime * result + ((monNo == null) ? 0 : monNo.hashCode());
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
		SysCurrency other = (SysCurrency) obj;
		if (codeName == null) {
			if (other.codeName != null){
				return false;

			}
		} else if (!codeName.equals(other.codeName)){
			return false;

		}
		if (countryCode == null) {
			if (other.countryCode != null){
				return false;

			}
		} else if (!countryCode.equals(other.countryCode)){
			return false;

		}
		if (monCode == null) {
			if (other.monCode != null){
				return false;

			}
		} else if (!monCode.equals(other.monCode)){
			return false;

		}
		if (monNo == null) {
			if (other.monNo != null){
				return false;

			}
		} else if (!monNo.equals(other.monNo)){
			return false;

		}
		return true;
	}

	@Override
	public String toString() {
		return "SysCurrency [countryCode=" + countryCode + ", monNo=" + monNo + ", monCode=" + monCode + ", codeName="
				+ codeName + "]";
	}

}
