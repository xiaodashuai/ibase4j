package org.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.core.util.CacheUtil;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 会话监听器
 * 
 * @author ShenHuaJie
 * @version $Id: SessionListener.java, v 0.1 2014年3月28日 上午9:06:12 ShenHuaJie Exp
 */
public class SessionListener implements HttpSessionListener {
	private Logger logger = LogManager.getLogger(SessionListener.class);


	@Override
    public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		session.setAttribute(SysConstant.SYSWEBTHEME, "default");
		logger.info("创建了一个Session连接:[" + session.getId() + "]");
		setAllUserNumber(1);
	}


	@Override
    public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		if (getAllUserNumber() > 0) {
			logger.info("销毁了一个Session连接:[" + session.getId() + "]");
		}
		session.removeAttribute(SysConstant.SYS_CURRENT_USER);
		setAllUserNumber(-1);
	}

	private void setAllUserNumber(int n) {
		Integer number = getAllUserNumber() + n;
		if (number >= 0) {
			logger.info("用户数：" + number);
			CacheUtil.getCache().set(SysConstant.ALLUSER_NUMBER, number, 60 * 60 * 24);
		}
	}

	/** 获取在线用户数量 */
	public static Integer getAllUserNumber() {
		Integer v = (Integer) CacheUtil.getCache().get(SysConstant.ALLUSER_NUMBER);
		if (v != null) {
			return v;
		}
		return 0;
	}
}
