package org.ibase4j.model;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;


/**
 * <p>
 * 
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-03-16
 *
 *
 *
 *
 */
@TableName("sys_msg_config")
public class SysMsgConfig extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 所属机构
     */
    @TableField("dept_code")
    private String deptCode;
    /**
     * 短信模板名称
     */
	@TableField("msg_name")
	private String msgName;
    /**
     * 短信模板标题
     */
	@TableField("title_")
	private String title;
    /**
     * 短信模板内容
     */
	@TableField("template_")
	private String template;
    /**
     * 所属模块
     */
	@TableField("module_")
	private String module;

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
}
