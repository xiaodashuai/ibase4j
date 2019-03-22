package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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
    private String creditLinesId;
    /**
     * 产品用信比例，用于用信主体的存取值
     */
    @TableField(exist = false)
    private String creditRatio;
    /**
     * 用来保存额度类型表的id
     */
    @TableField(exist = false)
    private String productLinesTypeId;

    public String getDebtCode() {
        return debtCode == "" ? null : debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public String getCustNo() {
        return custNo == "" ? null : custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getCustIdType() {
        return custIdType == "" ? null : custIdType;
    }

    public void setCustIdType(String custIdType) {
        this.custIdType = custIdType;
    }

    public String getOrganizationCode() {
        return organizationCode == "" ? null : organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCustNameCN() {
        return custNameCN == "" ? null : custNameCN;
    }

    public void setCustNameCN(String custNameCN) {
        this.custNameCN = custNameCN;
    }

    public String getCustNameEN() {
        return custNameEN == "" ? null : custNameEN;
    }

    public void setCustNameEN(String custNameEN) {
        this.custNameEN = custNameEN;
    }

    public String getCustAddressCN() {
        return custAddressCN == "" ? null : custAddressCN;
    }

    public void setCustAddressCN(String custAddressCN) {
        this.custAddressCN = custAddressCN;
    }

    public String getCustAddressEN() {
        return custAddressEN == "" ? null : custAddressEN;
    }

    public void setCustAddressEN(String custAddressEN) {
        this.custAddressEN = custAddressEN;
    }

    public String getCustBusinessUnit() {
        return custBusinessUnit == "" ? null : custBusinessUnit;
    }

    public void setCustBusinessUnit(String custBusinessUnit) {
        this.custBusinessUnit = custBusinessUnit;
    }

    public String getCustBusinessUnitName() {
        return custBusinessUnitName == "" ? null : custBusinessUnitName;
    }

    public void setCustBusinessUnitName(String custBusinessUnitName) {
        this.custBusinessUnitName = custBusinessUnitName;
    }

    public String getCustRegistrastionType() {
        return custRegistrastionType == "" ? null : custRegistrastionType;
    }

    public void setCustRegistrastionType(String custRegistrastionType) {
        this.custRegistrastionType = custRegistrastionType;
    }

    public String getCustRegistrastionTypeName() {
        return custRegistrastionTypeName == "" ? null : custRegistrastionTypeName;
    }

    public void setCustRegistrastionTypeName(String custRegistrastionTypeName) {
        this.custRegistrastionTypeName = custRegistrastionTypeName;
    }

    public String getCustRatingCreditType() {
        return custRatingCreditType == "" ? null : custRatingCreditType;
    }

    public void setCustRatingCreditType(String custRatingCreditType) {
        this.custRatingCreditType = custRatingCreditType;
    }

    public String getCustRatingCreditTypeName() {
        return custRatingCreditTypeName == "" ? null : custRatingCreditTypeName;
    }

    public void setCustRatingCreditTypeName(String custRatingCreditTypeName) {
        this.custRatingCreditTypeName = custRatingCreditTypeName;
    }

    public String getCustScale() {
        return custScale == "" ? null : custScale;
    }

    public void setCustScale(String custScale) {
        this.custScale = custScale;
    }

    public String getCustScaleName() {
        return custScaleName == "" ? null : custScaleName;
    }

    public void setCustScaleName(String custScaleName) {
        this.custScaleName = custScaleName;
    }

    public String getGroupNumber() {
        return groupNumber == "" ? null : groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGroupName() {
        return groupName == "" ? null : groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMainBusiness() {
        return mainBusiness == "" ? null : mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getMainBusinessName() {
        return mainBusinessName == "" ? null : mainBusinessName;
    }

    public void setMainBusinessName(String mainBusinessName) {
        this.mainBusinessName = mainBusinessName;
    }

    public String getCustCountry() {
        return custCountry == "" ? null : custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustCountryName() {
        return custCountryName == "" ? null : custCountryName;
    }

    public void setCustCountryName(String custCountryName) {
        this.custCountryName = custCountryName;
    }

    public String getCreditRating() {
        return creditRating == "" ? null : creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public Date getRatiTime() {
        return ratiTime;
    }

    public void setRatiTime(Date ratiTime) {
        this.ratiTime = ratiTime;
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

    public String getCustManager() {
        return custManager == "" ? null : custManager;
    }

    public void setCustManager(String custManager) {
        this.custManager = custManager;
    }

    public List<BizCreditLines> getCreditLinesList() {
        return creditLinesList;
    }

    public void setCreditLinesList(List<BizCreditLines> creditLinesList) {
        this.creditLinesList = creditLinesList;
    }

    public String getCreditLinesId() {
        return creditLinesId == "" ? null : creditLinesId;
    }

    public void setCreditLinesId(String creditLinesId) {
        this.creditLinesId = creditLinesId;
    }

    public String getCreditRatio() {
        return creditRatio == "" ? null : creditRatio;
    }

    public void setCreditRatio(String creditRatio) {
        this.creditRatio = creditRatio;
    }

    public String getProductLinesTypeId() {
        return productLinesTypeId == "" ? null : productLinesTypeId;
    }

    public void setProductLinesTypeId(String productLinesTypeId) {
        this.productLinesTypeId = productLinesTypeId;
    }

    public BizCust() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizCust)) return false;
        BizCust bizCust = (BizCust) o;
        return Objects.equal(getDebtCode(), bizCust.getDebtCode()) &&
                Objects.equal(getCustNo(), bizCust.getCustNo()) &&
                Objects.equal(getCustIdType(), bizCust.getCustIdType()) &&
                Objects.equal(getOrganizationCode(), bizCust.getOrganizationCode()) &&
                Objects.equal(getCustNameCN(), bizCust.getCustNameCN()) &&
                Objects.equal(getCustNameEN(), bizCust.getCustNameEN()) &&
                Objects.equal(getCustAddressCN(), bizCust.getCustAddressCN()) &&
                Objects.equal(getCustAddressEN(), bizCust.getCustAddressEN()) &&
                Objects.equal(getCustBusinessUnit(), bizCust.getCustBusinessUnit()) &&
                Objects.equal(getCustBusinessUnitName(), bizCust.getCustBusinessUnitName()) &&
                Objects.equal(getCustRegistrastionType(), bizCust.getCustRegistrastionType()) &&
                Objects.equal(getCustRegistrastionTypeName(), bizCust.getCustRegistrastionTypeName()) &&
                Objects.equal(getCustRatingCreditType(), bizCust.getCustRatingCreditType()) &&
                Objects.equal(getCustRatingCreditTypeName(), bizCust.getCustRatingCreditTypeName()) &&
                Objects.equal(getCustScale(), bizCust.getCustScale()) &&
                Objects.equal(getCustScaleName(), bizCust.getCustScaleName()) &&
                Objects.equal(getGroupNumber(), bizCust.getGroupNumber()) &&
                Objects.equal(getGroupName(), bizCust.getGroupName()) &&
                Objects.equal(getMainBusiness(), bizCust.getMainBusiness()) &&
                Objects.equal(getMainBusinessName(), bizCust.getMainBusinessName()) &&
                Objects.equal(getCustCountry(), bizCust.getCustCountry()) &&
                Objects.equal(getCustCountryName(), bizCust.getCustCountryName()) &&
                Objects.equal(getCreditRating(), bizCust.getCreditRating()) &&
                Objects.equal(getRatiTime(), bizCust.getRatiTime()) &&
                Objects.equal(getRatiBeginTime(), bizCust.getRatiBeginTime()) &&
                Objects.equal(getRatiEndTime(), bizCust.getRatiEndTime()) &&
                Objects.equal(getCustManager(), bizCust.getCustManager()) &&
                Objects.equal(getCreditLinesId(), bizCust.getCreditLinesId()) &&
                Objects.equal(getCreditRatio(), bizCust.getCreditRatio()) &&
                Objects.equal(getProductLinesTypeId(), bizCust.getProductLinesTypeId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDebtCode(), getCustNo(), getCustIdType(), getOrganizationCode(), getCustNameCN(), getCustNameEN(), getCustAddressCN(), getCustAddressEN(), getCustBusinessUnit(), getCustBusinessUnitName(), getCustRegistrastionType(), getCustRegistrastionTypeName(), getCustRatingCreditType(), getCustRatingCreditTypeName(), getCustScale(), getCustScaleName(), getGroupNumber(), getGroupName(), getMainBusiness(), getMainBusinessName(), getCustCountry(), getCustCountryName(), getCreditRating(), getRatiTime(), getRatiBeginTime(), getRatiEndTime(), getCustManager(), getCreditLinesId(), getCreditRatio(), getProductLinesTypeId());
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
                .add("productLinesTypeId", productLinesTypeId)
                .toString();
    }
}
