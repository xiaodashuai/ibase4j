package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

@TableName("biz_session")
@SuppressWarnings("serial")
public class BizSession extends BaseModel implements Serializable {
    private String sessionId;

    @TableField("account_")
    private String account;
//    @TableField("END_TIME")
//    private Date endTime;
    @TableField("ip_")
    private String ip;
    /**
     *   会话状态  0 已登录 1 已退出 2 已过期 3 已踢出
     */
    @TableField("SESSION_STATE")
    private Integer sessionState;


    private Date startTime;

    public Integer getSessionState() {
        return sessionState;
    }

    public void setSessionState(Integer sessionState) {
        this.sessionState = sessionState;
    }
    /**
     * @return the value of sys_session.session_id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the value for sys_session.session_id
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    /**
     * @return the value of sys_session.account_
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the value for sys_session.account_
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * @return the value of sys_session.ip_
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the value for sys_session.ip_
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * @return the value of sys_session.start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the value for sys_session.start_time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizSession)) return false;
        BizSession that = (BizSession) o;
        return Objects.equal(getSessionId(), that.getSessionId()) &&
                Objects.equal(getAccount(), that.getAccount()) &&
                Objects.equal(getIp(), that.getIp()) &&
                Objects.equal(getSessionState(), that.getSessionState()) &&
                Objects.equal(getStartTime(), that.getStartTime());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSessionId(), getAccount(), getIp(), getSessionState(), getStartTime());
    }

    public BizSession() {
    }

    /**
     *
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sessionId", sessionId)
                .add("account", account)
                .add("ip", ip)
                .add("sessionState", sessionState)
                .add("startTime", startTime)
                .toString();
    }
}
