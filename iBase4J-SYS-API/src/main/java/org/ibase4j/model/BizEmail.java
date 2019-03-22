package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;
/**
 * 描述：邮件发送记录
 * 
 * @author xy
 * @date 2018/05/25
 */

@TableName("SYS_EMAIL")
@SuppressWarnings("serial")
public class BizEmail extends BaseModel {
	/**
	 * 邮件名称
	 */
	@TableField("EMAIL_NAME")
	private String emailName;
	/**
	 * 发送邮件的人
	 */
	@TableField("SENDER_")
	private String sender;
	/**
	 * 邮件标题
	 */
	@TableField("EMAIL_TITLE")
	private String emaiTitle;
	/**
	 * 邮件内容
	 */
	@TableField("EMAIL_CONTENT")
	private String emailContent;
	public String getEmailName() {
		return emailName;
	}
	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getEmaiTitle() {
		return emaiTitle;
	}
	public void setEmaiTitle(String emaiTitle) {
		this.emaiTitle = emaiTitle;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	@Override
	public String toString() {
		return "BizEmail [emailName=" + emailName + ", sender=" + sender + ", emaiTitle=" + emaiTitle
				+ ", emailContent=" + emailContent + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
