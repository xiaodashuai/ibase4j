package org.ibase4j.vo;

import com.google.common.base.MoreObjects;

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
}
