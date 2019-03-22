package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

/**
 * 描述: 用户转换部门/岗位/离职等需要将自己的业务交接给新人
 * 
 * @author czm
 * @dete 2018/04/02
 */
@TableName("SYS_WORK_DELIVER")
@SuppressWarnings("serial")
public class SysWorkDeliver extends BaseModel {
	@TableField("OLD_USER_ID")
	private Long oldUserId;
	@TableField("NEW_USER_ID")
	private Long newUserId;

	public Long getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(Long oldUserId) {
		this.oldUserId = oldUserId;
	}

	public Long getNewUserId() {
		return newUserId;
	}

	public void setNewUserId(Long newUserId) {
		this.newUserId = newUserId;
	}

}
