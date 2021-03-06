package org.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.ibase4j.core.Constants;
import org.ibase4j.core.support.http.SessionUser;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Web层辅助类
 * 
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:19:28
 */
public final class WebUtil {
	private WebUtil() {
	}

	private static Logger logger = LogManager.getLogger();

	/**
	 * 获取指定Cookie的值
	 * 
	 * @param cookies
	 *            cookie集合
	 * @param cookieName
	 *            cookie名字
	 * @param defaultValue
	 *            缺省值
	 * @return
	 */
	public static final String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
		Cookie cookie = WebUtils.getCookie(request, cookieName);
		if (cookie == null) {
			return defaultValue;
		}
		return cookie.getValue();
	}

	/** 保存当前用户 */
	public static final void saveCurrentUser(Object user) {
		setSession(Constants.CURRENT_USER, user);
	}

	/** 保存当前用户 */
	public static final void saveCurrentUser(HttpServletRequest request, Object user) {
		setSession(request, Constants.CURRENT_USER, user);
	}

	/** 获取当前用户 */
	public static final SessionUser getCurrentUser(HttpServletRequest request) {
		return (SessionUser)request.getAttribute(Constants.CURRENT_USER);
	}

	/** 保存当前用户 */
	public static final void saveCurrentUser(HttpServletRequest request, SessionUser user) {
		request.setAttribute(Constants.CURRENT_USER, user);
	}

	/** 获取当前用户 */
	public static final Long getCurrentUser() {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			try {
				Session session = currentUser.getSession();
				if (null != session) {
					return (Long) session.getAttribute(Constants.CURRENT_USER);
				}
			} catch (InvalidSessionException e) {
				logger.error(e);
			}
		}
		return null;
	}

	/** 获取当前用户 */
//	public static final Object getCurrentUser(HttpServletRequest request) {
//		try {
//			HttpSession session = request.getSession();
//			if (null != session) {
//				return session.getAttribute(Constants.CURRENT_USER);
//			}
//		} catch (InvalidSessionException e) {
//			logger.error(e);
//		}
//		return null;
//	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	public static final void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	/**
	 * 将一些数据放到ShiroSession中,以便于其它地方使用
	 * 
	 * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	public static final void setSession(HttpServletRequest request, String key, Object value) {
		HttpSession session = request.getSession();
		if (null != session) {
			session.setAttribute(key, value);
		}
	}

	/** 移除当前用户 */
	public static final void removeCurrentUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Constants.CURRENT_USER);
	}

	/**
	 * 获得国际化信息
	 * 
	 * @param key
	 *            键
	 * @param request
	 * @return
	 */
	public static final String getApplicationResource(String key, HttpServletRequest request) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("ApplicationResources", request.getLocale());
		return resourceBundle.getString(key);
	}

	/**
	 * 获得参数Map
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, Object> getParameterMap(HttpServletRequest request) {
		return WebUtils.getParametersStartingWith(request, null);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameter(HttpServletRequest request) {
		String str, wholeStr = "";
		try {
			BufferedReader br = request.getReader();
			while ((str = br.readLine()) != null) {
				wholeStr += str;
			}
			if (StringUtils.isNotBlank(wholeStr)) {
				return JSON.parseObject(wholeStr, Map.class);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return getParameterMap(request);
	}

	public static <T> T getParameter(HttpServletRequest request, Class<T> cls) {
		String str, wholeStr = "";
		try {
			BufferedReader br = request.getReader();
			while ((str = br.readLine()) != null) {
				wholeStr += str;
			}
			if (StringUtils.isNotBlank(wholeStr)) {
				return JSON.parseObject(wholeStr, cls);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return Request2ModelUtil.covert(cls, request);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getParameters(HttpServletRequest request, Class<T> cls) {
		String str, wholeStr = "";
		try {
			BufferedReader br = request.getReader();
			while ((str = br.readLine()) != null) {
				wholeStr += str;
			}
			if (StringUtils.isNotBlank(wholeStr)) {
				return JSON.parseObject(wholeStr, List.class);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return Request2ListUtil.covert(cls, request);
	}

	/** 获取客户端IP */
	public static final String getHost(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.indexOf(",") > 0) {
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			ip = ip.substring(0, ip.indexOf(","));
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
			InetAddress inet = null;
			try { // 根据网卡取本机配置的IP
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				logger.error(Constants.Exception_Head, e);
			}
			ip = inet.getHostAddress();
		}
		logger.debug("getRemoteAddr ip: " + ip);
		return ip;
	}
	
	
	/**
	 * 转换字符集编码为指定的编码
	 * @param echo
	 * @return
	 */
	public static String convertCharsetToCharset(String echo,String charset){
		try {
			if(StringUtils.isNotBlank(echo)){
				String echostr = new String(echo.getBytes("ISO-8859-1"),charset);
				return echostr;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("convert charset error:"+e.getMessage());
			return null;
		}
		return null;
	}
	
	/**
	 * 转换字符集编码为gbk
	 * @param echo
	 * @return
	 */
	public static String convertCharsetToGBK(String echo){
		try {
			if(StringUtils.isNotBlank(echo)){
				String echostr = new String(echo.getBytes("ISO-8859-1"),"GBK");
				return echostr;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("convert charset error:"+e.getMessage());
			return null;
		}
		return null;
	}
	
	/**
	 * 转换字符集编码为UTF-8
	 * @param echo
	 * @return
	 */
	public static String convertCharsetToUtf8(String echo){
		try {
			if(StringUtils.isNotBlank(echo)){
				String echostr = new String(echo.getBytes("ISO-8859-1"),"UTF-8");
				return echostr;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("convert charset error:"+e.getMessage());
			return null;
		}
		return null;
	}
}
