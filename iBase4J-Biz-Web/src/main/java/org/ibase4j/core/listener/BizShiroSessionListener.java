package org.ibase4j.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.ibase4j.core.support.shiro.RedisSessionDAO;
import org.ibase4j.model.BizSession;
import org.ibase4j.service.BizSessionService;
import org.ibase4j.service.BizUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class BizShiroSessionListener extends SessionListenerAdapter {
    protected Logger logger = LogManager.getLogger(this.getClass());

    private RedisSessionDAO sessionDao;
    @Autowired
    private BizUserService bizUserService;
    @Autowired
    private BizSessionService sessionService;

    public void onStart(Session session) {
        // 会话创建时触发
        logger.warn("ShiroSessionListener session {} 被创建", session.getId());
//        Long userId = BizWebUtil.getCurrentUser();
//        logger.debug("=================BizShiroSessionListener=====onStart=====userId={}",userId);
//        SysUser sysUser = bizUserService.queryById(userId);
//        BizSession bizSession = new BizSession();
//        bizSession.setAccount(sysUser.getAccount());
//        bizSession.setStartTime(new Date());
////        bizSession.setIp(BizWebUtil.getHost(request));
//        bizSession.setSessionId(session.getId().toString());
//        bizSession.setCreateBy(sysUser.getId());
//        bizSession.setCreateTime(new Date());
//        sessionService.update(bizSession);
    }


    @Override
    public void onStop(Session session) {
//         会话被停止时触发
        logger.warn("ShiroSessionListener session {} 被销毁", session.getId());
        try {

            BizSession bizSession = sessionService.queryBySessionId(session.getId().toString());
            if(bizSession!=null) {
                //BizWebUtil.getCurrentUser() 只有管理员踢人时有用
                //1代表自己退出
                bizSession.setUpdateBy(1L);
                bizSession.setUpdateTime(new Date());
                bizSession.setEnable(0);
                bizSession.setSessionState(1);
                sessionService.update(bizSession);
                sessionDao.delete(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("===ShiroSessionListener==session=",e.getStackTrace());
        }
    }

    @Override
    public void onExpiration(Session session) {

//        Long userId = sessionDao.getUserIdBySession(session);
//        sessionDao.delSessionUserRelation(userId);
        //会话过期时触发
        logger.warn("ShiroSessionListener session {} 过期", session.getId());
        try {
            BizSession bizSession = sessionService.queryBySessionId(session.getId().toString());
            if (bizSession != null) {
                //定时执行的时候 没有用户会报异常
                //org.apache.shiro.UnavailableSecurityManagerException:
                // No SecurityManager accessible to the calling code,
                // either bound to the org.apache.shiro.util.ThreadContext
                // or as a vm static singleton.  This is an invalid application configuration.
//                bizSession.setUpdateBy(BizWebUtil.getCurrentUser());
                //0L 定时任务编号
                if (bizSession.getEnable() == 1) {
                    bizSession.setUpdateBy(0L);
                    bizSession.setUpdateTime(new Date());
                    bizSession.setEnable(0);
                    bizSession.setSessionState(2);
                    sessionService.update(bizSession);
                }
                sessionDao.delete(session);
            }
        } catch (Exception e) {
            logger.warn("ShiroSessionListener session {} 过期处理异常", session.getId());
            e.printStackTrace();
        }
    }

    public RedisSessionDAO getSessionDao() {
        return sessionDao;
    }

    public void setSessionDao(RedisSessionDAO sessionDao) {
        this.sessionDao = sessionDao;
    }
}
