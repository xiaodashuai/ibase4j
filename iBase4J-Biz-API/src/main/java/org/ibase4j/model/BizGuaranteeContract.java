/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;
import org.ibase4j.core.util.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能：担保合同表 日期：2018/8/16
 * 
 * @author lwh
 * @version 1.0
 */
@TableName("BIZ_GUARANTEE_CONTRACT")
@SuppressWarnings("serial")
public class BizGuaranteeContract extends BaseModel implements Serializable {

	/**
	 * 方案编码
	 */
	@TableField("DEBT_CODE")
	private String debtCode;

	/**
	 * 发放编码
	 */
	@TableField("GRANT_CODE")
	private String grantCode;

	/**
	 * 合同名称
	 */
	@TableField("NAME_")
	private String name;
	/**
	 * 担保合同编号
	 */
	@TableField("GUAR_NO")
	private String guarNo;
	/**
	 * 担保合同手工编号
	 */
	@TableField("GUAR_MANUAL_NO")
	private String guarManualNo;
	/**
	 * 担保人客户编号
	 */
	@TableField("GUAR_CUST_NO")
	private String guarCustNo;
	/**
	 * 担保人客户名称
	 */
	@TableField("GUAR_CUST_NAME")
	private String guarCustName;
	/**
	 *担保合同金额
	 */
	@TableField("GUAR_AMOUNT")
	private BigDecimal guarAmount;
	/**
	 * 币种
	 */
	@TableField("CURRENCY")
	private String currency;
	/**
	 * 开始日期
	 */
	@TableField("START_DATE")
	private Date startDate;
	/**
	 * 到期日期
	 */
	@TableField("DUE_DATE")
	private Date dueDate;
	/**
	 * 期限
	 */
	@TableField("TERM")
	private Long term;
	/**
	 * 期限单位
	 */
	@TableField("TERM_UNIT")
	private String termUnit;
	/**
	 * 保证担保类型
	 */
	@TableField("WARRANDICE")
	private String warrandice;
	/**
	 * 担保合同类型
	 */
	@TableField("GUAR_CONT_TYPE")
	private String guarContType;
	/**
	 * 担保合同状态
	 */
	@TableField("GUAR_CONT_STATE")
	private String guarContState;
	/**
	 * 是否最高额担保合同
	 */
	@TableField("GUAR_MAX_AMT")
	private Integer guarMaxAmt;
	/**
	 * 经办人
	 */
	@TableField("AGENT")
	private String agent;
	/**
	 * 经办机构
	 */
	@TableField("AGENCIES")
	private String agencies;
	/**
	 * 展示押品信息
	 */
	@TableField(exist = false)
	private List<BizBetInformation> bizBetInformationList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuarNo() {
		return guarNo;
	}

	public void setGuarNo(String guarNo) {
		this.guarNo = guarNo;
	}

	public String getGuarManualNo() {
		return guarManualNo;
	}

	public void setGuarManualNo(String guarManualNo) {
		this.guarManualNo = guarManualNo;
	}

	public String getGuarCustNo() {
		return guarCustNo;
	}

	public void setGuarCustNo(String guarCustNo) {
		this.guarCustNo = guarCustNo;
	}

	public String getGuarCustName() {
		return guarCustName;
	}

	public void setGuarCustName(String guarCustName) {
		this.guarCustName = guarCustName;
	}

	public BigDecimal getGuarAmount() {
		return guarAmount;
	}

	public void setGuarAmount(BigDecimal guarAmount) {
		this.guarAmount = guarAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public String getStartDateStr() {
		if(startDate!=null){
			return DateUtil.format(startDate);
		}
		return "";
	}
	
	public String getDueDateStr() {
		if(dueDate!=null){
			return DateUtil.format(dueDate);
		}
		return "";
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}

	public String getWarrandice() {
		return warrandice;
	}

	public void setWarrandice(String warrandice) {
		this.warrandice = warrandice;
	}

	public String getGuarContType() {
		return guarContType;
	}

	public void setGuarContType(String guarContType) {
		this.guarContType = guarContType;
	}

	public String getGuarContState() {
		return guarContState;
	}

	public void setGuarContState(String guarContState) {
		this.guarContState = guarContState;
	}

	public Integer getGuarMaxAmt() {
		return guarMaxAmt;
	}

	public void setGuarMaxAmt(Integer guarMaxAmt) {
		this.guarMaxAmt = guarMaxAmt;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgencies() {
		return agencies;
	}

	public void setAgencies(String agencies) {
		this.agencies = agencies;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getGrantCode() {
		return grantCode;
	}

	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}

	public List<BizBetInformation> getBizBetInformationList() {
		return bizBetInformationList;
	}

	public void setBizBetInformationList(List<BizBetInformation> bizBetInformationList) {
		this.bizBetInformationList = bizBetInformationList;
	}

	public BizGuaranteeContract() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("grantCode", grantCode)
				.add("name", name)
				.add("guarNo", guarNo)
				.add("guarManualNo", guarManualNo)
				.add("guarCustNo", guarCustNo)
				.add("guarCustName", guarCustName)
				.add("guarAmount", guarAmount)
				.add("currency", currency)
				.add("startDate", startDate)
				.add("dueDate", dueDate)
				.add("term", term)
				.add("termUnit", termUnit)
				.add("warrandice", warrandice)
				.add("guarContType", guarContType)
				.add("guarContState", guarContState)
				.add("guarMaxAmt", guarMaxAmt)
				.add("agent", agent)
				.add("agencies", agencies)
				.add("bizBetInformationList", bizBetInformationList)
				.toString();
	}
}
