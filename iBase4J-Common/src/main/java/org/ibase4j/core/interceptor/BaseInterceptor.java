package org.ibase4j.core.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器基类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:31
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LogManager.getLogger(this.getClass());
    private BaseInterceptor[] nextInterceptor;

    public void setNextInterceptor(BaseInterceptor... nextInterceptor) {
        this.nextInterceptor = nextInterceptor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (nextInterceptor == null) {
            return true;
        }
        for (int i = 0; i < nextInterceptor.length; i++) {
            if (!nextInterceptor[i].preHandle(request, response, handler)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        if (nextInterceptor != null) {
            for (int i = 0; i < nextInterceptor.length; i++) {
                nextInterceptor[i].postHandle(request, response, handler, modelAndView);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        if (nextInterceptor != null) {
            for (int i = 0; i < nextInterceptor.length; i++) {
                nextInterceptor[i].afterCompletion(request, response, handler, ex);
            }
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (nextInterceptor != null) {
            for (int i = 0; i < nextInterceptor.length; i++) {
                nextInterceptor[i].afterConcurrentHandlingStarted(request, response, handler);
            }
        }
    }
}
