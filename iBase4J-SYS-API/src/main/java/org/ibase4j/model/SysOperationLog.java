package org.ibase4j.model;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.util.Date;


/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-03-12
 */
@TableName("SYS_OPERATION_LOG")
public class SysOperationLog extends BaseModel {

    private static final long serialVersionUID = 1L;


	@TableField("RES_ID")
	private Long resId;

	@TableField("OPT_CODE_")
	private String optCode;

	@TableField("OPT_CONTENT")
	private String optContent;

	@TableField("OPT_DETAIL")
	private String optDetail;

	@TableField("IP_")
	private String operationerIp;

	@TableField("OPT_TIME")
	private Date optTime;

	@TableField("OPT_TYPE")
	private Long optType;


	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getOptCode() {
		return optCode;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getOptContent() {
		return optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	public String getOptDetail() {
		return optDetail;
	}

	public void setOptDetail(String optDetail) {
		this.optDetail = optDetail;
	}

	public String getOperationerIp() {
		return operationerIp;
	}

	public void setOperationerIp(String operationerIp) {
		this.operationerIp = operationerIp;
	}

	public Date getOptTime() {
		return optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

	public Long getOptType() {
		return optType;
	}

	public void setOptType(Long optType) {
		this.optType = optType;
	}
}