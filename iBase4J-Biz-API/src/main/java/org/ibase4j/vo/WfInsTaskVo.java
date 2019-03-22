package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class WfInsTaskVo {
    private String tiid;
    private String piid;
    private String blockInsId;
    private String processName;
    private String activityName;
    private String aiid;
    private String starter;
    private String owner;
    private String userId;
    private String depId;
    private String roleId;
    private String startedDate;

    public String getTiid() {
        return tiid;
    }

    public void setTiid(String tiid) {
        this.tiid = tiid;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getBlockInsId() {
        return blockInsId;
    }

    public void setBlockInsId(String blockInsId) {
        this.blockInsId = blockInsId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAiid() {
        return aiid;
    }

    public void setAiid(String aiid) {
        this.aiid = aiid;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WfInsTaskVo)) return false;
        WfInsTaskVo that = (WfInsTaskVo) o;
        return Objects.equal(getTiid(), that.getTiid()) &&
                Objects.equal(getPiid(), that.getPiid()) &&
                Objects.equal(getBlockInsId(), that.getBlockInsId()) &&
                Objects.equal(getProcessName(), that.getProcessName()) &&
                Objects.equal(getActivityName(), that.getActivityName()) &&
                Objects.equal(getAiid(), that.getAiid()) &&
                Objects.equal(getStarter(), that.getStarter()) &&
                Objects.equal(getOwner(), that.getOwner()) &&
                Objects.equal(getUserId(), that.getUserId()) &&
                Objects.equal(getDepId(), that.getDepId()) &&
                Objects.equal(getRoleId(), that.getRoleId()) &&
                Objects.equal(getStartedDate(), that.getStartedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTiid(), getPiid(), getBlockInsId(), getProcessName(), getActivityName(), getAiid(), getStarter(), getOwner(), getUserId(), getDepId(), getRoleId(), getStartedDate());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tiid", tiid)
                .add("piid", piid)
                .add("blockInsId", blockInsId)
                .add("processName", processName)
                .add("activityName", activityName)
                .add("aiid", aiid)
                .add("starter", starter)
                .add("owner", owner)
                .add("userId", userId)
                .add("depId", depId)
                .add("roleId", roleId)
                .add("startedDate", startedDate)
                .toString();
    }
}
