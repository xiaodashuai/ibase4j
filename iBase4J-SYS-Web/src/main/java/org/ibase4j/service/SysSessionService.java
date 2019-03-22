package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.support.shiro.RedisManager;
import org.ibase4j.core.support.shiro.exception.SerializationException;
import org.ibase4j.core.support.shiro.serializer.StringSerializer;
import org.ibase4j.core.support.Assert;
import org.ibase4j.model.BizSession;
import org.ibase4j.model.SysSession;
import org.ibase4j.provider.IBizSessionProvider;
import org.ibase4j.provider.ISysSessionProvider;
import org.ibase4j.provider.WFLogsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会话管理
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:08:32
 */
@Service
public class SysSessionService {

    private static Logger logger = LoggerFactory.getLogger(SysSessionService.class);

    @Autowired
    private ISysSessionProvider sysSessionProvider;
    @Autowired
    private IBizSessionProvider bizSessionProvider;
    @Autowired
    private RedisManager redisManager;

    @Reference
    private WFLogsProvider wFLogsProvider;

    public Page<?> query(Map<String, Object> params) {
        return sysSessionProvider.query(params);
    }

    public Page<?> queryBizList(Map<String, Object> params) {
        return bizSessionProvider.query(params);
    }

    /**
     * 根据后台管理sessionId 获取后台管理session信息
     *
     * @param sessionId
     * @return
     */
    public SysSession queryBySessionId(String sessionId) {
        return sysSessionProvider.querySysSessionBySessionId(sessionId);
    }

    public Long queryByAccount(String account) {
        return sysSessionProvider.queryIdByAccount(account);
    }

    public Long queryPrimaryIdByAccountAndSessionId(String account, String sessionId) {
        return sysSessionProvider.queryPrimaryIdByAccountAndSessionId(account, sessionId);
    }

    /**
     * 删除会话
     */
    public void deleteByAccount(String account, String currentSessionId) {
        Assert.isNotBlank(account, "ACCOUNT");
        List<String> sessionIds = sysSessionProvider.querySessionIdByAccount(account);
        if (sessionIds != null) {
            for (String sessionId : sessionIds) {
                sysSessionProvider.deleteBySessionId(sessionId);
                if (!sessionId.equals(currentSessionId)) {
//                    sessionRepository.delete(sessionId);
//                    sessionRepository.cleanupExpiredSessions();
                }
            }
        } else {
            logger.debug("没找到会话信息");
        }
    }

    /**
     * 删除会话
     */
    public void delete(Long id) {
        Assert.notNull(id, "ID");
        SysSession sysSession = sysSessionProvider.queryById(id);
        if (sysSession != null) {
            sysSessionProvider.delete(id);
//            sessionRepository.delete(sysSession.getSessionId());
        }
    }

    /**
     * 删除会话
     */
    public void deleteBizSession(Long id, Long userId) {
        Assert.notNull(id, "ID");
        BizSession bizSession = bizSessionProvider.queryById(id);
        if (bizSession != null) {
            bizSession.setEnable(0);
            bizSession.setUpdateBy(userId);
            bizSession.setUpdateTime(new Date());
            bizSession.setSessionState(3);
            bizSessionProvider.update(bizSession);
            //删除redis缓存中的session信息
//			sessionRepository.delete(bizSession.getSessionId());
            try {
                redisManager.del(new StringSerializer().serialize("shiro:session:" + bizSession.getSessionId()));
                //删除redis中对应的  "iBase4J:bizSession:数据BIZ_SESSION sessionID"
                redisManager.del(new StringSerializer().serialize("iBase4J:bizSession:" + bizSession.getId()));

            } catch (SerializationException e) {
                logger.error("踢出用户  删除缓存信息异常.  bizSessionId=" + bizSession.getId());
            }
            //工作流退出
            /** 工作引擎退出系统 **/
            Long bizUserId = null;
            try {
                //根据account 查询出用户Id 工作流根据用户Id 退出
                String account = bizSession.getAccount();
                bizUserId = sysSessionProvider.queryIdByAccount(account);
                wFLogsProvider.exits(bizUserId.toString());
            } catch (Exception e) {
                logger.error("踢出用户 退出工作流异常 .  bizUserId=" + bizUserId);
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新会话
     */
    public void update(SysSession record) {
        sysSessionProvider.update(record);
    }

    /**
     * 删除会话
     */
    public void deleteBySessionId(String sessionId) {
        sysSessionProvider.deleteBySessionId(sessionId);
    }

}
