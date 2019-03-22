package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;
/**
 * 描述：检查计划配置
 * 
 * @author xy
 * @date 2018/05/08
 */
@TableName("BIZ_REDU_RATE_FORMULA")
@SuppressWarnings("serial")
public class BizReductionRatioFormula extends BaseModel{
	/**
	 * 比例名称
	 */
	@TableField("RATIO_NAME")
	private String ratioName;
	/**
	 * 比例公式
	 */
	@TableField("RATIO_EXPRES")
	private String ratioExpression;
	/**
	 * 比例规则说明
	 */
	@TableField("RULE_REMARK")
	private String ruleRemark;
	
	/**
	 * 风险分类级别ID(外键关联BIZ_CLASSFY_LEVEL表)
	 */
	@TableField("CLASSFY_LEVEL_ID")
	private Long classfyLevelId;
	
	@Override
	public String toString() {
		return "BizReductionRatioFormula [ratioName=" + ratioName + ", ratioExpression=" + ratioExpression
				+ ", ruleRemark=" + ruleRemark + ", classfyLevelId=" + classfyLevelId + "]";
	}

	public String getRatioName() {
		return ratioName;
	}

	public void setRatioName(String ratioName) {
		this.ratioName = ratioName;
	}

	public String getRatioExpression() {
		return ratioExpression;
	}

	public void setRatioExpression(String ratioExpression) {
		this.ratioExpression = ratioExpression;
	}

	public String getRuleRemark() {
		return ruleRemark;
	}

	public void setRuleRemark(String ruleRemark) {
		this.ruleRemark = ruleRemark;
	}

	public Long getClassfyLevelId() {
		return classfyLevelId;
	}

	public void setClassfyLevelId(Long classfyLevelId) {
		this.classfyLevelId = classfyLevelId;
	}
	
	
}
