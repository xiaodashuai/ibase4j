package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("biz_classify_level")
@SuppressWarnings("serial")
public class BizClassifyLevel extends BaseModel implements Serializable {

	@TableField("cl_code") // 分类编码
	private String clCode;
	@TableField("cl_name") // 分类名称
	private String clName;

	public String getClCode() {
		return clCode;
	}

	public void setClCode(String clCode) {
		this.clCode = clCode;
	}

	public String getClName() {
		return clName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clCode == null) ? 0 : clCode.hashCode());
		result = prime * result + ((clName == null) ? 0 : clName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		BizClassifyLevel other = (BizClassifyLevel) obj;
		if (clCode == null) {
			if (other.clCode != null){
				return false;
			}
		} else if (!clCode.equals(other.clCode)){
			return false;
		}
		if (clName == null) {
			if (other.clName != null){
				return false;
			}
		} else if (!clName.equals(other.clName)){
			return false;
		}
		return true;
	}

}
