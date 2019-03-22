/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 功能：客户类型表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_PTS")
public class BizPTS extends BaseModel implements Serializable {
	/**
	 * 债项方案id
	 */
	@TableField("DEBT_CODE")
	private String debtCode;
	/**
	 * 业务表名称（XXD）
	 */
	@TableField("OBJTYP")
	private String objtyp;
	/**
	 * 业务表INR(XXD的ID)
	 */
	@TableField("OBJINR")
	private String objinr;
	/**
	 * 角色种类
	 */
	@TableField("ROL")
	private String role;
	/**
	 * 关联客户信息表
	 */
	@TableField("PTYINR")
	private String ptyinr;


	public String getDebtCode() {
		return debtCode;
	}

	public void setDebtCode(String debtCode) {
		this.debtCode = debtCode;
	}

	public String getObjtyp() {
		return objtyp;
	}

	public void setObjtyp(String objtyp) {
		this.objtyp = objtyp;
	}

	public String getObjinr() {
		return objinr;
	}

	public void setObjinr(String objinr) {
		this.objinr = objinr;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPtyinr() {
		return ptyinr;
	}

	public void setPtyinr(String ptyinr) {
		this.ptyinr = ptyinr;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtCode", debtCode)
				.add("objtyp", objtyp)
				.add("objinr", objinr)
				.add("role", role)
				.add("ptyinr", ptyinr)
				.toString();
	}

	public BizPTS() {
	}
}
