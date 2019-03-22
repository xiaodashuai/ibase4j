/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：交易流水表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_TRN")
@SuppressWarnings("serial")
public class BizTRN extends BaseModel implements Serializable {
	/**
	 * 存盘时间
	 */
	@TableField("INIDATTIM")
	private Date inidattim;
	/**
	 * 交易代码(流水号)自定义
	 */
	@TableField("INIFRM")
	private String inifrm;
	/**
	 * 交易名称(业务名称)
	 */
	@TableField("ININAM")
	private String ininam;
	/**
	 * 登录柜员
	 */
	@TableField("INIUSR")
	private Long iniusr;
	/**
	 * 业务编号
	 */
	@TableField("OWNREF")
	private String ownref;
	/**
	 * 业务表名称（XXD）
	 */
	@TableField("OBJTYP")
	private String objtyp;
	/**
	 * 业务表INR(XXD的ID)
	 */
	//将Long类型系列化成String避免精度丢失
    @JsonSerialize(using = ToStringSerializer.class)
	@TableField("OBJINR")
	private Long objinr;
	/**
	 * 交易对象描述
	 */
	@TableField("OBJNAM")
	private String objnam;
	/**
	 * 交易对象描述
	 */
	@TableField("BUSINESS_TYPES")
	private String businessTypes;
	/**
	 * 产品种类名称
	 */
	@TableField(exist=false)
	private String businessTypeName;
	/**
	 * 可用货币
	 */
	@TableField("XRECURBLK")
	private String xrecurblk;
	/**
	 * 授权币种
	 */
	@TableField("RELCUR")
	private String relcur;
	/**
	 * 授权金额
	 */
	@TableField("RELAMT")
	private BigDecimal relamt;
	/**
	 * 币种
	 */
	@TableField("RELORICUR")
	private String reloricur;
	/**
	 * 金额
	 */
	@TableField("RELORIAMT")
	private BigDecimal reloriamt;
	/**
	 * 执行日期
	 */
	@TableField("EXEDAT")
	private Date exedat;
	/**
	 * 实体
	 */
	@TableField("ETYEXTKEY")
	private String etyextkey;
	/**
	 * Applied Signatures复核的状态
	 */
	@TableField("RELRES")
	private String relres;
	/**
	 * 经办机构
	 */
	@TableField("BCHKEYINR")
	private String bchkeyinr;

	public String getBchkeyinr() {
		return bchkeyinr;
	}

	public void setBchkeyinr(String bchkeyinr) {
		this.bchkeyinr = bchkeyinr;
	}

	public Date getInidattim() {
		return inidattim;
	}

	public void setInidattim(Date inidattim) {
		this.inidattim = inidattim;
	}

	public String getInifrm() {
		return inifrm;
	}

	public void setInifrm(String inifrm) {
		this.inifrm = inifrm;
	}

	public String getIninam() {
		return ininam;
	}

	public void setIninam(String ininam) {
		this.ininam = ininam;
	}

	public Long getIniusr() {
		return iniusr;
	}

	public void setIniusr(Long iniusr) {
		this.iniusr = iniusr;
	}

	public String getOwnref() {
		return ownref;
	}

	public void setOwnref(String ownref) {
		this.ownref = ownref;
	}

	public String getObjtyp() {
		return objtyp;
	}

	public void setObjtyp(String objtyp) {
		this.objtyp = objtyp;
	}

	public Long getObjinr() {
		return objinr;
	}

	public void setObjinr(Long objinr) {
		this.objinr = objinr;
	}

	public String getObjnam() {
		return objnam;
	}

	public void setObjnam(String objnam) {
		this.objnam = objnam;
	}

	public String getXrecurblk() {
		return xrecurblk;
	}

	public void setXrecurblk(String xrecurblk) {
		this.xrecurblk = xrecurblk;
	}

	public String getRelcur() {
		return relcur;
	}

	public void setRelcur(String relcur) {
		this.relcur = relcur;
	}

	public BigDecimal getRelamt() {
		return relamt;
	}

	public void setRelamt(BigDecimal relamt) {
		this.relamt = relamt;
	}

	public String getReloricur() {
		return reloricur;
	}

	public void setReloricur(String reloricur) {
		this.reloricur = reloricur;
	}

	public BigDecimal getReloriamt() {
		return reloriamt;
	}

	public void setReloriamt(BigDecimal reloriamt) {
		this.reloriamt = reloriamt;
	}

	public Date getExedat() {
		return exedat;
	}

	public void setExedat(Date exedat) {
		this.exedat = exedat;
	}

	public String getEtyextkey() {
		return etyextkey;
	}

	public void setEtyextkey(String etyextkey) {
		this.etyextkey = etyextkey;
	}

	public String getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(String businessTypes) {
		this.businessTypes = businessTypes;
	}
	
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	
	public String getRelres() {
		return relres;
	}

	public void setRelres(String relres) {
		this.relres = relres;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etyextkey == null) ? 0 : etyextkey.hashCode());
		result = prime * result + ((exedat == null) ? 0 : exedat.hashCode());
		result = prime * result + ((inidattim == null) ? 0 : inidattim.hashCode());
		result = prime * result + ((inifrm == null) ? 0 : inifrm.hashCode());
		result = prime * result + ((ininam == null) ? 0 : ininam.hashCode());
		result = prime * result + ((iniusr == null) ? 0 : iniusr.hashCode());
		result = prime * result + ((objinr == null) ? 0 : objinr.hashCode());
		result = prime * result + ((objnam == null) ? 0 : objnam.hashCode());
		result = prime * result + ((objtyp == null) ? 0 : objtyp.hashCode());
		result = prime * result + ((ownref == null) ? 0 : ownref.hashCode());
		result = prime * result + ((relamt == null) ? 0 : relamt.hashCode());
		result = prime * result + ((relcur == null) ? 0 : relcur.hashCode());
		result = prime * result + ((reloriamt == null) ? 0 : reloriamt.hashCode());
		result = prime * result + ((reloricur == null) ? 0 : reloricur.hashCode());
		result = prime * result + ((xrecurblk == null) ? 0 : xrecurblk.hashCode());
		result = prime * result + ((businessTypes == null) ? 0 : businessTypes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BizTRN other = (BizTRN) obj;
		if (etyextkey == null) {
			if (other.etyextkey != null) {
				return false;
			}
		} else if (!etyextkey.equals(other.etyextkey)) {
			return false;
		}
		if (exedat == null) {
			if (other.exedat != null) {
				return false;
			}
		} else if (!exedat.equals(other.exedat)) {
			return false;
		}
		if (inidattim == null) {
			if (other.inidattim != null) {
				return false;
			}
		} else if (!inidattim.equals(other.inidattim)) {
			return false;
		}
		if (inifrm == null) {
			if (other.inifrm != null) {
				return false;
			}
		} else if (!inifrm.equals(other.inifrm)) {
			return false;
		}
		if (ininam == null) {
			if (other.ininam != null) {
				return false;
			}
		} else if (!ininam.equals(other.ininam)) {
			return false;
		}
		if (iniusr == null) {
			if (other.iniusr != null){
				return false;
			}
		} else if (!iniusr.equals(other.iniusr)){
			return false;
		}
		if (objinr == null) {
			if (other.objinr != null) {
				return false;
			}
		} else if (!objinr.equals(other.objinr)) {
			return false;
		}
		if (objnam == null) {
			if (other.objnam != null){
				return false;
			}
		} else if (!objnam.equals(other.objnam)){
			return false;
		}
		if (objtyp == null) {
			if (other.objtyp != null) {
				return false;
			}
		} else if (!objtyp.equals(other.objtyp)) {
			return false;
		}
		if (ownref == null) {
			if (other.ownref != null) {
				return false;
			}
		} else if (!ownref.equals(other.ownref)){
			return false;
		}
		if (relamt == null) {
			if (other.relamt != null){
				return false;
			}
		} else if (!relamt.equals(other.relamt)) {
			return false;
		}
		if (relcur == null) {
			if (other.relcur != null) {
				return false;
			}
		} else if (!relcur.equals(other.relcur)) {
			return false;
		}
		if (reloriamt == null) {
			if (other.reloriamt != null){
				return false;
			}
		} else if (!reloriamt.equals(other.reloriamt)){
			return false;
		}
		if (reloricur == null) {
			if (other.reloricur != null) {
				return false;
			}
		} else if (!reloricur.equals(other.reloricur)) {
			return false;
		}
		if (xrecurblk == null) {
			if (other.xrecurblk != null) {
				return false;
			}
		} else if (!xrecurblk.equals(other.xrecurblk)) {
			return false;
		}
		if (businessTypes == null) {
			if (other.businessTypes != null) {
				return false;
			}
		} else if (!businessTypes.equals(other.businessTypes)) {
			return false;
		}
		return true;
	}

	public BizTRN() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("inidattim", inidattim)
				.add("inifrm", inifrm)
				.add("ininam", ininam)
				.add("iniusr", iniusr)
				.add("ownref", ownref)
				.add("objtyp", objtyp)
				.add("objinr", objinr)
				.add("objnam", objnam)
				.add("businessTypes", businessTypes)
				.add("businessTypeName", businessTypeName)
				.add("xrecurblk", xrecurblk)
				.add("relcur", relcur)
				.add("relamt", relamt)
				.add("reloricur", reloricur)
				.add("reloriamt", reloriamt)
				.add("exedat", exedat)
				.add("etyextkey", etyextkey)
				.add("relres", relres)
				.add("bchkeyinr", bchkeyinr)
				.toString();
	}
}
