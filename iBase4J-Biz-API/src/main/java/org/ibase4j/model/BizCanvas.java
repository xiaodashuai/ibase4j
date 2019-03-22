package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("BIZ_CANVAS")
public class BizCanvas extends BaseModel implements Serializable {

    //查询编号
    @TableField("BIZ_CODE")
    private String bizcode;

    //债项事前录入A、债项补录B、审批过程录入S、发放审核F三种
    @TableField("TYPE_")
    private String type;

    //存事中的taskid，补录给1好了，发放审核编号
    @TableField("EXTRA_")
    private String extra;

    //页码,可用于排序
    @TableField("NUM_")
    private Integer num;

    //文件名
    @TableField("FILENAME_")
    private String filename;

    /**
     * 版本号：记录方案变更版本;
     */
    @TableField("VERNUM_")
    private Integer verNum;

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getVerNum() {
        return verNum;
    }

    public void setVerNum(Integer verNum) {
        this.verNum = verNum;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bizcode", bizcode)
                .add("type", type)
                .add("extra", extra)
                .add("num", num)
                .add("filename", filename)
				.add("verNum", verNum)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizCanvas)) return false;
        BizCanvas bizCanvas = (BizCanvas) o;
        return Objects.equal(getBizcode(), bizCanvas.getBizcode()) &&
                Objects.equal(getType(), bizCanvas.getType()) &&
                Objects.equal(getExtra(), bizCanvas.getExtra()) &&
                Objects.equal(getNum(), bizCanvas.getNum()) &&
                Objects.equal(getFilename(), bizCanvas.getFilename()) &&
                Objects.equal(getVerNum(), bizCanvas.getVerNum());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBizcode(), getType(), getExtra(), getNum(), getFilename(), getVerNum());
    }
}
