package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.ibase4j.model.BizDebtGrant;

/**
 * @program: iBase4J
 * @description: 待办/已办 废弃详情数据
 * @author: lzy
 * @create: 2019-01-02 12:06
 */
public class BizDebtGrantVo extends BizDebtGrant {

    /**
     * 方案主币中   “英文 中文” 格式
     */
    private  String mpcName;

    public String getMpcName() {
        return mpcName;
    }

    public void setMpcName(String mpcName) {
        this.mpcName = mpcName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BizDebtGrantVo)) return false;
        if (!super.equals(o)) return false;
        BizDebtGrantVo that = (BizDebtGrantVo) o;
        return Objects.equal(getMpcName(), that.getMpcName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getMpcName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mpcName", mpcName)
                .toString();
    }
}
