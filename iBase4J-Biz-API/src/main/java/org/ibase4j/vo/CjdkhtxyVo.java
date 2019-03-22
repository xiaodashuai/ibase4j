package org.ibase4j.vo;

import com.google.common.base.MoreObjects;

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
}
