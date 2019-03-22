package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.matrix.engine.foundation.domain.TaskVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;


/**
 * @author mac19
 */
public class ProStatementVo extends TaskVO implements java.io.Serializable{
    private static final long serialVersionUID = -7468647948932874399L;
    /**
     *
     */
    private String bizid;
    private String title;// 标题
    private Date createddate;
    private String insprocessname; //流程名称
    private String actprocessname;//活动名称

    public ProStatementVo() {
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProStatementVo)) return false;
        ProStatementVo that = (ProStatementVo) o;
        return Objects.equal(getBizid(), that.getBizid()) &&
                Objects.equal(getTitle(), that.getTitle()) &&
                Objects.equal(getCreateddate(), that.getCreateddate()) &&
                Objects.equal(getInsprocessname(), that.getInsprocessname()) &&
                Objects.equal(getActprocessname(), that.getActprocessname());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBizid(), getTitle(), getCreateddate(), getInsprocessname(), getActprocessname());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bizid", bizid)
                .add("title", title)
                .add("createddate", createddate)
                .add("insprocessname", insprocessname)
                .add("actprocessname", actprocessname)
                .toString();
    }
}
