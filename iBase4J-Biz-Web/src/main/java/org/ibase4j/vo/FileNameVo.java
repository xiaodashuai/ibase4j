package org.ibase4j.vo;

import java.io.Serializable;

/**
 * 功能：判断角色是否被选中
 * 
 * @author lianwenhao
 * @version 1.0
 */
public class FileNameVo implements Serializable{
	private String fileName;//文件名

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
