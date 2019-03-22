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

@TableName("BIZ_CHECK_PLAN")
@SuppressWarnings("serial")
public class BizCheckPlan extends BaseModel {
	/**
	 * 检查名称
	 */
	@TableField("CK_NAME")
	private String ckName;
	/**
	 * 检查间隔（天）
	 */
	@TableField("CK_INTERVAL_TIME")
	private Integer ckTime;
	/**
	 * 风险分类级别ID(外键关联BIZ_CLASSFY_LEVEL表)
	 */
	@TableField("CLASSFY_LEVEL_ID")
	private Long classfyLevelId;

	public String getCkName() {
		return ckName;
	}

	public void setCkName(String ckName) {
		this.ckName = ckName;
	}

	public Integer getCkTime() {
		return ckTime;
	}

	public void setCkTime(Integer ckTime) {
		this.ckTime = ckTime;
	}

	public Long getClassfyLevelId() {
		return classfyLevelId;
	}

	public void setClassfyLevelId(Long classfyLevelId) {
		this.classfyLevelId = classfyLevelId;
	}

	@Override
	public String toString() {
		return "BizCheckPlan [ckName=" + ckName + ", ckTime=" + ckTime + ", classfyLevelId=" + classfyLevelId + "]";
	}

}
