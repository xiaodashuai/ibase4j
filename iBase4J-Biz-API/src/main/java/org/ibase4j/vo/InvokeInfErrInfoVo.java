package org.ibase4j.vo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class InvokeInfErrInfoVo implements Serializable {
    private String ErrorInfo;
    private String RspCode;
    private String RspMsg;
    private String ErrorNo;

    public InvokeInfErrInfoVo() {
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        ErrorInfo = errorInfo;
    }

    public String getRspCode() {
        return RspCode;
    }

    public void setRspCode(String rspCode) {
        RspCode = rspCode;
    }

    public String getRspMsg() {
        return RspMsg;
    }

    public void setRspMsg(String rspMsg) {
        RspMsg = rspMsg;
    }

    public String getErrorNo() {
        return ErrorNo;
    }

    public void setErrorNo(String errorNo) {
        ErrorNo = errorNo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ErrorInfo", ErrorInfo)
                .add("RspCode", RspCode)
                .add("RspMsg", RspMsg)
                .add("ErrorNo", ErrorNo)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvokeInfErrInfoVo)) return false;
        InvokeInfErrInfoVo that = (InvokeInfErrInfoVo) o;
        return Objects.equal(getErrorInfo(), that.getErrorInfo()) &&
                Objects.equal(getRspCode(), that.getRspCode()) &&
                Objects.equal(getRspMsg(), that.getRspMsg()) &&
                Objects.equal(getErrorNo(), that.getErrorNo());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getErrorInfo(), getRspCode(), getRspMsg(), getErrorNo());
    }
}
