/*
* Copyright (C) 2018 Happy Fish / Tom.Chen
*
* zTree节点，构造tree节点模型
* Licensed same as jquery - MIT License
 * http://www.opensource.org/licenses/mit-license.php
*/
package org.ibase4j.model;

import java.io.Serializable;

/**
 * 功能：构造zTree节点
 * 
 * @author czm
 * @version 1.0
 */
public class ZTreeRadioNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 856335042462453160L;
	private String nId;
	private String pId;// 父id
	private String name;// 节点名
	private boolean open;// 是否打开状态
	private boolean nocheck;// 是否选中状态


	public String getnId() {
		return nId;
	}

	public void setnId(String nId) {
		this.nId = nId;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ZTreeRadioNode() {
	}

	@Override
	public String toString() {
		return "ZTreeRadioNode{" +
				"nId=" + nId +
				", pId=" + pId +
				", name='" + name + '\'' +
				", open=" + open +
				", nocheck=" + nocheck +
				'}';
	}
}
