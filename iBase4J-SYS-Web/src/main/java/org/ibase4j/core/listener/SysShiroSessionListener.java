package org.ibase4j.core.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.ibase4j.core.support.shiro.RedisSessionDAO;
import org.ibase4j.model.SysSession;
import org.ibase4j.service.SysSessionService;
import org.ibase4j.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SysShiroSessionListener extends SessionListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SysShiroSessionListener.class);

    private RedisSessionDAO sessionDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSessionService sysSessionService;

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
//        try {
//            BizSession bizSession = sessionService.queryBySessionId(session.getId().toString());
//            //BizWebUtil.getCurrentUser() 只有管理员踢人时有用
//            //1代表自己退出
//            bizSession.setUpdateBy(1L);
//            bizSession.setUpdateTime(new Date());
//            bizSession.setEnable(0);
//            bizSession.setSessionState(1);
//            sessionService.update(bizSession);
//            sessionDao.delete(session);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onExpiration(Session session) {

//        Long userId = sessionDao.getUserIdBySession(session);
//        sessionDao.delSessionUserRelation(userId);
        //会话过期时触发
        logger.warn("ShiroSessionListener session {} 过期", session.getId());
        try {
            SysSession sysSession = sysSessionService.queryBySessionId(session.getId().toString());
            if (sysSession != null && !"".equals(sysSession)) {
                //0L 定时任务编号
                if (sysSession.getEnable() == 1) {
                    sysSession.setUpdateBy(0L);
                    sysSession.setUpdateTime(new Date());
                    sysSession.setEnable(0);
                    sysSession.setSessionState(2);
                    //数据库修改状态
                    sysSessionService.update(sysSession);
                }
                //redis缓存 删除
                sessionDao.delete(session);
            }
        } catch (Exception e) {
            logger.warn("SYS_ShiroSessionListener session {} 过期处理异常", session.getId());
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
