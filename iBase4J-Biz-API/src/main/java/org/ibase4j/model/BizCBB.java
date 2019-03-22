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
 * 功能：金额流水信息表
 * @author czm 
 * 日期：2018/7/6
 */
@TableName("BIZ_CBB")
@SuppressWarnings("serial")
public class BizCBB extends BaseModel implements Serializable {
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
	 * CBE的INR,生成该余额的CBE的INR
	 */
	@TableField("CBEINR")
	private Long cbeInr;
	/**
	 * 金额类型
	 */
	@TableField("CBC")
	private String cbc;
	/**
	 * 金额种类
	 */
	@TableField("EXTID")
	private String extid;
	/**
	 * 创建日期
	 */
	@TableField("BEGDAT")
	private Date begdat;
	/**
	 * 结束日期
	 */
	@TableField("ENDDAT")
	private Date enddat;
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
	 * 折算币种
	 */
	@TableField("XRFCUR")
	private String xrfcur;
	/**
	 * 折算后金额
	 */
	@TableField("XRFAMT")
	private BigDecimal xrfamt;
	/**
	 * 提交余额币种
	 */
	@TableField("COMCUR")
	private String comcur;
	/**
	 * 提交余额金额
	 */
	@TableField("COMAMT")
	private BigDecimal comamt;
	/**
	 * 修改提交余额币种
	 */
	@TableField("XCOCUR")
	private String xcocur;
	/**
	 * 修改提交余额金额
	 */
	@TableField("XCOAMT")
	private BigDecimal xcoamt;

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

	public Long getCbeInr() {
		return cbeInr;
	}

	public void setCbeInr(Long cbeInr) {
		this.cbeInr = cbeInr;
	}

	public String getCbc() {
		return cbc;
	}

	public void setCbc(String cbc) {
		this.cbc = cbc;
	}

	public String getExtid() {
		return extid;
	}

	public void setExtid(String extid) {
		this.extid = extid;
	}

	public Date getBegdat() {
		return begdat;
	}

	public void setBegdat(Date begdat) {
		this.begdat = begdat;
	}

	public Date getEnddat() {
		return enddat;
	}

	public void setEnddat(Date enddat) {
		this.enddat = enddat;
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

	public String getComcur() {
		return comcur;
	}

	public void setComcur(String comcur) {
		this.comcur = comcur;
	}

	public BigDecimal getComamt() {
		return comamt;
	}

	public void setComamt(BigDecimal comamt) {
		this.comamt = comamt;
	}

	public String getXcocur() {
		return xcocur;
	}

	public void setXcocur(String xcocur) {
		this.xcocur = xcocur;
	}

	public BigDecimal getXcoamt() {
		return xcoamt;
	}

	public void setXcoamt(BigDecimal xcoamt) {
		this.xcoamt = xcoamt;
	}

    public BizCBB() {
    }

    @Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("objType", objType)
				.add("objInr", objInr)
				.add("cbeInr", cbeInr)
				.add("cbc", cbc)
				.add("extid", extid)
				.add("begdat", begdat)
				.add("enddat", enddat)
				.add("cur", cur)
				.add("amt", amt)
				.add("xrfcur", xrfcur)
				.add("xrfamt", xrfamt)
				.add("comcur", comcur)
				.add("comamt", comamt)
				.add("xcocur", xcocur)
				.add("xcoamt", xcoamt)
				.toString();
	}
}
