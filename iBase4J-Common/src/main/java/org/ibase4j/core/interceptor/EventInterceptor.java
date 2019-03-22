package org.ibase4j.core.interceptor;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.Constants;
import org.ibase4j.core.support.ISysEventService;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.SysEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 日志拦截器
 * 
 * @author ShenHuaJie
 * @version 2016年6月14日 下午6:18:46
 */
public class EventInterceptor extends BaseInterceptor {

	private final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 20, 5, TimeUnit.SECONDS,
			new SynchronousQueue<Runnable>());

	@Autowired
	private ISysEventService sysEventService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 开始时间（该数据只有当前请求的线程可见）
		startTimeThreadLocal.set(System.currentTimeMillis());
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, HttpServletResponse response, Object handler,
			final Exception ex) throws Exception {
		String account = "";
		String methodStr = null;
		String userAgent="";
		String clientIp="";
		Long uid=0L;
		final Long startTime = startTimeThreadLocal.get();
		final Long endTime = System.currentTimeMillis();
		String path = request.getServletPath();
		// 保存日志
		if (handler instanceof HandlerMethod) {
			if (!path.contains("/read/") && !path.contains("/unauthorized") && !path.contains("/forbidden") && !path.contains("/logout")) {
				userAgent = (String) request.getSession().getAttribute(Constants.USER_AGENT);
				clientIp = (String) request.getSession().getAttribute(Constants.USER_IP);
				uid = WebUtil.getCurrentUser();

				final SysEvent record = new SysEvent();
				record.setClientHost(clientIp);
				record.setUserAgent(userAgent);
				final String msg = (String)request.getAttribute("msg");
				try {
					HandlerMethod handlerMethod = (HandlerMethod) handler;
					ApiOperation apiOperation = handlerMethod.getMethod().getAnnotation(ApiOperation.class);
					methodStr = getLg(apiOperation.value());
					if(msg != null){
						String[] split = msg.split("\\[")[1].split("\\]");
						account = split[0];
					}
					record.setName(methodStr);
					record.setAccount(account);
					record.setCreateTime(new Date());
					if (uid != null) {
						record.setCreateBy(Long.parseLong(uid.toString()));
						record.setUpdateBy(Long.parseLong(uid.toString()));
					}
				} catch (Exception e) {
					logger.error("登录日志错误", e);
				}
				if (StringUtils.isNotBlank(methodStr)) {
					executor.submit(new Runnable() {
						@Override
						public void run() {
							try { // 保存操作
								record.setRemark("系统登录!");
								sysEventService.update(record);
							} catch (Exception e) {
								logger.error("Save event log cause error :", e);
							}
						}
					});
				}
			} else if (path.contains("/unauthorized")) {
				logger.warn("用户[{}]没有登录", clientIp + "@" + userAgent);
			} else if (path.contains("/forbidden")) {
				logger.warn("用户[{}]没有权限", WebUtil.getCurrentUser() + "@" + clientIp + "@" + userAgent);
			} else {
				logger.info(uid + "@" + path + "@" + clientIp + userAgent);
			}
		}
		// 内存信息
		if (logger.isDebugEnabled()) {
			String message = "开始时间: {}; 结束时间: {}; 耗时: {}s; URI: {}; ";
			logger.debug(message, DateUtil.format(startTime, "HH:mm:ss.SSS"), DateUtil.format(endTime, "HH:mm:ss.SSS"),
					(endTime - startTime) / 1000.00, path);
		}
		super.afterCompletion(request, response, handler, ex);
	}

	private String getLg(String str) {
		if ("用户登录".equals(str) || "用户登出".equals(str)) {
			return str;
		} else {
			return null;
		}
	}
}
