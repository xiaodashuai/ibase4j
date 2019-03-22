package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.mapper.SysSessionMapper;
import org.ibase4j.model.SysSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysSession")
@Service(interfaceClass = ISysSessionProvider.class)
public class SysSessionProviderImpl extends BaseProviderImpl<SysSession> implements ISysSessionProvider {

    @Autowired
    private SysSessionMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysSession update(SysSession record) {
        Assert.notNull(record,"record is not null");
        logger.debug("========SysSessionProviderImpl====update===record=={}", JSON.toJSONString(record));
        if (record.getId() == null) {
            record.setUpdateTime(new Date());
            Long id = mapper.queryBySessionId(record.getSessionId());
            if (id != null) {
                record.setId(id);
                mapper.updateById(record);
            } else {
                record.setCreateTime(new Date());
                mapper.insert(record);
            }
        } else {
            mapper.updateById(record);
        }
        return record;
    }

    @Override
    public void delete(final Long id) {
        mapper.deleteById(id);
    }

    // 系统触发,由系统自动管理缓存
    @Override
    public void deleteBySessionId(final String sessionId) {
        mapper.deleteBySessionId(sessionId);
    }

    @Override
    public SysSession querySysSessionBySessionId(String sessionId) {
        return mapper.querySysSessionBySessionId(sessionId);
    }

    @Override
    public List<String> querySessionIdByAccount(String account) {
        return mapper.querySessionIdByAccount(account);
    }


    @Override
    public Long queryIdByAccount(String account) {
        return mapper.queryIdByAccount(account);
    }

    @Override
    public Long queryPrimaryIdByAccountAndSessionId(String account, String sessionId) {
        return mapper.queryPrimaryIdByAccountAndSessionId(account, sessionId);
    }

    @Override
    public void cleanExpiredSessions() {
        Long sessionExpireTime = Long.parseLong(PropertiesUtil.getString("session.expireTime"));
        String key = "spring:session:" + PropertiesUtil.getString("sys.session.redis.namespace") + ":sessions:expires:";
        Map<String, Object> columnMap = InstanceUtil.newHashMap();
        columnMap.put("ENABLE_", 1);
        List<SysSession> sessions = mapper.selectByMap(columnMap);
        for (SysSession sysSession : sessions) {
            logger.info("检查SESSION : {}", sysSession.getSessionId());
            if (!CacheUtil.getCache().exists(key + sysSession.getSessionId())) {
                logger.info("修改SESSION : {}", sysSession.getSessionId());
                Date startTime = sysSession.getStartTime();
                Date updateTime = sysSession.getUpdateTime();
                if (!StringUtils.isEmpty(startTime)) {
                    if (!StringUtils.isEmpty(updateTime)) {
                        //如果更新时间 已经过期sessionExpireTime毫秒
                        if (System.currentTimeMillis() - updateTime.getTime() > sessionExpireTime) {
                            sysSessionUpdate(sysSession);
                        }
                    } else if (System.currentTimeMillis() - startTime.getTime() > sessionExpireTime) {
                        sysSessionUpdate(sysSession);
                    }
                }
            }
        }
    }
    private void sysSessionUpdate(SysSession sysSession) {
        sysSession.setSessionState(5);
        sysSession.setEnable(0);
        sysSession.setRemark("定时任务修改session状态");
        sysSession.setUpdateTime(new Date());
        mapper.updateById(sysSession);
        logger.info("修改SESSION成功 : {}", sysSession.getSessionId());
    }

}