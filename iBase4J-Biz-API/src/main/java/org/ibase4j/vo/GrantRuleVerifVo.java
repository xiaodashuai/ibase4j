/**
 * 
 */
package org.ibase4j.vo;

import org.ibase4j.core.util.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 * 描述：产品发放过程中需要验证是否可发放的规则判断
 * 
 * @author czm
 * @version 1.0
 * @date 2018/7/26
 */
public class GrantRuleVerifVo implements Serializable {
	// 产品规则类型(1:全局产品规则、2:单一产品规则)
	private Integer ruleType;
	// 方案生效日期
	private String efeectiveDate;
	// 方案失效日期
	private String expiDate;
	// 方案生效日期
	private Timestamp efeectiveDatedt;
	// 方案失效日期
	private Timestamp expiDatedt;
	// 方案可办理笔数限制(1不限制、2限制)
	private Integer ltnopa;
	// 方案可办理笔数
	private Integer tdwln;
	// 总金额
	private BigDecimal amt;
	// 总金额字符串
	private String amtStr;
	// 方案业务期限范围
	private Integer scopeBnsPeriod;
	// 方案循环标志(1是；0否）
	private Integer loopFlag;
	// 产品可办理笔数限制(1不限制、2限制)
	private Integer numLimit;
	// 产品可办理笔数
	private Integer dealNum;
	// 已发放的笔数
	private Integer usedNum;
	//方案费率形式（1方案费率、2方案费率范围）
	private Integer schemeRateForm;
	//方案费率PACKAGE_RATE
	private BigDecimal packageRate;
	//方案费率范围最低值
	private BigDecimal rateRangeMix;
	//方案费率范围最高值
	private BigDecimal rateRangeMax;
	//方案辅助币种(0无1其他可选币种2等值其他币种)
	private String aCurrency;
	//方案主币种
	private String mCurrency;
	// 发放编码
	private String grantCode;
	//方案费率类型(0无、1优惠费率、2非优惠费率)
	private Integer progRateType;

	public Timestamp getEfeectiveDatedt() {
		return efeectiveDatedt;
	}

	public void setEfeectiveDatedt(Timestamp efeectiveDatedt) {
		this.efeectiveDatedt = efeectiveDatedt;
	}
	
	public String getaCurrency() {
		return aCurrency;
	}

	public void setaCurrency(String aCurrency) {
		this.aCurrency = aCurrency;
	}
	
	public String getmCurrency() {
		return mCurrency;
	}

	public void setmCurrency(String mCurrency) {
		this.mCurrency = mCurrency;
	}

	public Timestamp getExpiDatedt() {
		return expiDatedt;
	}

	public void setExpiDatedt(Timestamp expiDatedt) {
		this.expiDatedt = expiDatedt;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public String getEfeectiveDate() {
		if(efeectiveDatedt!=null){
			this.efeectiveDate = DateUtil.format(efeectiveDatedt, "yyyy-MM-dd HH:mm:ss");
		}
		return efeectiveDate;
	}

	public void setEfeectiveDate(String efeectiveDate) {
		this.efeectiveDate = efeectiveDate;
	}

	public String getExpiDate() {
		if(expiDatedt!=null){
			this.expiDate = DateUtil.format(expiDatedt, "yyyy-MM-dd HH:mm:ss");
		}
		return expiDate;
	}

	public void setExpiDate(String expiDate) {
		this.expiDate = expiDate;
	}

	public Integer getLtnopa() {
		return ltnopa;
	}

	public void setLtnopa(Integer ltnopa) {
		this.ltnopa = ltnopa;
	}

	public Integer getTdwln() {
		return tdwln;
	}

	public void setTdwln(Integer tdwln) {
		this.tdwln = tdwln;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	/**
	 * 转换金额
	 * @return
	 */
	public String getAmtStr(){
		if(amt != null){
			DecimalFormat numberFormat = new DecimalFormat(",###.00");
			return numberFormat.format(amt);
		}else{
			return "0";
		}
	}
	
	public void setAmtStr(String amtStr) {
		this.amtStr = amtStr;
	}

	public Integer getScopeBnsPeriod() {
		return scopeBnsPeriod;
	}

	public void setScopeBnsPeriod(Integer scopeBnsPeriod) {
		this.scopeBnsPeriod = scopeBnsPeriod;
	}

	public Integer getLoopFlag() {
		return loopFlag;
	}

	public void setLoopFlag(Integer loopFlag) {
		this.loopFlag = loopFlag;
	}

	public Integer getNumLimit() {
		return numLimit;
	}

	public void setNumLimit(Integer numLimit) {
		this.numLimit = numLimit;
	}

	public Integer getDealNum() {
		return dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	public Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public Integer getSchemeRateForm() {
		return schemeRateForm;
	}

	public void setSchemeRateForm(Integer schemeRateForm) {
		this.schemeRateForm = schemeRateForm;
	}

	public BigDecimal getPackageRate() {
		return packageRate;
	}

	public void setPackageRate(BigDecimal packageRate) {
		this.packageRate = packageRate;
	}

	public BigDecimal getRateRangeMix() {
		return rateRangeMix;
	}

	public void setRateRangeMix(BigDecimal rateRangeMix) {
		this.rateRangeMix = rateRangeMix;
	}

	public BigDecimal getRateRangeMax() {
		return rateRangeMax;
	}

	public void setRateRangeMax(BigDecimal rateRangeMax) {
		this.rateRangeMax = rateRangeMax;
	}

	public Integer getProgRateType() {
		return progRateType;
	}

	public void setProgRateType(Integer progRateType) {
		this.progRateType = progRateType;
	}
	
}
