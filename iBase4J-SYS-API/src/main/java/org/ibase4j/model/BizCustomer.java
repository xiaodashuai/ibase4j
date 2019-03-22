package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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

@TableName("BIZ_CUSTOMER")
@SuppressWarnings("serial")
public class BizCustomer extends BaseModel implements Serializable {

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
	 * 组织机构代码
	 */
	@TableField("ORGANIZATION_CODE")
	private Long organizationCode;
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
	 * 客户登记注册类型(码表)
	 */
	@TableField("CUST_REG_TYPE")
	private String custRegistrastionType;
	/**
	 * 客户评级授信类型(码表)
	 */
	@TableField("CUST_RCREDIT_TYPE")
	private String custRatingCreditType;
	/**
	 * 客户规模
	 */
	@TableField("CUST_SCALE")
	private String custScale;
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
	 * 客户所属国别
	 */
	@TableField("CUST_COUNTRY")
	private String custCountry;
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
	 * 客户经理
	 */
	@TableField("CUST_MANAGER")
	private String custManager;

    /**
     * 用于用信主体中存取值,存储额度信息
     */
	@TableField(exist = false)
	private List<BizCreditLines> creditLinesList;

	/**
	 * 用来保存额度类型表的id
	 */
	@TableField(exist = false)
	private String productLinesTypeId;
	/**
	 * 授信额度信息外键,用于用信主体的存取值
	 */
	@TableField(exist = false)
	private String creditLinesId;

	/**
	 * 产品用信比例，用于用信主体的存取值
	 */
	@TableField(exist = false)
	private String creditRatio;

	public String getProductLinesTypeId() {
		return productLinesTypeId;
	}

	public void setProductLinesTypeId(String productLinesTypeId) {
		this.productLinesTypeId = productLinesTypeId;
	}

	public String getCreditLinesId() {
		return creditLinesId;
	}

	public void setCreditLinesId(String creditLinesId) {
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

	public Long getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(Long organizationCode) {
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

	@Override
	public String toString() {
		return "BizCustomer{" +
				"debtCode='" + debtCode + '\'' +
				", custNo='" + custNo + '\'' +
				", organizationCode=" + organizationCode +
				", custNameCN='" + custNameCN + '\'' +
				", custNameEN='" + custNameEN + '\'' +
				", custAddressCN='" + custAddressCN + '\'' +
				", custAddressEN='" + custAddressEN + '\'' +
				", custBusinessUnit='" + custBusinessUnit + '\'' +
				", custRegistrastionType='" + custRegistrastionType + '\'' +
				", custRatingCreditType='" + custRatingCreditType + '\'' +
				", custScale='" + custScale + '\'' +
				", groupNumber='" + groupNumber + '\'' +
				", groupName='" + groupName + '\'' +
				", mainBusiness='" + mainBusiness + '\'' +
				", custCountry='" + custCountry + '\'' +
				", creditRating='" + creditRating + '\'' +
				", ratiTime=" + ratiTime +
				", custManager='" + custManager + '\'' +
				", creditLinesList=" + creditLinesList +
				", creditLinesId=" + creditLinesId +
				", creditRatio='" + creditRatio + '\'' +
				'}';
	}
}
