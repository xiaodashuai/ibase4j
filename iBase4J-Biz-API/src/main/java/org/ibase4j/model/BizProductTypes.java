/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：产品种类（品种）<br/>
 * 时间：2018-04-02
 * 
 * @author czm
 * @version 1.0
 */
@TableName("BIZ_PRODUCT_TYPES")
public class BizProductTypes extends BaseModel implements Serializable{
	@TableField("NAME_") // 品种名称
	private String name;
	@TableField("CODE_") // 品种编码
	private String code;
	@TableField("PARENT_CODE") // 父编码
	private String parentCode;
	@TableField("LEAF_") // 层级
	private Integer leaf;
	@TableField("IS_CHILD") // 存在子节点(1是；0否)
	private Integer isChild;

	@TableField(exist = false) // 绑定客户信息
	private List<BizCust> customersList;

	@TableField(exist = false) // 债项方案id
	private String debtCode;
	@TableField(exist = false) // 长名字
	private String names;
	@TableField(exist = false) // 用于用信页面的勾选
	private Boolean checked;

	/**
	 * 存储专有信息页面的承租人
	 */
	@TableField(exist = false)
	private String custNo;
	/**
	 * 容忍期，用于信息页面的的存取值
	 */
	@TableField(exist = false)
	private Long tolerancePertod;

	/**
	 * 行业投向，用于专有信息的存取值
	 */
	@TableField(exist = false)
	private String industryTo;
	/**
	 * 承租人是否是地方性融资平台，用于专有信息的存取值
	 */
	@TableField(exist = false)
	private String financePlatform;
	/**
	 * 背景国别，用于风险分析的存取值
	 */
	@TableField(exist = false)
	private String tbon;

	/**
	 * 保存单一规则表的id
	 */
	@TableField(exist = false)
	private String singleId;
	/**
	 * 保存租金保理表的id
	 */
	@TableField(exist = false)
	private String theRentFactorId;

	/**
	 * 用于租金保理表的客户名称
	 */
	@TableField(exist = false)
	private String custName;

	/**
	 * 用于租金保理表的客户评级
	 */
	@TableField(exist = false)
	private String custTating;

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustTating() {
		return custTating;
	}

	public void setCustTating(String custTating) {
		this.custTating = custTating;
	}

	public String getFinancePlatform() {
		return financePlatform;
	}

	public void setFinancePlatform(String financePlatform) {
		this.financePlatform = financePlatform;
	}

	public String getTheRentFactorId() {
		return theRentFactorId;
	}

	public void setTheRentFactorId(String theRentFactorId) {
		this.theRentFactorId = theRentFactorId;
	}

	public Long getTolerancePertod() {
		return tolerancePertod;
	}

	public String getSingleId() {
		return singleId;
	}

	public void setSingleId(String singleId) {
		this.singleId = singleId;
	}

	public void setTolerancePertod(Long tolerancePertod) {
		this.tolerancePertod = tolerancePertod;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public List<BizCust> getCustomersList() {
		return customersList;
	}

	public void setCustomersList(List<BizCust> customersList) {
		this.customersList = customersList;
	}

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

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	
	

	public Integer getIsChild() {
		return isChild;
	}

	public void setIsChild(Integer isChild) {
		this.isChild = isChild;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public BizProductTypes() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("code", code)
				.add("parentCode", parentCode)
				.add("leaf", leaf)
				.add("isChild", isChild)
				.add("customersList", customersList)
				.add("debtCode", debtCode)
				.add("names", names)
				.add("checked", checked)
				.add("custNo", custNo)
				.add("tolerancePertod", tolerancePertod)
				.add("industryTo", industryTo)
				.add("financePlatform", financePlatform)
				.add("tbon", tbon)
				.add("singleId", singleId)
				.add("theRentFactorId", theRentFactorId)
				.add("custName", custName)
				.add("custTating", custTating)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizProductTypes)) return false;
		BizProductTypes that = (BizProductTypes) o;
		return Objects.equal(getName(), that.getName()) &&
				Objects.equal(getCode(), that.getCode()) &&
				Objects.equal(getParentCode(), that.getParentCode()) &&
				Objects.equal(getLeaf(), that.getLeaf()) &&
				Objects.equal(getIsChild(), that.getIsChild()) &&
				Objects.equal(getCustomersList(), that.getCustomersList()) &&
				Objects.equal(getDebtCode(), that.getDebtCode()) &&
				Objects.equal(getNames(), that.getNames()) &&
				Objects.equal(getChecked(), that.getChecked()) &&
				Objects.equal(getCustNo(), that.getCustNo()) &&
				Objects.equal(getTolerancePertod(), that.getTolerancePertod()) &&
				Objects.equal(getIndustryTo(), that.getIndustryTo()) &&
				Objects.equal(getFinancePlatform(), that.getFinancePlatform()) &&
				Objects.equal(getTbon(), that.getTbon()) &&
				Objects.equal(getSingleId(), that.getSingleId()) &&
				Objects.equal(getTheRentFactorId(), that.getTheRentFactorId()) &&
				Objects.equal(getCustName(), that.getCustName()) &&
				Objects.equal(getCustTating(), that.getCustTating());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), getCode(), getParentCode(), getLeaf(), getIsChild(), getCustomersList(), getDebtCode(), getNames(), getChecked(), getCustNo(), getTolerancePertod(), getIndustryTo(), getFinancePlatform(), getTbon(), getSingleId(), getTheRentFactorId(), getCustName(), getCustTating());
	}
}
