/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：余额信息表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_CBE")
@SuppressWarnings("serial")
public class BizCBE extends BaseModel implements Serializable {
	/**
	 * 业务类型
	 */
	@TableField("OBJTYP")
	private String objType;
	/**
	 * 业务表INR
	 */
	@TableField("OBJINR")
	private Long objInr;
	/**
	 * 退出
	 */
	@TableField("EXTID")
	private String extid;
	/**
	 * 业务类型CBS ENTRY 类型
	 */
	@TableField("CBT")
	private String cbt;
	/**
	 * 初始交易类型
	 */
	@TableField("TRNTYP")
	private String trntyp;
	/**
	 * TRN表的INR，标识该发生额来自哪一笔交易
	 */
	@TableField("TRNINR")
	private Long trninr;
	/**
	 * 发生日期
	 */
	@TableField("DAT")
	private Date dat;
	/**
	 * 币种
	 */
	@TableField("CUR")
	private String cur;
	/**
	 * 金额
	 */
	@TableField("AMT")
	private BigDecimal amt;
	/**
	 * 授权标志 R: Released P: Preliminary E: Entered
	 */
	@TableField("RELFLG")
	private String relflg;
	/**
	 * 创建日期
	 */
	@TableField("CREDAT")
	private Date credat;
	/**
	 * 折算币种，即外币需要折算成人民币（系统默认赋值人民币）
	 */
	@TableField("XRFCUR")
	private String xrfcur;
	/**
	 * amt折算后的金额
	 */
	@TableField("XRFAMT")
	private BigDecimal xrfamt;
	/**
	 * 入口名称
	 */
	@TableField("NAM")
	private String nam;
	/**
	 * 核心账号1
	 */
	@TableField("ACC")
	private String acc;
	/**
	 * 核心账号2
	 */
	@TableField("ACC2")
	private String acc2;
	/**
	 * 可选日期
	 */
	@TableField("OPTDAT")
	private Date optdat;
	/**
	 * 记账日期
	 */
	@TableField("GLEDAT")
	private Date gledat;

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public Long getObjInr() {
		return objInr;
	}

	public void setObjInr(Long objInr) {
		this.objInr = objInr;
	}

	public String getExtid() {
		return extid;
	}

	public void setExtid(String extid) {
		this.extid = extid;
	}

	public String getCbt() {
		return cbt;
	}

	public void setCbt(String cbt) {
		this.cbt = cbt;
	}

	public String getTrntyp() {
		return trntyp;
	}

	public void setTrntyp(String trntyp) {
		this.trntyp = trntyp;
	}

	public Long getTrninr() {
		return trninr;
	}

	public void setTrninr(Long trninr) {
		this.trninr = trninr;
	}

	public Date getDat() {
		return dat;
	}

	public void setDat(Date dat) {
		this.dat = dat;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getRelflg() {
		return relflg;
	}

	public void setRelflg(String relflg) {
		this.relflg = relflg;
	}

	public Date getCredat() {
		return credat;
	}

	public void setCredat(Date credat) {
		this.credat = credat;
	}

	public String getXrfcur() {
		return xrfcur;
	}

	public void setXrfcur(String xrfcur) {
		this.xrfcur = xrfcur;
	}

	public BigDecimal getXrfamt() {
		return xrfamt;
	}

	public void setXrfamt(BigDecimal xrfamt) {
		this.xrfamt = xrfamt;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getAcc2() {
		return acc2;
	}

	public void setAcc2(String acc2) {
		this.acc2 = acc2;
	}

	public Date getOptdat() {
		return optdat;
	}

	public void setOptdat(Date optdat) {
		this.optdat = optdat;
	}

	public Date getGledat() {
		return gledat;
	}

	public void setGledat(Date gledat) {
		this.gledat = gledat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acc == null) ? 0 : acc.hashCode());
		result = prime * result + ((acc2 == null) ? 0 : acc2.hashCode());
		result = prime * result + ((amt == null) ? 0 : amt.hashCode());
		result = prime * result + ((cbt == null) ? 0 : cbt.hashCode());
		result = prime * result + ((cur == null) ? 0 : cur.hashCode());
		result = prime * result + ((extid == null) ? 0 : extid.hashCode());
		result = prime * result + ((nam == null) ? 0 : nam.hashCode());
		result = prime * result + ((objInr == null) ? 0 : objInr.hashCode());
		result = prime * result + ((objType == null) ? 0 : objType.hashCode());
		result = prime * result + ((relflg == null) ? 0 : relflg.hashCode());
		result = prime * result + ((trninr == null) ? 0 : trninr.hashCode());
		result = prime * result + ((trntyp == null) ? 0 : trntyp.hashCode());
		result = prime * result + ((xrfamt == null) ? 0 : xrfamt.hashCode());
		result = prime * result + ((xrfcur == null) ? 0 : xrfcur.hashCode());
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
		BizCBE other = (BizCBE) obj;
		if (acc == null) {
			if (other.acc != null){
				return false;
			}
		} else if (!acc.equals(other.acc)){
			return false;
		}
		if (acc2 == null) {
			if (other.acc2 != null){
				return false;
			}
		} else if (!acc2.equals(other.acc2)){
			return false;
		}
		if (amt == null) {
			if (other.amt != null){
				return false;
			}
		} else if (!amt.equals(other.amt)){
			return false;
		}
		if (cbt == null) {
			if (other.cbt != null){
				return false;
			}
		} else if (!cbt.equals(other.cbt)){
			return false;
		}
		if (cur == null) {
			if (other.cur != null){
				return false;
			}
		} else if (!cur.equals(other.cur)){
			return false;
		}
		if (extid == null) {
			if (other.extid != null){
				return false;
			}
		} else if (!extid.equals(other.extid)){
			return false;
		}
		if (nam == null) {
			if (other.nam != null){
				return false;
			}
		} else if (!nam.equals(other.nam)){
			return false;
		}
		if (objInr == null) {
			if (other.objInr != null){
				return false;
			}
		} else if (!objInr.equals(other.objInr)){
			return false;
		}
		if (objType == null) {
			if (other.objType != null){
				return false;
			}
		} else if (!objType.equals(other.objType)){
			return false;
		}
		if (relflg == null) {
			if (other.relflg != null){
				return false;
			}
		} else if (!relflg.equals(other.relflg)){
			return false;
		}
		if (trninr == null) {
			if (other.trninr != null){
				return false;
			}
		} else if (!trninr.equals(other.trninr)){
			return false;
		}
		if (trntyp == null) {
			if (other.trntyp != null){
				return false;
			}
		} else if (!trntyp.equals(other.trntyp)){
			return false;
		}
		if (xrfamt == null) {
			if (other.xrfamt != null){
				return false;
			}
		} else if (!xrfamt.equals(other.xrfamt)){
			return false;
		}
		if (xrfcur == null) {
			if (other.xrfcur != null){
				return false;
			}
		} else if (!xrfcur.equals(other.xrfcur)){
			return false;
		}
		return true;
	}

    public BizCBE() {
    }

    @Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("objType", objType)
				.add("objInr", objInr)
				.add("extid", extid)
				.add("cbt", cbt)
				.add("trntyp", trntyp)
				.add("trninr", trninr)
				.add("dat", dat)
				.add("cur", cur)
				.add("amt", amt)
				.add("relflg", relflg)
				.add("credat", credat)
				.add("xrfcur", xrfcur)
				.add("xrfamt", xrfamt)
				.add("nam", nam)
				.add("acc", acc)
				.add("acc2", acc2)
				.add("optdat", optdat)
				.add("gledat", gledat)
				.toString();
	}
}
