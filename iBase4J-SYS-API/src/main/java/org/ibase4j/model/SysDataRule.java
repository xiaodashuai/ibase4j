package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

@TableName("SYS_DATARULE")
@SuppressWarnings("serial")
public class SysDataRule extends BaseModel {
	@TableField("CODE_")
	private Integer code;
	@TableField("SQL_SEBTENCE")
	private String sqlSebtence;
	@TableField("SQL_LEVEL")
	private Integer sqlLevel;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getSqlSebtence() {
		return sqlSebtence;
	}

	public void setSqlSebtence(String sqlSebtence) {
		this.sqlSebtence = sqlSebtence;
	}

	public Integer getSqlLevel() {
		return sqlLevel;
	}

	public void setSqlLevel(Integer sqlLevel) {
		this.sqlLevel = sqlLevel;
	}
	
}