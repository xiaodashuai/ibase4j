package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

/**
 * 描述：数据权限与部门岗位关系表
 * 
 * @author czm
 * @version 1.2 日期：2018/6/22
 */
@TableName("SYS_DEPT_DATARULE")
@SuppressWarnings("serial")
public class SysDeptDataRule extends BaseModel {
	@TableField("ROLE_CODE") // 岗位编码
	private String roleCode;
	@TableField("DATA_ID") // 数据权限编号
	private Long dataId;
	@TableField("DEPT_ORG") // 部门编码
	private String deptOrg;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getDeptOrg() {
		return deptOrg;
	}

	public void setDeptOrg(String deptOrg) {
		this.deptOrg = deptOrg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataId == null) ? 0 : dataId.hashCode());
		result = prime * result + ((deptOrg == null) ? 0 : deptOrg.hashCode());
		result = prime * result + ((roleCode == null) ? 0 : roleCode.hashCode());
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
		SysDeptDataRule other = (SysDeptDataRule) obj;
		if (dataId == null) {
			if (other.dataId != null){
				return false;
			}
		} else if (!dataId.equals(other.dataId)){
			return false;
		}
		if (deptOrg == null) {
			if (other.deptOrg != null){
				return false;
			}
		} else if (!deptOrg.equals(other.deptOrg)){
			return false;
		}
		if (roleCode == null) {
			if (other.roleCode != null){
				return false;
			}
		} else if (!roleCode.equals(other.roleCode)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SysDeptDataRule [roleCode=" + roleCode + ", dataId=" + dataId + ", deptOrg=" + deptOrg + "]";
	}

}