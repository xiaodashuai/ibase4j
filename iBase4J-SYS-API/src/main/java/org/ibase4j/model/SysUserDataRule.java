package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("SYS_USER_DATARULE")
@SuppressWarnings("serial")
public class SysUserDataRule extends BaseModel implements Serializable {
	@TableField("USER_ID")
	private Long userId;
	@TableField("DATA_ID")
	private Long dataId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

}
