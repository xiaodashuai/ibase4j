package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

@TableName("biz_risk_classify")
@SuppressWarnings("serial")
public class BizRiskClassify extends BaseModel{

	@TableField("cl_code") // 分类编码外键
	private String clCode;

	@TableField("classify_date")
	private Long clDate;

	@TableField("overdue_hint")
	private Long ovHint;
	
	@TableField("cl_name")
	private String clName;

	public String getClCode() {
		return clCode;
	}

	public void setClCode(String clCode) {
		this.clCode = clCode;
	}

	public Long getClDate() {
		return clDate;
	}

	public void setClDate(Long clDate) {
		this.clDate = clDate;
	}

	public Long getOvHint() {
		return ovHint;
	}

	public void setOvHint(Long ovHint) {
		this.ovHint = ovHint;
	}

	public String getClName() {
		return clName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}
	
}
