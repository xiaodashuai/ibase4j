/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：利率表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_FEC")
public class BizFEC extends BaseModel implements Serializable {
	/**
	 * 业务类型
	 */
	@TableField("OBJTYP")
	private String objTyp;
	/**
	 * 业务对象INR
	 */
	@TableField("OBJINR")
	private Long objInr;
	/**
	 * 利率规则描述
	 */
	@TableField("RULES")
	private String rules;
	/**
	 * 币种
	 */
	@TableField("CURRENCY")
	private String currency;
	/**
	 * 折算牌价
	 */
	@TableField("CONVERTED_PRICE")
	private BigDecimal convertedPrice;
	/**
	 * 发放金额
	 */
	@TableField("PAYMENT_AMT")
	private BigDecimal paymentAmt;
	/**
	 * 计息标志
	 */
	@TableField("IR_SIGN")
	private Integer irSign;
	/**
	 * 利息类型（1固定、2浮动）
	 */
	@TableField("TYPE")
	private Integer type;
	/**
	 * 利率基准
	 */
	@TableField("IR_BK")
	private String irBk;
	/**
	 * 利率期限
	 */
	@TableField("TERM")
	private String term;
	/**
	 * 利率浮动方向
	 */
	@TableField("FLOAT_DIRECTION")
		private String floatDirectioin;
	/**
	 * 利率浮动方式
	 */
	@TableField("FLOAT_MODE")
	private String floatMode;
	/**
	 * 浮动比例
	 */
	@TableField("SLIDING_SCALES")
	private BigDecimal slidingScales;
	/**
	 * 浮动率
	 */
	@TableField("FLOATING_RATE")
	private BigDecimal floatingRate;
	/**
	 * 利率值
	 */
	@TableField("RATE_VAL")
	private BigDecimal rateVal;
	/**
	 * 利率变动周期
	 */
	@TableField("VAR_CYCLE")
	private Integer varCycle;
	/**
	 * 利率变动周期单位
	 */
	@TableField("CHG_CYCLE_UNIT")
	private Integer chgCycleUnit;
	/**
	 * 逾期利率计算方式
	 */
	@TableField("OVERDUE_RATE_CALC_MODE")
	private String overdueRateCalcMode;
	/**
	 * 逾期加点(或百分比)
	 */
	@TableField("OVERDUE_INCR_RATIO")
	private BigDecimal overdueIncrRatio;
	/**
	 * 挪用利率计算方式
	 */
	@TableField("EMBE_RATE_CALC_MODE")
	private String embeRateCalcMode;
	/**
	 * 挪用加点(或百分比)
	 */
	@TableField("EMBE_NCR_RATIO")
	private BigDecimal embeNcrRatio;
	/**
	 * 利率计算天数
	 */
	@TableField("CALC_DAYS")
	private Integer calcDays;
	/**
	 * 利息计收方式
	 */
	@TableField("CALC_WAY")
	private String calcWay;
	/**
	 * 首次还息日
	 */
	@TableField("FIRST_DAY")
	private Date firstDay;
	/**
	 * 还息计划类型
	 */
	@TableField("PLAN_TYPE")
	private String planType;
	/**
	 * 末次还款日
	 */
	@TableField("LAST_REPAY_DAY")
	private Date lastRepayDay;
	/**
	 * 是否首次放款
	 */
	@TableField("FIRST_LOAN_FLAG")
	private Integer firstLoanFlag;
	/**
	 * 是否末次放款
	 */
	@TableField("LAST_LOAN_FLAG")
	private Integer lastLoanFlag;
	/**
	 * 还本方式
	 */
	@TableField("REPAYMENT_MODE")
	private String repaymentMode;
	/**
	 * 首次还本日期
	 */
	@TableField("FRIST_REPAY_DATE")
	private Date fristRepayDate;
	/**
	 * 支付方式
	 */
	@TableField("PAYMENT_MODE")
	private String paymentMode;

	public String getObjTyp() {
		return objTyp;
	}

	public void setObjTyp(String objTyp) {
		this.objTyp = objTyp;
	}

	public Long getObjInr() {
		return objInr;
	}

	public void setObjInr(Long objInr) {
		this.objInr = objInr;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getConvertedPrice() {
		return convertedPrice;
	}

	public void setConvertedPrice(BigDecimal convertedPrice) {
		this.convertedPrice = convertedPrice;
	}

	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public Integer getIrSign() {
		return irSign;
	}

	public void setIrSign(Integer irSign) {
		this.irSign = irSign;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIrBk() {
		return irBk;
	}

	public void setIrBk(String irBk) {
		this.irBk = irBk;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getFloatDirectioin() {
		return floatDirectioin;
	}

	public void setFloatDirectioin(String floatDirectioin) {
		this.floatDirectioin = floatDirectioin;
	}

	public String getFloatMode() {
		return floatMode;
	}

	public void setFloatMode(String floatMode) {
		this.floatMode = floatMode;
	}

	public BigDecimal getSlidingScales() {
		return slidingScales;
	}

	public void setSlidingScales(BigDecimal slidingScales) {
		this.slidingScales = slidingScales;
	}

	public BigDecimal getFloatingRate() {
		return floatingRate;
	}

	public void setFloatingRate(BigDecimal floatingRate) {
		this.floatingRate = floatingRate;
	}

	public BigDecimal getRateVal() {
		return rateVal;
	}

	public void setRateVal(BigDecimal rateVal) {
		this.rateVal = rateVal;
	}

	public Integer getVarCycle() {
		return varCycle;
	}

	public void setVarCycle(Integer varCycle) {
		this.varCycle = varCycle;
	}

	public Integer getChgCycleUnit() {
		return chgCycleUnit;
	}

	public void setChgCycleUnit(Integer chgCycleUnit) {
		this.chgCycleUnit = chgCycleUnit;
	}

	public String getOverdueRateCalcMode() {
		return overdueRateCalcMode;
	}

	public void setOverdueRateCalcMode(String overdueRateCalcMode) {
		this.overdueRateCalcMode = overdueRateCalcMode;
	}

	public BigDecimal getOverdueIncrRatio() {
		return overdueIncrRatio;
	}

	public void setOverdueIncrRatio(BigDecimal overdueIncrRatio) {
		this.overdueIncrRatio = overdueIncrRatio;
	}

	public String getEmbeRateCalcMode() {
		return embeRateCalcMode;
	}

	public void setEmbeRateCalcMode(String embeRateCalcMode) {
		this.embeRateCalcMode = embeRateCalcMode;
	}

	public BigDecimal getEmbeNcrRatio() {
		return embeNcrRatio;
	}

	public void setEmbeNcrRatio(BigDecimal embeNcrRatio) {
		this.embeNcrRatio = embeNcrRatio;
	}

	public Integer getCalcDays() {
		return calcDays;
	}

	public void setCalcDays(Integer calcDays) {
		this.calcDays = calcDays;
	}

	public String getCalcWay() {
		return calcWay;
	}

	public void setCalcWay(String calcWay) {
		this.calcWay = calcWay;
	}

	public Date getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Date getLastRepayDay() {
		return lastRepayDay;
	}

	public void setLastRepayDay(Date lastRepayDay) {
		this.lastRepayDay = lastRepayDay;
	}

	public Integer getFirstLoanFlag() {
		return firstLoanFlag;
	}

	public void setFirstLoanFlag(Integer firstLoanFlag) {
		this.firstLoanFlag = firstLoanFlag;
	}

	public Integer getLastLoanFlag() {
		return lastLoanFlag;
	}

	public void setLastLoanFlag(Integer lastLoanFlag) {
		this.lastLoanFlag = lastLoanFlag;
	}

	public String getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

	public Date getFristRepayDate() {
		return fristRepayDate;
	}

	public void setFristRepayDate(Date fristRepayDate) {
		this.fristRepayDate = fristRepayDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BizFEC() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("objTyp", objTyp)
				.add("objInr", objInr)
				.add("rules", rules)
				.add("currency", currency)
				.add("convertedPrice", convertedPrice)
				.add("paymentAmt", paymentAmt)
				.add("irSign", irSign)
				.add("type", type)
				.add("irBk", irBk)
				.add("term", term)
				.add("floatDirectioin", floatDirectioin)
				.add("floatMode", floatMode)
				.add("slidingScales", slidingScales)
				.add("floatingRate", floatingRate)
				.add("rateVal", rateVal)
				.add("varCycle", varCycle)
				.add("chgCycleUnit", chgCycleUnit)
				.add("overdueRateCalcMode", overdueRateCalcMode)
				.add("overdueIncrRatio", overdueIncrRatio)
				.add("embeRateCalcMode", embeRateCalcMode)
				.add("embeNcrRatio", embeNcrRatio)
				.add("calcDays", calcDays)
				.add("calcWay", calcWay)
				.add("firstDay", firstDay)
				.add("planType", planType)
				.add("lastRepayDay", lastRepayDay)
				.add("firstLoanFlag", firstLoanFlag)
				.add("lastLoanFlag", lastLoanFlag)
				.add("repaymentMode", repaymentMode)
				.add("fristRepayDate", fristRepayDate)
				.add("paymentMode", paymentMode)
				.toString();
	}
}
