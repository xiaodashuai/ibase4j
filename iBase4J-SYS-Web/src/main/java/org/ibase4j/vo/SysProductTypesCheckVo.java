package org.ibase4j.vo;


import java.util.List;

public class SysProductTypesCheckVo implements java.io.Serializable {
    private static final long serialVersionUID = 1406275095023736604L;

    private long productTypesID;

    private String code;

    private  String name;

    private String parentCode;

    private boolean checked;

    private String remark;

    private List sonList;

    public List<SysProductTypesCheckVo> getSonList() {
        return sonList;
    }
    public void setSonList(List<SysProductTypesCheckVo> sonList) {
        this.sonList = sonList;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getProductTypesID() {
        return productTypesID;
    }

    public void setProductTypesID(long productTypesID) {
        this.productTypesID = productTypesID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}