package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class CjdkhtxyVo implements Serializable {
    String mediumid;
    String transok;
    String errNo;

    public CjdkhtxyVo() {
    }

    public String getMediumid() {
        return mediumid;
    }

    public void setMediumid(String mediumid) {
        this.mediumid = mediumid;
    }

    public String getTransok() {
        return transok;
    }

    public void setTransok(String transok) {
        this.transok = transok;
    }

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mediumid", mediumid)
                .add("transok", transok)
                .add("errNo", errNo)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CjdkhtxyVo)) return false;
        CjdkhtxyVo that = (CjdkhtxyVo) o;
        return Objects.equal(getMediumid(), that.getMediumid()) &&
                Objects.equal(getTransok(), that.getTransok()) &&
                Objects.equal(getErrNo(), that.getErrNo());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMediumid(), getTransok(), getErrNo());
    }
}
