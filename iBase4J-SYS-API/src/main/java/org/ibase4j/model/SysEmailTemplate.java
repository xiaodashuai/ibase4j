package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

/**
 * <p>
 * 邮件模版表
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-01-29
 */
@SuppressWarnings("serial")
@TableName("sys_email_template")
public class SysEmailTemplate extends BaseModel {
	/**
	 * 所属机构代码
	 */
	@TableField(value = "dept_code")
	private String deptCode;
	/**
	 * 邮件名称
	 */
	@TableField(value = "email_name")
	private String emailName;
	/**
	 * 发送邮件帐号
	 */
	@TableField(value = " email_account")
	private String emailAccount;
	/**
	 * 排序号
	 */
	@TableField(value = "sort_")
	private Integer sort;
	/**
	 * 标题模版
	 */
	@TableField(value = "title_")
	private String title;
	/**
	 * 内容模板
	 */
	@TableField(value = "template_")
	private String template;
	/**
	 * 功能类型
	 */
	@TableField(value = "type_")
	private String type;

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected Serializable pkVal() {
		return getId();
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
}
