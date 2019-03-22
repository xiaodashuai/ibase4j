package org.ibase4j.vo;


public class SysDataRuleCheckVo implements java.io.Serializable {
    private static final long serialVersionUID = 1406275095023736604L;

    private long dataruleID;

    private Integer code;

    private Integer sqlLevel;

    private String sqlSebtence;

    private boolean checked;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getDataruleID() {
        return dataruleID;
    }

    public void setDataruleID(long dataruleID) {
        this.dataruleID = dataruleID;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setSqlLevel(Integer sqlLevel) {
        this.sqlLevel = sqlLevel;
    }

    public Integer getSqlLevel() {
        return sqlLevel;
    }

    public String getSqlSebtence() {
        return sqlSebtence;
    }

    public void setSqlSebtence(String sqlSebtence) {
        this.sqlSebtence = sqlSebtence;
    }

}