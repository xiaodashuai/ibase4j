package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("sys_dept")
@SuppressWarnings("serial")
public class SysDept extends BaseModel implements Serializable{
	@TableField("DEPT_NAME")
	private String deptName;
	@TableField("ADDR_") // 联系地址
	private String address;
	@TableField("TEL_")
	private String tel;
	@TableField("CODE_") // 编码
	private String code;
	@TableField("PARENT_CODE") // 上级部门编码
	private String parentCode;
	@TableField("SORT_NO") // 菜单排列顺序
	private Integer sortNo;
	@TableField("TYPE_") // 类型(1:机构2:部门)
	private Integer type;
	@TableField("DEPT_LEVEL ") // 级别 1： 一级； 2 ：二级；3：三级；
	private Integer deptLevel;
	@TableField("UNIT_ID")
	private String unitId;
	@TableField("LEAF_")
	private Integer leaf;
	@TableField("CHILDREN_") // 是否有子类（1是；0否）
	private Integer children;
	@TableField("FIN_LICENSE_NO") // 金融机构许可证编号
	private String finLicenseNo;
	@TableField("BIZ_LICENSE_NO") // 营业执照编号
	private String bizLicenseNo;
	@TableField("ORG_CERT_NO") // 营业执照编号
	private String orgCertNo;
	@TableField("PROVINCE_") // 省
	private String province;
	@TableField("DISTRICT_") // 县/区
	private String district;
	@TableField("CITY_") // 市
	private String city;


	@TableField(exist = false)
	private String parentName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}

	public String getFinLicenseNo() {
		return finLicenseNo;
	}

	public void setFinLicenseNo(String finLicenseNo) {
		this.finLicenseNo = finLicenseNo;
	}

	public String getBizLicenseNo() {
		return bizLicenseNo;
	}

	public void setBizLicenseNo(String bizLicenseNo) {
		this.bizLicenseNo = bizLicenseNo;
	}

	public String getOrgCertNo() {
		return orgCertNo;
	}

	public void setOrgCertNo(String orgCertNo) {
		this.orgCertNo = orgCertNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(Integer deptLevel) {
		this.deptLevel = deptLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bizLicenseNo == null) ? 0 : bizLicenseNo.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((finLicenseNo == null) ? 0 : finLicenseNo.hashCode());
		result = prime * result + ((leaf == null) ? 0 : leaf.hashCode());
		result = prime * result + ((orgCertNo == null) ? 0 : orgCertNo.hashCode());
		result = prime * result + ((parentCode == null) ? 0 : parentCode.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((sortNo == null) ? 0 : sortNo.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((deptLevel == null) ? 0 : deptLevel.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		SysDept other = (SysDept) obj;
		if (address == null) {
			if (other.address != null){
				return false;
			}
		} else if (!address.equals(other.address)){
			return false;
		}
		if (bizLicenseNo == null) {
			if (other.bizLicenseNo != null){
				return false;
			}
		} else if (!bizLicenseNo.equals(other.bizLicenseNo)){
			return false;
		}
		if (children == null) {
			if (other.children != null){
				return false;
			}
		} else if (!children.equals(other.children)){
			return false;
		}
		if (city == null) {
			if (other.city != null){
				return false;
			}
		} else if (!city.equals(other.city)){
			return false;
		}
		if (code == null) {
			if (other.code != null){
				return false;
			}
		} else if (!code.equals(other.code)){
			return false;
		}
		if (district == null) {
			if (other.district != null){
				return false;
			}
		} else if (!district.equals(other.district)){
			return false;
		}
		if (finLicenseNo == null) {
			if (other.finLicenseNo != null){
				return false;
			}
		} else if (!finLicenseNo.equals(other.finLicenseNo)){
			return false;
		}
		if (leaf == null) {
			if (other.leaf != null){
				return false;
			}
		} else if (!leaf.equals(other.leaf)){
			return false;
		}
		if (orgCertNo == null) {
			if (other.orgCertNo != null){
				return false;
			}
		} else if (!orgCertNo.equals(other.orgCertNo)){
			return false;
		}
		if (parentCode == null) {
			if (other.parentCode != null){
				return false;
			}
		} else if (!parentCode.equals(other.parentCode)){
			return false;
		}
		if (province == null) {
			if (other.province != null){
				return false;
			}
		} else if (!province.equals(other.province)){
			return false;
		}
		if (sortNo == null) {
			if (other.sortNo != null){
				return false;
			}
		} else if (!sortNo.equals(other.sortNo)){
			return false;
		}
		if (tel == null) {
			if (other.tel != null){
				return false;
			}
		} else if (!tel.equals(other.tel)){
			return false;
		}
		if (type == null) {
			if (other.type != null){
				return false;
			}
		} else if (!type.equals(other.type)){
			return false;
		}
		return true;
	}

}
