package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;
/**
 * <p>
 * 国别风险评级
 * </p>
 *
 * @author XiaoYu
 * @since 2018-06-27
 */
@TableName("SYS_COUNTRY_RISK_RATING")
@SuppressWarnings("serial")
public class BizCountryRiskRating extends BaseModel {
	/**
	 * 国家编码
	 */
	@TableField("CODE_")
	private String code;
	/**
	 * 国家名称
	 */
	@TableField("NAME_CN")
	private String nameCN;
	/**
	 * 国家英文名
	 */
	@TableField("NAME_EN")
	private String nameEN;
	/**
	 * 国家简称
	 */
	@TableField("SHORT_EN")
	private String shortEN;
	/**
	 * 风险等级外键
	 */
	@TableField("RISK_LEVEL")
	private Long riskLevel;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNameCN() {
		return nameCN;
	}
	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}
	public String getNameEN() {
		return nameEN;
	}
	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}
	public String getShortEN() {
		return shortEN;
	}
	public void setShortEN(String shortEN) {
		this.shortEN = shortEN;
	}
	public Long getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(Long riskLevel) {
		this.riskLevel = riskLevel;
	}
	@Override
	public String toString() {
		return "CountryRiskRating [code=" + code + ", nameCN=" + nameCN + ", nameEN=" + nameEN + ", shortEN=" + shortEN
				+ ", riskLevel=" + riskLevel + ", toString()=" + super.toString() + "]";
	}
	
	
}
