package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 描述：客户主体信息
 * 
 * @author xiaoshuiquan
 * @date 2018/05/26
 */

@TableName("BIZ_CUST")
@SuppressWarnings("serial")
public class BizCust extends BaseModel implements Serializable {

	/**
	 * 债项方案id
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 客户编号(核心系统用户编码)
	 */
	@TableField("CUST_NO")
	private String custNo;
	/**
	 * 客户证件类型(前台不展示)
	 */
	@TableField("CUST_ID_TYPE")
	private String custIdType;
	/**
	 * 组织机构代码
	 */
	@TableField("ORGANIZATION_CODE")
	private String organizationCode;

	/**
	 * 客户名称（中文）
	 */
	@TableField("CUST_NAME_CN")
	private String custNameCN;
	/**
	 * 客户名称（英文）
	 */
	@TableField("CUST_NAME_EN")
	private String custNameEN;
	/**
	 * 客户所在地址（中文）
	 */
	@TableField("CUST_ADDRESS_CN")
	private String custAddressCN;
	/**
	 * 客户所在地址（英文）
	 */
	@TableField("CUST_ADDRESS_EN")
	private String custAddressEN;
	/**
	 * 客户属辖经营单位
	 */
	@TableField("CUST_BUSINESS_UNIT")
	private String custBusinessUnit;
	/**
	 * 客户属辖经营单位的名称
	 */
	@TableField(exist = false)
	private String custBusinessUnitName;
	/**
	 * 客户登记注册类型(码表)
	 */
	@TableField("CUST_REG_TYPE")
	private String custRegistrastionType;
	/**
	 * 客户登记注册类型的名称
	 */
	@TableField(exist = false)
	private String custRegistrastionTypeName;
	/**
	 * 客户评级授信类型(码表)
	 */
	@TableField("CUST_RCREDIT_TYPE")
	private String custRatingCreditType;
	/**
	 * 客户评级授信类型名称
	 */
	@TableField(exist = false)
	private String custRatingCreditTypeName;
	/**
	 * 客户规模
	 */
	@TableField("CUST_SCALE")
	private String custScale;
	/**
	 * 客户规模名称
	 */
	@TableField(exist = false)
	private String custScaleName;
	/**
	 * 所属集团编号
	 */
	@TableField("GROUP_NO")
	private String groupNumber;
	/**
	 * 所属集团名称
	 */
	@TableField("GROUP_NAME")
	private String groupName;
	/**
	 * 客户所属行业
	 */
	@TableField("MAIN_BUSINESS")
	private String mainBusiness;
	/**
	 * 客户所属行业名称
	 */
	@TableField(exist = false)
	private String mainBusinessName;
		/**
	 * 客户所属国别
	 */
	@TableField("CUST_COUNTRY")
	private String custCountry;
	/**
	 * 客户所属国别名称
	 */
	@TableField(exist = false)
	private String custCountryName;
	/**
	 * 信用评级
	 */
	@TableField("CREDIT_RATING")
	private String creditRating;
	/**
	 * 评级时间
	 */
	@TableField("RATING_TIME")
	private Date ratiTime;
	/**
	 * 评级开始时间
	 */
	@TableField("RATING_BEGIN_TIME")
	private Date ratiBeginTime;
	/**
	 * 评级到期时间
	 */
	@TableField("RATING_END_TIME")
	private Date ratiEndTime;
	/**
	 * 客户经理
	 */
	@TableField("CUST_MANAGER")
	private String custManager;

    /**
     * 用于用信主体中存取值
     */
	@TableField(exist = false)
	private List<BizCreditLines> creditLinesList;

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

	public List<BizCreditLines> getCreditLinesList() {
		return creditLinesList;
	}

	public void setCreditLinesList(List<BizCreditLines> creditLinesList) {
		this.creditLinesList = creditLinesList;
	}

	public String getCustAddressEN() {
		return custAddressEN;
	}

	public void setCustAddressEN(String custAddressEN) {
		this.custAddressEN = custAddressEN;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustNameCN() {
		return custNameCN;
	}

	public void setCustNameCN(String custNameCN) {
		this.custNameCN = custNameCN;
	}

	public String getCustNameEN() {
		return custNameEN;
	}

	public void setCustNameEN(String custNameEN) {
		this.custNameEN = custNameEN;
	}

	public String getCustAddressCN() {
		return custAddressCN;
	}

	public void setCustAddressCN(String custAddressCN) {
		this.custAddressCN = custAddressCN;
	}

	public String getCustBusinessUnit() {
		return custBusinessUnit;
	}

	public void setCustBusinessUnit(String custBusinessUnit) {
		this.custBusinessUnit = custBusinessUnit;
	}

	public String getCustRegistrastionType() {
		return custRegistrastionType;
	}

	public void setCustRegistrastionType(String custRegistrastionType) {
		this.custRegistrastionType = custRegistrastionType;
	}

	public String getCustBusinessUnitName() {
		return custBusinessUnitName;
	}

	public void setCustBusinessUnitName(String custBusinessUnitName) {
		this.custBusinessUnitName = custBusinessUnitName;
	}

	public String getCustRegistrastionTypeName() {
		return custRegistrastionTypeName;
	}

	public void setCustRegistrastionTypeName(String custRegistrastionTypeName) {
		this.custRegistrastionTypeName = custRegistrastionTypeName;
	}

	public String getCustRatingCreditTypeName() {
		return custRatingCreditTypeName;
	}

	public void setCustRatingCreditTypeName(String custRatingCreditTypeName) {
		this.custRatingCreditTypeName = custRatingCreditTypeName;
	}

	public String getCustScaleName() {
		return custScaleName;
	}

	public void setCustScaleName(String custScaleName) {
		this.custScaleName = custScaleName;
	}

	public String getMainBusinessName() {
		return mainBusinessName;
	}

	public void setMainBusinessName(String mainBusinessName) {
		this.mainBusinessName = mainBusinessName;
	}

	public String getCustCountryName() {
		return custCountryName;
	}

	public void setCustCountryName(String custCountryName) {
		this.custCountryName = custCountryName;
	}

	public String getCustRatingCreditType() {
		return custRatingCreditType;
	}

	public void setCustRatingCreditType(String custRatingCreditType) {
		this.custRatingCreditType = custRatingCreditType;
	}

	public String getCustCountry() {
		return custCountry;
	}

	public void setCustCountry(String custCountry) {
		this.custCountry = custCountry;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getCustScale() {
		return custScale;
	}

	public void setCustScale(String custScale) {
		this.custScale = custScale;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCustManager() {
		return custManager;
	}

	public void setCustManager(String custManager) {
		this.custManager = custManager;
	}

	public Date getRatiTime() {
		return ratiTime;
	}

	public void setRatiTime(Date ratiTime) {
		this.ratiTime = ratiTime;
	}

	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String getCustIdType() {
		return custIdType;
	}

	public void setCustIdType(String custIdType) {
		this.custIdType = custIdType;
	}

	public Date getRatiBeginTime() {
		return ratiBeginTime;
	}

	public void setRatiBeginTime(Date ratiBeginTime) {
		this.ratiBeginTime = ratiBeginTime;
	}

	public Date getRatiEndTime() {
		return ratiEndTime;
	}

	public void setRatiEndTime(Date ratiEndTime) {
		this.ratiEndTime = ratiEndTime;
	}

    public BizCust() {
    }

    @Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("custNo", custNo)
				.add("custIdType", custIdType)
				.add("organizationCode", organizationCode)
				.add("custNameCN", custNameCN)
				.add("custNameEN", custNameEN)
				.add("custAddressCN", custAddressCN)
				.add("custAddressEN", custAddressEN)
				.add("custBusinessUnit", custBusinessUnit)
				.add("custBusinessUnitName", custBusinessUnitName)
				.add("custRegistrastionType", custRegistrastionType)
				.add("custRegistrastionTypeName", custRegistrastionTypeName)
				.add("custRatingCreditType", custRatingCreditType)
				.add("custRatingCreditTypeName", custRatingCreditTypeName)
				.add("custScale", custScale)
				.add("custScaleName", custScaleName)
				.add("groupNumber", groupNumber)
				.add("groupName", groupName)
				.add("mainBusiness", mainBusiness)
				.add("mainBusinessName", mainBusinessName)
				.add("custCountry", custCountry)
				.add("custCountryName", custCountryName)
				.add("creditRating", creditRating)
				.add("ratiTime", ratiTime)
				.add("ratiBeginTime", ratiBeginTime)
				.add("ratiEndTime", ratiEndTime)
				.add("custManager", custManager)
				.add("creditLinesList", creditLinesList)
				.add("creditLinesId", creditLinesId)
				.add("creditRatio", creditRatio)
				.toString();
	}
}
