package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;


@TableName("SYS_EVENT_LOG")
@SuppressWarnings("serial")
public class SysEvent extends BaseModel {
	/**
	 * 名称（登入，退出）
	 */
	@TableField("NAME_")
	private String name;
	/**
	 * 登陆用户
	 */
	@TableField("ACCOUNT_")
	private String account;
	/**
	 * 登录人ip地址
	 */
	@TableField("CLIENT_HOST")
	private String clientHost;
	/**
	 * 浏览器类型
	 */
	@TableField("USER_AGENT")
	private String userAgent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClientHost() {
		return clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
