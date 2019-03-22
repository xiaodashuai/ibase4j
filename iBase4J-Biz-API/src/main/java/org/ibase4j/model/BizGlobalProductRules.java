package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("biz_global_product_rules")
@SuppressWarnings("serial")
public class BizGlobalProductRules extends BaseModel implements Serializable{

	@TableField("LTNOPA")//办理笔数限制
	private Long ltnopa;
	@TableField("TDWLN")//笔数
	private Long tdwln;
	@TableField("MPC")//主方案币种
	private String mpc;
	@TableField("AC")//辅助币种
	private String ac;
	@TableField("OCA")//其他可选币种
	private String oca;
	@TableField("SOLUTION_AMOUNT")//方案金额
	private Long solutionAmount;
	@TableField("DOPO")//方案业务期限范围
	private Long dopo;
	@TableField("SRT")//方案利率类型
	private Long str;
	@TableField("PACKAGE_RATES")//方案利率
	private Long packageRates;
	@TableField("PIRR")//方案利率范围
	private Long pirr;
	@TableField("EAAA")//审批机构
	private String eaaa;
	@TableField("PACKAGE_RATE")//方案费率
	private Long packageRate;
	@TableField("PRR")//方案费率范围
	private Long prr;
	@TableField("BTEAA")//是否经审批
	private Long bteaa;
	@TableField("DEBT_NUM")//债项编号
	private Long debtNum;

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

	public String getMpc() {
		return mpc;
	}

	public void setMpc(String mpc) {
		this.mpc = mpc;
	}

	public String getAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getOca() {
		return oca;
	}

	public void setOca(String oca) {
		this.oca = oca;
	}

	public Long getSolutionAmount() {
		return solutionAmount;
	}

	public void setSolutionAmount(Long solutionAmount) {
		this.solutionAmount = solutionAmount;
	}

	public Long getDopo() {
		return dopo;
	}

	public void setDopo(Long dopo) {
		this.dopo = dopo;
	}

	public Long getStr() {
		return str;
	}

	public void setStr(Long str) {
		this.str = str;
	}

	public Long getPackageRates() {
		return packageRates;
	}

	public void setPackageRates(Long packageRates) {
		this.packageRates = packageRates;
	}

	public Long getPirr() {
		return pirr;
	}

	public void setPirr(Long pirr) {
		this.pirr = pirr;
	}

	public String getEaaa() {
		return eaaa;
	}

	public void setEaaa(String eaaa) {
		this.eaaa = eaaa;
	}

	public Long getPackageRate() {
		return packageRate;
	}

	public void setPackageRate(Long packageRate) {
		this.packageRate = packageRate;
	}

	public Long getPrr() {
		return prr;
	}

	public void setPrr(Long prr) {
		this.prr = prr;
	}

	public Long getBteaa() {
		return bteaa;
	}

	public void setBteaa(Long bteaa) {
		this.bteaa = bteaa;
	}

	public Long getDebtNum() {
		return debtNum;
	}

	public void setDebtNum(Long debtNum) {
		this.debtNum = debtNum;
	}

	public BizGlobalProductRules() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("ltnopa", ltnopa)
				.add("tdwln", tdwln)
				.add("mpc", mpc)
				.add("ac", ac)
				.add("oca", oca)
				.add("solutionAmount", solutionAmount)
				.add("dopo", dopo)
				.add("str", str)
				.add("packageRates", packageRates)
				.add("pirr", pirr)
				.add("eaaa", eaaa)
				.add("packageRate", packageRate)
				.add("prr", prr)
				.add("bteaa", bteaa)
				.add("debtNum", debtNum)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizGlobalProductRules)) return false;
		BizGlobalProductRules that = (BizGlobalProductRules) o;
		return Objects.equal(getLtnopa(), that.getLtnopa()) &&
				Objects.equal(getTdwln(), that.getTdwln()) &&
				Objects.equal(getMpc(), that.getMpc()) &&
				Objects.equal(getAc(), that.getAc()) &&
				Objects.equal(getOca(), that.getOca()) &&
				Objects.equal(getSolutionAmount(), that.getSolutionAmount()) &&
				Objects.equal(getDopo(), that.getDopo()) &&
				Objects.equal(getStr(), that.getStr()) &&
				Objects.equal(getPackageRates(), that.getPackageRates()) &&
				Objects.equal(getPirr(), that.getPirr()) &&
				Objects.equal(getEaaa(), that.getEaaa()) &&
				Objects.equal(getPackageRate(), that.getPackageRate()) &&
				Objects.equal(getPrr(), that.getPrr()) &&
				Objects.equal(getBteaa(), that.getBteaa()) &&
				Objects.equal(getDebtNum(), that.getDebtNum());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getLtnopa(), getTdwln(), getMpc(), getAc(), getOca(), getSolutionAmount(), getDopo(), getStr(), getPackageRates(), getPirr(), getEaaa(), getPackageRate(), getPrr(), getBteaa(), getDebtNum());
	}
}
