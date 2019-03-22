package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * 描述：检查计划配置
 * 
 * @author xy
 * @date 2018/05/08
 */

@TableName("BIZ_BUSI_CATEGORY")
@SuppressWarnings("serial")
public class BizBusiCategory extends BaseModel implements Serializable {
	/**
	 * 评审点名称
	 */
	@TableField("CATEGORY_NAME")
	private String categoryNmae;
	/**
	 * 排序号
	 */
	@TableField("SORT_NO")
	private Long sortNo;
	/**
	 * 父ID
	 */
	@TableField("PARENT_ID")
	private Long parentId;

	public String getCategoryNmae() {
		return categoryNmae;
	}

	public void setCategoryNmae(String categoryNmae) {
		this.categoryNmae = categoryNmae;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public BizBusiCategory() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("categoryNmae", categoryNmae)
				.add("sortNo", sortNo)
				.add("parentId", parentId)
				.toString();
	}
}
