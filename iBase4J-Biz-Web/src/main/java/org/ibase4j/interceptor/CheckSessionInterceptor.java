package org.ibase4j.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.ibase4j.core.interceptor.BaseInterceptor;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.model.BizSession;
import org.ibase4j.service.BizSessionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @program: iBase4J
 * @description: 拦截器修改BizSession的更新时间
 * @author: lzy
 * @create: 2018-11-14 15:10
 */
public class CheckSessionInterceptor extends BaseInterceptor {
    protected Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BizSessionService bizSessionService;
    Long sessionExpireTime = Long.parseLong(PropertiesUtil.getString("session.expireTime"));
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //对所有的请求放行  只是修改一下数据库session的更新时间
        //1.获取shiroSession   不能这样获取啊 如果 能拿到cookie中sessionId 也可以  因为没有就会创建一个
        String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        if (sessionId != null && !"".equals(sessionId)) {
            //根据sessionId 去查数据库 如果有就更新  没有就放行
            BizSession bizSession = bizSessionService.queryBySessionId(sessionId);
            if (bizSession != null && !"".equals(bizSession)) {

                bizSession.setUpdateTime(new Date(System.currentTimeMillis()+sessionExpireTime));
                try {
                    bizSessionService.update(bizSession);
                    logger.info("拦截器刷新BIZ_SESSION更新时间成功 : {}", bizSession.getSessionId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("拦截器刷新BIZ_SESSION更新时间 放行,请求Url:" + httpServletRequest.getRequestURI());
        return true;
    }
}
