package org.ibase4j.vo;

import java.io.Serializable;

/**
 * 功能：判断角色是否被选中
 * 
 * @author lianwenhao
 * @version 1.0
 */
public class RiskClassifyVo implements Serializable{
	private Long id;
	private String id_;
	private String name;
	private String remark;
	//日期设置
	private Long classifyDate;
	//逾期提示
	private Long overdueHint;

	private String clCode;


	public Long getId() {
		return id;
	}
	public String getId_() {
		return id == null ? "" : id.toString();
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getClassifyDate() {
		return classifyDate;
	}

	public void setClassifyDate(Long classifyDate) {
		this.classifyDate = classifyDate;
	}

	public Long getOverdueHint() {
		return overdueHint;
	}

	public void setOverdueHint(Long overdueHint) {
		this.overdueHint = overdueHint;
	}

	public String getClCode() {
		return clCode;
	}

	public void setClCode(String clCode) {
		this.clCode = clCode;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	
}
