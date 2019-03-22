
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
 * 描述：员工请假
 * 
 * @author czm
 *
 */
@TableName("BIZ_LEAVE")
@SuppressWarnings("serial")
public class BizLeave extends BaseModel implements Serializable {
	private String title;
	@TableField("DAYS")
	private Integer days;// 请假天数
	@TableField("START_DATE")
	private Date startDate;// 休假开始日期
	@TableField("END_DATE")
	private Date endDate;// 休假结束日期
	/**
	 * 请假理由
	 */
	@TableField("reason_")
	private String reason;
	/**
	 * 流程编码
	 */
	@TableField("PIID")
	private String piid;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPiid() {
		return piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}


	public BizLeave() {
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("title", title)
				.add("days", days)
				.add("startDate", startDate)
				.add("endDate", endDate)
				.add("reason", reason)
				.add("piid", piid)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BizLeave)) return false;
		BizLeave bizLeave = (BizLeave) o;
		return Objects.equal(getTitle(), bizLeave.getTitle()) &&
				Objects.equal(getDays(), bizLeave.getDays()) &&
				Objects.equal(getStartDate(), bizLeave.getStartDate()) &&
				Objects.equal(getEndDate(), bizLeave.getEndDate()) &&
				Objects.equal(getReason(), bizLeave.getReason()) &&
				Objects.equal(getPiid(), bizLeave.getPiid());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getTitle(), getDays(), getStartDate(), getEndDate(), getReason(), getPiid());
	}
}
