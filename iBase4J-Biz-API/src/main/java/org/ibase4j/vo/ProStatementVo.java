package org.ibase4j.vo;

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
    public String toString() {
        return new ToStringBuilder(this)
                .append("bizid", bizid)
                .append("title", title)
                .append("createddate", createddate)
                .append("insprocessname", insprocessname)
                .append("actprocessname", actprocessname)
                .toString();
    }
}
