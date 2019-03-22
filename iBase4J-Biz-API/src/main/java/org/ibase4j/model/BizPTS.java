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
	 * 客户名称（中文）
	 */
	@TableField("CUST_NAME_CN")
	private String custNameCN;
	/**
	 * 客户表主键
	 */
    @TableField("PTYINR")
	private String ptyinr;
<<<<<<< HEAD
=======
    /**
	 * 业务主表主键
	 */
    @TableField("BIZ_ID")
	private String bizId;
    /**
     * 债项方案编号
     */
    @TableField("DEBT_CODE")
    private String debtCode;
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a

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

	public String getCustNameCN() {
		return custNameCN;
	}

	public void setCustNameCN(String custNameCN) {
		this.custNameCN = custNameCN;
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
				.add("custNameCN", custNameCN)
				.add("ptyinr", ptyinr)
				.toString();
	}

	public BizPTS() {
	}

    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizPTS)) return false;
        BizPTS bizPTS = (BizPTS) o;
        return Objects.equal(getDebtCode(), bizPTS.getDebtCode()) &&
                Objects.equal(getObjtyp(), bizPTS.getObjtyp()) &&
                Objects.equal(getObjinr(), bizPTS.getObjinr()) &&
                Objects.equal(getRole(), bizPTS.getRole()) &&
                Objects.equal(getCustNameCN(), bizPTS.getCustNameCN()) &&
<<<<<<< HEAD
                Objects.equal(getPtyinr(), bizPTS.getPtyinr());
=======
                Objects.equal(getPtyinr(), bizPTS.getPtyinr()) &&
                Objects.equal(getDebtCode(), bizPTS.getDebtCode()) &&
                Objects.equal(getBizId(), bizPTS.getBizId());
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        return Objects.hashCode(getDebtCode(), getObjtyp(), getObjinr(), getRole(), getCustNameCN(), getPtyinr());
=======
        return Objects.hashCode(getObjtyp(), getObjinr(), getRole(), getCustNo(), getCustNameCN(), getPtyinr(), getBizId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("objtyp", objtyp)
                .add("objinr", objinr)
                .add("role", role)
                .add("custNo", custNo)
                .add("custNameCN", custNameCN)
                .add("ptyinr", ptyinr)
                .add("bizId", bizId)
                .add("debtCode", debtCode)
                .toString();
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
    }

}
