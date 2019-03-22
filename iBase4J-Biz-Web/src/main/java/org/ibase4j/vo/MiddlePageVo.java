package org.ibase4j.vo;

import org.ibase4j.model.SysDic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：判断角色是否被选中
 * 
 * @author lianwenhao
 * @version 1.0
 */
public class MiddlePageVo implements Serializable{
	private Long mid;
	private String name;
	private String auditName;//选项内容
	private String code;//选项类型
	private String codeText;//选项描述

    //字典选项
	private List<SysDic> dicList  = new ArrayList<>();

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    public List<SysDic> getDicList() {
        return dicList;
    }

    public void setDicList(List<SysDic> dicList) {
        this.dicList = dicList;
    }
}
