/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
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
	 * 为*时代表业务中最新审批通过的数据
	 */
	@TableField("RELRES")
	private String relres;
	/**
	 * 经办机构
	 */
	@TableField("BCHKEYINR")
	private String bchkeyinr;

    /**
     * 流程状态
     */
    @TableField("PROCESS_STATUS")
	private Integer processStatus;

	/**
	 * 方案状态或发放状态
	 */
	@TableField("BIZ_STATUS")
	private Integer bizStatus;

	/**
	 * 为Y时代表最新的流水
	 */
	@TableField("RELFLG")
	private String relflg;

    /**
     * 修订版本号
     */
    @TableField("VERNUM_")
	private Integer verNum;

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

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

	public String getRelflg() {
		return relflg;
	}

	public void setRelflg(String relflg) {
		this.relflg = relflg;
	}

    public Integer getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Integer bizStatus) {
        this.bizStatus = bizStatus;
    }

    public Integer getVerNum() {
        return verNum;
    }

    public void setVerNum(Integer verNum) {
        this.verNum = verNum;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizTRN)) return false;
		BizTRN bizTRN = (BizTRN) o;
		return Objects.equal(getInidattim(), bizTRN.getInidattim()) &&
				Objects.equal(getInifrm(), bizTRN.getInifrm()) &&
				Objects.equal(getIninam(), bizTRN.getIninam()) &&
				Objects.equal(getIniusr(), bizTRN.getIniusr()) &&
				Objects.equal(getOwnref(), bizTRN.getOwnref()) &&
				Objects.equal(getObjtyp(), bizTRN.getObjtyp()) &&
				Objects.equal(getObjinr(), bizTRN.getObjinr()) &&
				Objects.equal(getObjnam(), bizTRN.getObjnam()) &&
				Objects.equal(getBusinessTypes(), bizTRN.getBusinessTypes()) &&
				Objects.equal(getBusinessTypeName(), bizTRN.getBusinessTypeName()) &&
				Objects.equal(getXrecurblk(), bizTRN.getXrecurblk()) &&
				Objects.equal(getRelcur(), bizTRN.getRelcur()) &&
				Objects.equal(getRelamt(), bizTRN.getRelamt()) &&
				Objects.equal(getReloricur(), bizTRN.getReloricur()) &&
				Objects.equal(getReloriamt(), bizTRN.getReloriamt()) &&
				Objects.equal(getExedat(), bizTRN.getExedat()) &&
				Objects.equal(getEtyextkey(), bizTRN.getEtyextkey()) &&
				Objects.equal(getRelres(), bizTRN.getRelres()) &&
				Objects.equal(getBchkeyinr(), bizTRN.getBchkeyinr()) &&
				Objects.equal(getProcessStatus(), bizTRN.getProcessStatus()) &&
				Objects.equal(getBizStatus(), bizTRN.getBizStatus()) &&
				Objects.equal(getRelflg(), bizTRN.getRelflg()) &&
				Objects.equal(getVerNum(), bizTRN.getVerNum());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getInidattim(), getInifrm(), getIninam(), getIniusr(), getOwnref(), getObjtyp(), getObjinr(), getObjnam(), getBusinessTypes(), getBusinessTypeName(), getXrecurblk(), getRelcur(), getRelamt(), getReloricur(), getReloriamt(), getExedat(), getEtyextkey(), getRelres(), getBchkeyinr(), getProcessStatus(), getBizStatus(), getRelflg(), getVerNum());
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
