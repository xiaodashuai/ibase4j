package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("BIZ_TMPSAVE")
public class BizTemporarySave extends BaseModel implements Serializable {

    @TableField("USER_ID")
    private String userid;

    @TableField("ROL_ID")
    private String rolid;

    @TableField("BIZ_CODE")
    private String bizcode;
    @TableField("TASK_ID")
    private String taskid;

    @TableField("FILE_NAME")
    private String filename;

    @TableField(exist = false)
    private Object obj;

    @TableField(exist = false)
    private String userName;

    @TableField("TYPE_")
    private String type;

    //项目名称
    @TableField("PROJECT_NAME")
    private String projectName;

    @TableField("DEPT_CODE")
    private String deptCode;

    //设置暂存需要展示的机构名称
    @TableField(exist = false)
    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRolid() {
        return rolid;
    }

    public void setRolid(String rolid) {
        this.rolid = rolid;
    }

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public BizTemporarySave() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userid", userid)
                .add("rolid", rolid)
                .add("bizcode", bizcode)
                .add("taskid", taskid)
                .add("filename", filename)
                .add("obj", obj)
                .add("userName", userName)
                .add("type", type)
                .add("projectName", projectName)
                .add("deptCode", deptCode)
                .add("deptName", deptName)
                .toString();
    }
}
