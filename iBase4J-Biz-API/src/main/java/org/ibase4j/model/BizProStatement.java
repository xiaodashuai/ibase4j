package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;
/**
 * @author mac19
 */
@TableName("BIZ_INSPROCESS_STATEMENT")
public class BizProStatement extends BaseModel implements Serializable {

    @TableField("TITLE")
    private String title;
    @TableField("BIZ_ID")
    private String bizid;
    @TableField("CREATED_DATE")
    private Date createddate;
    @TableField("INS_PROCESS_NAME")
    private String insprocessname;
    @TableField("ACT_PROCESS_NAME")
    private String actprocessname;
    @TableField("PROCESS_DID")
    private String pdid;
    /**
     * 流程编码
     */
    @TableField("PIID")
    private String piid;

    public BizProStatement() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getInsprocessname() {
        return insprocessname;
    }

    public void setInsprocessname(String insprocessname) {
        this.insprocessname = insprocessname;
    }

    public String getActprocessname() {
        return actprocessname;
    }

    public void setActprocessname(String actprocessname) {
        this.actprocessname = actprocessname;
    }

    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizProStatement)) return false;
        BizProStatement that = (BizProStatement) o;
        return Objects.equal(getTitle(), that.getTitle()) &&
                Objects.equal(getBizid(), that.getBizid()) &&
                Objects.equal(getCreateddate(), that.getCreateddate()) &&
                Objects.equal(getInsprocessname(), that.getInsprocessname()) &&
                Objects.equal(getActprocessname(), that.getActprocessname()) &&
                Objects.equal(getPdid(), that.getPdid()) &&
                Objects.equal(getPiid(), that.getPiid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTitle(), getBizid(), getCreateddate(), getInsprocessname(), getActprocessname(), getPdid(), getPiid());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("bizid", bizid)
                .add("createddate", createddate)
                .add("insprocessname", insprocessname)
                .add("actprocessname", actprocessname)
                .add("pdid", pdid)
                .add("piid", piid)
                .toString();
    }
}
