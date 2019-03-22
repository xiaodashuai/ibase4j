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

    public BizCBE() {
    }

    public BizCBE(String objType, Long objInr, Long trninr) {
        this.objType = objType;
        this.objInr = objInr;
        this.trninr = trninr;
    }

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizCBE)) return false;
		BizCBE bizCBE = (BizCBE) o;
		return Objects.equal(getObjType(), bizCBE.getObjType()) &&
				Objects.equal(getObjInr(), bizCBE.getObjInr()) &&
				Objects.equal(getExtid(), bizCBE.getExtid()) &&
				Objects.equal(getCbt(), bizCBE.getCbt()) &&
				Objects.equal(getTrntyp(), bizCBE.getTrntyp()) &&
				Objects.equal(getTrninr(), bizCBE.getTrninr()) &&
				Objects.equal(getDat(), bizCBE.getDat()) &&
				Objects.equal(getCur(), bizCBE.getCur()) &&
				Objects.equal(getAmt(), bizCBE.getAmt()) &&
				Objects.equal(getRelflg(), bizCBE.getRelflg()) &&
				Objects.equal(getCredat(), bizCBE.getCredat()) &&
				Objects.equal(getXrfcur(), bizCBE.getXrfcur()) &&
				Objects.equal(getXrfamt(), bizCBE.getXrfamt()) &&
				Objects.equal(getNam(), bizCBE.getNam()) &&
				Objects.equal(getAcc(), bizCBE.getAcc()) &&
				Objects.equal(getAcc2(), bizCBE.getAcc2()) &&
				Objects.equal(getOptdat(), bizCBE.getOptdat()) &&
				Objects.equal(getGledat(), bizCBE.getGledat());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getObjType(), getObjInr(), getExtid(), getCbt(), getTrntyp(), getTrninr(), getDat(), getCur(), getAmt(), getRelflg(), getCredat(), getXrfcur(), getXrfamt(), getNam(), getAcc(), getAcc2(), getOptdat(), getGledat());
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
