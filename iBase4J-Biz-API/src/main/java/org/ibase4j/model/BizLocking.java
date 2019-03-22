package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：锁表
 *
 * @author YLQ
 * @date 2018/10/08
 */
@TableName("BIZ_LOCKING")
public class BizLocking extends BaseModel implements Serializable {
    /**
     * 业务编号
     */
    @TableField("CODE_")
    private String code;
    /**
     * 方案编号
     */
    @TableField("DEBT_CODE")
    private String debtCode;
    /**
     * 锁失效时间
     */
    @TableField("PG_EXPI_DATE")
    private Date pgExpiDate;
    /**
     * 锁状态
     */
    @TableField("STATUS")
    private Integer status;
    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private Long roleId;
    /**
     * 角色名
     */
    @TableField("ROLE_NAME")
    private String roleName;
    /**
     * 用户id
     */
    @TableField("USER_ID")
    private Long userId;
    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 锁创建时间
     */
    @TableField("LOCKING_DATE")
    private Date lockingDate;





    public String getDebtCode() {
        return debtCode;
    }

    public void setDebtCode(String debtCode) {
        this.debtCode = debtCode;
    }

    public Date getPgExpiDate() {
        return pgExpiDate;
    }

    public void setPgExpiDate(Date pgExpiDate) {
        this.pgExpiDate = pgExpiDate;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getLockingDate() {
        return lockingDate;
    }

    public void setLockingDate(Date lockingDate) {
        this.lockingDate = lockingDate;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BizLocking() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizLocking)) return false;
        BizLocking that = (BizLocking) o;
        return Objects.equal(getCode(), that.getCode()) &&
                Objects.equal(getDebtCode(), that.getDebtCode()) &&
                Objects.equal(getPgExpiDate(), that.getPgExpiDate()) &&
                Objects.equal(getStatus(), that.getStatus()) &&
                Objects.equal(getRoleId(), that.getRoleId()) &&
                Objects.equal(getRoleName(), that.getRoleName()) &&
                Objects.equal(getUserId(), that.getUserId()) &&
                Objects.equal(getUserName(), that.getUserName()) &&
                Objects.equal(getLockingDate(), that.getLockingDate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode(), getDebtCode(), getPgExpiDate(), getStatus(), getRoleId(), getRoleName(), getUserId(), getUserName(), getLockingDate());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("debtCode", debtCode)
                .add("pgExpiDate", pgExpiDate)
                .add("status", status)
                .add("roleId", roleId)
                .add("roleName", roleName)
                .add("userId", userId)
                .add("userName", userName)
                .add("lockingDate", lockingDate)
                .toString();
    }
}
