package org.ibase4j.core.config;

public class SysConstant {
	/**
	 * 1代表前台用户
	 * 2带包后台用户
	 */
	public static final String BIZUSER = "1";
	public static final String SYSUSER = "2";
	public static final String ADMINUSER = "3";
	/**
	 * 1代表前台岗位
	 * 2代表后台岗位
	 */
	public static final String BIZROLE = "1";
	public static final String SYSROLE = "2";
	
	/** 是否可用1：是 ***/
	public static final int ENABLE_YES = 1;
	/** 是否可用0：否 ***/
	public static final int ENABLE_NO = 0;

    public static final String SYS_CURRENT_USER = "SYS_CURRENT_USER";

	public static final String SYSWEBTHEME = "SysWebTheme";

	/** 缓存命名空间 */
	public static final String CACHE_SYS_NAMESPACE = "iBase4J:sys:";
	/** 在线用户数量 */
	public static final String ALLUSER_NUMBER = "SYSTEM:" + CACHE_SYS_NAMESPACE + "ALLUSER_NUMBER";


}
