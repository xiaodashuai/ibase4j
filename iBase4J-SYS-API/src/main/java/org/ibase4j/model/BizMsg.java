package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;
/**
 * 描述：短信发送记录
 * 
 * @author xy
 * @date 2018/05/25
 */

@TableName("SYS_MSG")
@SuppressWarnings("serial")
public class BizMsg extends BaseModel {
	/**
	 * 平台编号
	 */
	@TableField("BIZ_ID")
	private String bizId;
	/**
	 * 短信类型
	 */
	@TableField("TYPE_")
	private String type;
	/**
	 * 接收短信号码
	 */
	@TableField("PHONE_")
	private String phone;
	/**
	 * 短信内容
	 */
	@TableField("CONTENT_")
	private String content;
	/**
	 * 发送状态
	 */
	@TableField("SEND_STATE")
	private String sendState;
	
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
	@Override
	public String toString() {
		return "BizMsg [bizId=" + bizId + ", type=" + type + ", phone=" + phone + ", content=" + content
				+ ", sendState=" + sendState + ", toString()=" + super.toString() + "]";
	}
	
	
}
