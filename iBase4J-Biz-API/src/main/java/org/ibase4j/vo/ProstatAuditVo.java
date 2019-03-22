package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class ProstatAuditVo implements java.io.Serializable {
	private Long id;
	private String userId;
	private String userName;
	private String auditOpinion;
	private String activityName;
	private String adid;
	private String aiid;
	private String piid;
	private String ptid;
	private String subject;
	private boolean isSubmit;
	private String taskId;
	private String tdid;
	
	public String getPtid() {
		return ptid;
	}

	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}

	public String getAiid() {
		return aiid;
	}

	public void setAiid(String aiid) {
		this.aiid = aiid;
	}

	public String getPiid() {
		return piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isSubmit() {
		return isSubmit;
	}

	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTdid() {
		return tdid;
	}

	public void setTdid(String tdid) {
		this.tdid = tdid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProstatAuditVo)) return false;
		ProstatAuditVo that = (ProstatAuditVo) o;
		return isSubmit() == that.isSubmit() &&
				Objects.equal(getId(), that.getId()) &&
				Objects.equal(getUserId(), that.getUserId()) &&
				Objects.equal(getUserName(), that.getUserName()) &&
				Objects.equal(getAuditOpinion(), that.getAuditOpinion()) &&
				Objects.equal(getActivityName(), that.getActivityName()) &&
				Objects.equal(getAdid(), that.getAdid()) &&
				Objects.equal(getAiid(), that.getAiid()) &&
				Objects.equal(getPiid(), that.getPiid()) &&
				Objects.equal(getPtid(), that.getPtid()) &&
				Objects.equal(getSubject(), that.getSubject()) &&
				Objects.equal(getTaskId(), that.getTaskId()) &&
				Objects.equal(getTdid(), that.getTdid());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getUserId(), getUserName(), getAuditOpinion(), getActivityName(), getAdid(), getAiid(), getPiid(), getPtid(), getSubject(), isSubmit(), getTaskId(), getTdid());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("userId", userId)
				.add("userName", userName)
				.add("auditOpinion", auditOpinion)
				.add("activityName", activityName)
				.add("adid", adid)
				.add("aiid", aiid)
				.add("piid", piid)
				.add("ptid", ptid)
				.add("subject", subject)
				.add("isSubmit", isSubmit)
				.add("taskId", taskId)
				.add("tdid", tdid)
				.toString();
	}
}
