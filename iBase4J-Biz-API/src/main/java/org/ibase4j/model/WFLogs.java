/**
 * 
 */
package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：工作流登录日志
 * 
 * @author czm
 *
 */
@TableName("wf_logs")
@SuppressWarnings("serial")
public class WFLogs extends BaseModel implements Serializable {
	@TableField("lg_date")
	private Date lgDate;// 登录日期
	@TableField("ip_address")
	private String ipAddress;

	public Date getLgDate() {
		return lgDate;
	}

	public void setLgDate(Date lgDate) {
		this.lgDate = lgDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public WFLogs() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("lgDate", lgDate)
				.add("ipAddress", ipAddress)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof WFLogs)) return false;
		WFLogs wfLogs = (WFLogs) o;
		return Objects.equal(getLgDate(), wfLogs.getLgDate()) &&
				Objects.equal(getIpAddress(), wfLogs.getIpAddress());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getLgDate(), getIpAddress());
	}
}
