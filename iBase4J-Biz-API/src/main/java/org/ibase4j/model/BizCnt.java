package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.google.common.base.MoreObjects;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;

@TableName("BIZ_CNT")
public class BizCnt extends BaseModel implements Serializable {

    //字段名
    @TableField("NAME_")
    private String name;

    //下个主键值
    @TableField("VAL_")
    private Integer val;

    //下个主键间隔
    @TableField("STEP_")
    private Integer step;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public BizCnt() {
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("val", val)
                .add("step", step)
                .toString();
    }
}
