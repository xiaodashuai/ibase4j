package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**风险分析
 * xiaoshuiquan
 * 2018.6.22
 * */

@TableName("biz_risk_analysis")
@SuppressWarnings("serial")
public class BizRiskAnalysis extends BaseModel implements Serializable{

	/**
	 * 业务编号(暂时用指债项方案id)
	 */
	@TableField("DEBT_CODE")
	private Long debtNum;
	/**交易背景描述*/
	@TableField("BRIF_BACKGROUND")
	private String backgroundBrief;
	/**货物情况描述*/
	@TableField("GOODS_SKETCH")
	private String dogs;
	/**行业投向*/
	@TableField("INDUSTRY_INVESTMENT")
	private String industryTo;
	/**背景国别*/
	@TableField("BACKGROUND_NATIONALITY")
	private String tbon;
	/**是否有信贷贷款*/
	@TableField("CREDIT_LOAN_NO")
	private Long wtclnia;
	/**信贷贷款合同编号*/
	@TableField("CREDIT_LOAN_CONTRACT_NUM")
	private Long clcn;
	/**打分表*/
	@TableField("SCORE")
	private Long grade;


	public String getBackgroundBrief() {
		return backgroundBrief;
	}

	public void setBackgroundBrief(String backgroundBrief) {
		this.backgroundBrief = backgroundBrief;
	}

	public String getDogs() {
		return dogs;
	}

	public void setDogs(String dogs) {
		this.dogs = dogs;
	}

	public String getIndustryTo() {
		return industryTo;
	}

	public void setIndustryTo(String industryTo) {
		this.industryTo = industryTo;
	}

	public String getTbon() {
		return tbon;
	}

	public void setTbon(String tbon) {
		this.tbon = tbon;
	}

	public Long getWtclnia() {
		return wtclnia;
	}

	public void setWtclnia(Long wtclnia) {
		this.wtclnia = wtclnia;
	}

	public Long getClcn() {
		return clcn;
	}

	public void setClcn(Long clcn) {
		this.clcn = clcn;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public BizRiskAnalysis() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("debtNum", debtNum)
				.add("backgroundBrief", backgroundBrief)
				.add("dogs", dogs)
				.add("industryTo", industryTo)
				.add("tbon", tbon)
				.add("wtclnia", wtclnia)
				.add("clcn", clcn)
				.add("grade", grade)
				.toString();
	}
}
