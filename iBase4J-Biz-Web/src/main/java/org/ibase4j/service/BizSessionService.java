package org.ibase4j.service;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.shiro.RedisManager;
import org.ibase4j.core.support.shiro.exception.SerializationException;
import org.ibase4j.core.support.shiro.serializer.StringSerializer;
import org.ibase4j.model.BizSession;
import org.ibase4j.provider.IBizSessionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会话管理
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:08:32
 */
@Service
public class BizSessionService {
    private static Logger logger = LoggerFactory.getLogger(BizSessionService.class);

    @Autowired
    private IBizSessionProvider bizSessionProvider;
    //	@Reference
//	private RedisOperationsSessionRepository sessionRepository;
    @Autowired
    private RedisManager redisManager;

    public Page<?> query(Map<String, Object> params) {
        return bizSessionProvider.query(params);
    }

    /**
     * 删除会话
     */
    public void deleteByAccount(String account, String currentSessionId) {
        Assert.isNotBlank(account, "ACCOUNT");
        List<String> sessionIds = bizSessionProvider.querySessionIdByAccount(account);
        if (sessionIds != null) {
            for (String sessionId : sessionIds) {
                bizSessionProvider.deleteBySessionId(sessionId);
                if (!sessionId.equals(currentSessionId)) {
//					sessionRepository.delete(sessionId);
//					sessionRepository.cleanupExpiredSessions();
                }
            }
        }
    }

    /**
     * 删除会话
     */
    public void delete(Long id) {
        Assert.notNull(id, "ID");
        BizSession sysSession = bizSessionProvider.queryById(id);
        if (sysSession != null) {
            bizSessionProvider.delete(id);
//			sessionRepository.delete(sysSession.getSessionId());
        }
    }
    /**
     * 删除redis中 "iBase4J:bizSession:数据BIZ_SESSION sessionID" 这种键的会话
     */
    public void deleteFromRedis(Long id) {
        //删除redis缓存中的session信息
        try {
            redisManager.del(new StringSerializer().serialize("iBase4J:bizSession:" + id));
        } catch (SerializationException e) {
            logger.error("用户退出  删除后台缓存信息异常.  bizSessionId=" + id);
        }
    }

    /**
     * 更新会话
     */
    public void update(BizSession record) {
        bizSessionProvider.update(record);
    }

    /**
     * 删除会话
     */
    public void deleteBySessionId(String sessionId) {
        bizSessionProvider.deleteBySessionId(sessionId);
    }

    public Long queryByAccount(String account) {
        return bizSessionProvider.queryIdByAccount(account);
    }

    public BizSession queryBySessionId(String id) {
        return bizSessionProvider.queryOneBySessionId(id);
    }

    public BizSession queryOneByAccount(String account) {
        return bizSessionProvider.queryOneByAccount(account);
    }
}
