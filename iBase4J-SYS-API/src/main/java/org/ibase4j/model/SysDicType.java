package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

@TableName("sys_dic_type")
@SuppressWarnings("serial")
public class SysDicType extends BaseModel {

	@TableField("code_")
	private String code;
	@TableField("code_text")
	private String codeText;
	@TableField("sys_flg")
	private Integer sysFlg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	public Integer getSysFlg() {
		return sysFlg;
	}

	public void setSysFlg(Integer sysFlg) {
		this.sysFlg = sysFlg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codeText == null) ? 0 : codeText.hashCode());
		result = prime * result + ((sysFlg == null) ? 0 : sysFlg.hashCode());
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
		SysDicType other = (SysDicType) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (codeText == null) {
			if (other.codeText != null) {
				return false;
			}
		} else if (!codeText.equals(other.codeText)) {
			return false;
		}
		if (sysFlg == null) {
			if (other.sysFlg != null) {
				return false;
			}
		} else if (!sysFlg.equals(other.sysFlg)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SysDicType [code=" + code + ", codeText=" + codeText + ", sysFlg=" + sysFlg + "]";
	}

}
