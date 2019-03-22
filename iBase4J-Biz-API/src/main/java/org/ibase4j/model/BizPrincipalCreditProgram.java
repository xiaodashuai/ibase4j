package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;
/**
 * 描述：客户授信信息
 * 
 * @author xy
 * @date 2018/05/14
 */

@TableName("BIZ_PRINCIPAL_CREDIT_PROGRAM")
@SuppressWarnings("serial")
public class BizPrincipalCreditProgram extends BaseModel implements Serializable {
	/**
	 * 客户编号(核心系统用户编码)
	 */
	@TableField("CUST_NO")
	private String custNo;
	/**
	 * 信用评级
	 */
	@TableField("CREDIT_TATINGS")
	private Long creditTatings;
	/**
	 * 评级起始日
	 */
	@TableField("RATING_START_DATE")
	private Date ratingStartDate;
	/**
	 * 同业授信金额
	 */
	@TableField("INTERBANK_CREDIT_AMOUNT")
	private Double interbankCreditAmount;
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public Long getCreditTatings() {
		return creditTatings;
	}
	public void setCreditTatings(Long creditTatings) {
		this.creditTatings = creditTatings;
	}
	public Date getRatingStartDate() {
		return ratingStartDate;
	}
	public void setRatingStartDate(Date ratingStartDate) {
		this.ratingStartDate = ratingStartDate;
	}
	public Double getInterbankCreditAmount() {
		return interbankCreditAmount;
	}
	public void setInterbankCreditAmount(Double interbankCreditAmount) {
		this.interbankCreditAmount = interbankCreditAmount;
	}

	public BizPrincipalCreditProgram() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("custNo", custNo)
				.add("creditTatings", creditTatings)
				.add("ratingStartDate", ratingStartDate)
				.add("interbankCreditAmount", interbankCreditAmount)
				.toString();
	}
}
