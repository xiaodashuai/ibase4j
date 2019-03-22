package org.ibase4j.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import org.ibase4j.core.base.BaseModel;

import java.io.Serializable;


@TableName("BIZ_FILE")
public class BizFile extends BaseModel implements Serializable {

    @TableField("BIZ_CODE")
    private String bizCode;

    @TableField("BIZ_TYPE")
    private String bizType;

    @TableField("PRODUCT_")
    private String product;

    @TableField("FIELD_NAME")
    private String fieldName;

    @TableField("FILE_NAME")
    private String fileName;

    @TableField("REAL_NAME")
    private String realName;

    @TableField("FILE_TYPE")
    private String fileType;

    @TableField("URL_")
    private String URL;

    @TableField("FILE_SIZE")
    private Long size_;

    @TableField("EXT_")
    private String ext;


    public BizFile() {
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Long getSize_() {
        return size_;
    }

    public void setSize_(Long size_) {
        this.size_ = size_;
    }
    
    //页面专用
    public Long getSize() {
        return size_;
    }
    //页面专用
    public void setSize(Long size) {
        this.size_ = size;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("bizCode", bizCode)
                .add("bizType", bizType)
                .add("product", product)
                .add("fieldName", fieldName)
                .add("fileName", fileName)
                .add("realName", realName)
                .add("fileType", fileType)
                .add("URL", URL)
                .add("size_", size_)
                .add("ext", ext)
                .toString();
    }


}

