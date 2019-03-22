package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

//二级字典表
@TableName("sys_dic")
@SuppressWarnings("serial")
public class SysDic extends BaseModel {
	// 一级字典表中的code_(相当于父ID)
	@TableField("type_")
	private String type;
	// 二级字典表数据的序号
	@TableField("code_")
	private String code;
	// 二级字典表数据的文字描述
	@TableField("code_text")
	private String codeText;
	// 序号
	private Integer sortNo;
	// 是否可编辑(1是0否)
	@TableField("editable_")
	private Boolean editable;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((editable == null) ? 0 : editable.hashCode());
		result = prime * result + ((codeText == null) ? 0 : codeText.hashCode());
		result = prime * result + ((sortNo == null) ? 0 : sortNo.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SysDic other = (SysDic) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (editable == null) {
			if (other.editable != null) {
				return false;
			}
		} else if (!editable.equals(other.editable)) {
			return false;
		}
		if (codeText == null) {
			if (other.codeText != null) {
				return false;
			}
		} else if (!codeText.equals(other.codeText)) {
			return false;
		}
		if (sortNo == null) {
			if (other.sortNo != null) {
				return false;
			}
		} else if (!sortNo.equals(other.sortNo)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SysDic [type=" + type + ", code=" + code + ", codeText=" + codeText + ", sortNo=" + sortNo + ", editable="
				+ editable + "]";
	}

}
