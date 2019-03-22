package org.ibase4j.vo;



import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;
import java.util.List;

public class ProductBussinessVo implements java.io.Serializable {

	private String name;

	private String code;

	private String parentCode;

	private boolean children;
	/**
	 * 单一产品规则表id
	 */
	private String singleId;
	/**
	 * 债项概要编号
	 */
	private String debtNum;
	/**
	 * 产品生效日期
	 * */
	private Date ptEffectiveDate;
	/**
	 * 产品失效日期
	 * */
	private Date ptExpiDate;
	/**
	 * 产品循环标志
	 * */
	private Long ptLs;
	/**
	 * 方案生效日期
	 * */
	private Date pgEffectiveDate;
	/**
	 * 方案失效日期
	 * */
	private Date pgExpiDate;

	/**
	 * 授信额度信息外键
	 */
	private Long creditLinesId;
	/**
	 * 产品用信比例
	 */
	private String creditRatio;

	/**
	 * 产品币种
	 */
	private String productCurrency;
	/**
	 * 产品金额形式
	 */
	private Long aopForm;
	/**
	 * 产品金额
	 */
	private Long aop;
	/**
	 * 产品金额范围最低值
	 */
	private Long aopMiN;
	/**
	 * 产品金额范围最高值
	 */
	private Long aopMax;
	/**
	 * 可办理笔数限制
	 * */
	private Long ltnopa;
	/**
	 * 办理笔数
	 * */
	private Long tdwln;
	/**
	 * 产品业务期限范围
	 * */
	private Long psp;
	/**
	 * 产品利率类型
	 * */
	private Long prType;
	/**
	 * 产品利率形式
	 * */
	private Long interestRateForm;
	/**
	 * 产品利率
	 * */
	private Long tpoRate;
	/**
	 * 产品利率范围
	 * */
	private Long pirr;
	/**
	 * 产品费率
	 * */
	private Long tpRate;
	/**
	 * 产品费率类型
	 * */
	private Long prodRateType;
	/**
	 * 产品费率范围
	 * */
	private Long prRange;
	/**
	 * 是否经审批
	 * */
	private Long bteaa;
	/**
	 * 审批机构
	 * */
	private String eaaa;


	public String getProductCurrency() {
		return productCurrency;
	}

	public void setProductCurrency(String productCurrency) {
		this.productCurrency = productCurrency;
	}

	public Long getAopForm() {
		return aopForm;
	}

	public void setAopForm(Long aopForm) {
		this.aopForm = aopForm;
	}

	public Long getAop() {
		return aop;
	}

	public void setAop(Long aop) {
		this.aop = aop;
	}

	public Long getAopMiN() {
		return aopMiN;
	}

	public void setAopMiN(Long aopMiN) {
		this.aopMiN = aopMiN;
	}

	public Long getAopMax() {
		return aopMax;
	}

	public void setAopMax(Long aopMax) {
		this.aopMax = aopMax;
	}

	public Long getLtnopa() {
		return ltnopa;
	}

	public void setLtnopa(Long ltnopa) {
		this.ltnopa = ltnopa;
	}

	public Long getTdwln() {
		return tdwln;
	}

	public void setTdwln(Long tdwln) {
		this.tdwln = tdwln;
	}

	public Long getPsp() {
		return psp;
	}

	public void setPsp(Long psp) {
		this.psp = psp;
	}

	public Long getPrType() {
		return prType;
	}

	public void setPrType(Long prType) {
		this.prType = prType;
	}

	public Long getInterestRateForm() {
		return interestRateForm;
	}

	public void setInterestRateForm(Long interestRateForm) {
		this.interestRateForm = interestRateForm;
	}

	public Long getTpoRate() {
		return tpoRate;
	}

	public void setTpoRate(Long tpoRate) {
		this.tpoRate = tpoRate;
	}

	public Long getPirr() {
		return pirr;
	}

	public void setPirr(Long pirr) {
		this.pirr = pirr;
	}

	public Long getTpRate() {
		return tpRate;
	}

	public void setTpRate(Long tpRate) {
		this.tpRate = tpRate;
	}

	public Long getProdRateType() {
		return prodRateType;
	}

	public void setProdRateType(Long prodRateType) {
		this.prodRateType = prodRateType;
	}

	public Long getPrRange() {
		return prRange;
	}

	public void setPrRange(Long prRange) {
		this.prRange = prRange;
	}

	public Long getBteaa() {
		return bteaa;
	}

	public void setBteaa(Long bteaa) {
		this.bteaa = bteaa;
	}

	public String getEaaa() {
		return eaaa;
	}

	public void setEaaa(String eaaa) {
		this.eaaa = eaaa;
	}

	public String getSingleId() {
		return singleId;
	}

	public void setSingleId(String singleId) {
		this.singleId = singleId;
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

	public String getDebtNum() {
		return debtNum;
	}

	public void setDebtNum(String debtNum) {
		this.debtNum = debtNum;
	}

	public Date getPtEffectiveDate() {
		return ptEffectiveDate;
	}

	public void setPtEffectiveDate(Date ptEffectiveDate) {
		this.ptEffectiveDate = ptEffectiveDate;
	}

	public Date getPtExpiDate() {
		return ptExpiDate;
	}

	public void setPtExpiDate(Date ptExpiDate) {
		this.ptExpiDate = ptExpiDate;
	}

	public Long getPtLs() {
		return ptLs;
	}

	public void setPtLs(Long ptLs) {
		this.ptLs = ptLs;
	}

	public Date getPgEffectiveDate() {
		return pgEffectiveDate;
	}

	public void setPgEffectiveDate(Date pgEffectiveDate) {
		this.pgEffectiveDate = pgEffectiveDate;
	}

	public Date getPgExpiDate() {
		return pgExpiDate;
	}

	public void setPgExpiDate(Date pgExpiDate) {
		this.pgExpiDate = pgExpiDate;
	}

	private List<ProductBussinessVo> pbList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<ProductBussinessVo> getPbList() {
		return pbList;
	}

	public void setPbList(List<ProductBussinessVo> pbList) {
		this.pbList = pbList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProductBussinessVo)) return false;
		ProductBussinessVo that = (ProductBussinessVo) o;
		return isChildren() == that.isChildren() &&
				Objects.equal(getName(), that.getName()) &&
				Objects.equal(getCode(), that.getCode()) &&
				Objects.equal(getParentCode(), that.getParentCode()) &&
				Objects.equal(getSingleId(), that.getSingleId()) &&
				Objects.equal(getDebtNum(), that.getDebtNum()) &&
				Objects.equal(getPtEffectiveDate(), that.getPtEffectiveDate()) &&
				Objects.equal(getPtExpiDate(), that.getPtExpiDate()) &&
				Objects.equal(getPtLs(), that.getPtLs()) &&
				Objects.equal(getPgEffectiveDate(), that.getPgEffectiveDate()) &&
				Objects.equal(getPgExpiDate(), that.getPgExpiDate()) &&
				Objects.equal(getCreditLinesId(), that.getCreditLinesId()) &&
				Objects.equal(getCreditRatio(), that.getCreditRatio()) &&
				Objects.equal(getProductCurrency(), that.getProductCurrency()) &&
				Objects.equal(getAopForm(), that.getAopForm()) &&
				Objects.equal(getAop(), that.getAop()) &&
				Objects.equal(getAopMiN(), that.getAopMiN()) &&
				Objects.equal(getAopMax(), that.getAopMax()) &&
				Objects.equal(getLtnopa(), that.getLtnopa()) &&
				Objects.equal(getTdwln(), that.getTdwln()) &&
				Objects.equal(getPsp(), that.getPsp()) &&
				Objects.equal(getPrType(), that.getPrType()) &&
				Objects.equal(getInterestRateForm(), that.getInterestRateForm()) &&
				Objects.equal(getTpoRate(), that.getTpoRate()) &&
				Objects.equal(getPirr(), that.getPirr()) &&
				Objects.equal(getTpRate(), that.getTpRate()) &&
				Objects.equal(getProdRateType(), that.getProdRateType()) &&
				Objects.equal(getPrRange(), that.getPrRange()) &&
				Objects.equal(getBteaa(), that.getBteaa()) &&
				Objects.equal(getEaaa(), that.getEaaa()) &&
				Objects.equal(getPbList(), that.getPbList());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), getCode(), getParentCode(), isChildren(), getSingleId(), getDebtNum(), getPtEffectiveDate(), getPtExpiDate(), getPtLs(), getPgEffectiveDate(), getPgExpiDate(), getCreditLinesId(), getCreditRatio(), getProductCurrency(), getAopForm(), getAop(), getAopMiN(), getAopMax(), getLtnopa(), getTdwln(), getPsp(), getPrType(), getInterestRateForm(), getTpoRate(), getPirr(), getTpRate(), getProdRateType(), getPrRange(), getBteaa(), getEaaa(), getPbList());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("code", code)
				.add("parentCode", parentCode)
				.add("children", children)
				.add("singleId", singleId)
				.add("debtNum", debtNum)
				.add("ptEffectiveDate", ptEffectiveDate)
				.add("ptExpiDate", ptExpiDate)
				.add("ptLs", ptLs)
				.add("pgEffectiveDate", pgEffectiveDate)
				.add("pgExpiDate", pgExpiDate)
				.add("creditLinesId", creditLinesId)
				.add("creditRatio", creditRatio)
				.add("productCurrency", productCurrency)
				.add("aopForm", aopForm)
				.add("aop", aop)
				.add("aopMiN", aopMiN)
				.add("aopMax", aopMax)
				.add("ltnopa", ltnopa)
				.add("tdwln", tdwln)
				.add("psp", psp)
				.add("prType", prType)
				.add("interestRateForm", interestRateForm)
				.add("tpoRate", tpoRate)
				.add("pirr", pirr)
				.add("tpRate", tpRate)
				.add("prodRateType", prodRateType)
				.add("prRange", prRange)
				.add("bteaa", bteaa)
				.add("eaaa", eaaa)
				.add("pbList", pbList)
				.toString();
	}
}
