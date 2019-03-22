package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.ibase4j.mapper.BizSessionMapper;
import org.ibase4j.model.BizSession;
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
@CacheConfig(cacheNames = "bizSession")
@Service(interfaceClass = IBizSessionProvider.class)
public class BizSessionProviderImpl extends BaseProviderImpl<BizSession> implements IBizSessionProvider {
    @Autowired
    private BizSessionMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BizSession update(BizSession record) {
        Assert.notNull(record,"record is not null");
        logger.debug("========BizSessionProviderImpl====update===record=={}", JSON.toJSONString(record));
        if (record.getId() == null) {
            record.setUpdateTime(new Date());
            Long id = mapper.queryBySessionId(record.getSessionId());
            if (id != null) {
                //原纪录没有Id 要添加上
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

    @Override
    public void deleteBySessionId(final String sessionId) {
        mapper.deleteBySessionId(sessionId);
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
    public BizSession queryOneBySessionId(String id) {
        return  mapper.queryOneBySessionId(id);
    }

    @Override
    public BizSession queryOneByAccount(String account) {
        return mapper.queryOneByAccount(account);
    }

    @Override
    public void cleanExpiredSessions() {
        Long sessionExpireTime = Long.parseLong(PropertiesUtil.getString("session.expireTime"));
        String key = "spring:session:" + PropertiesUtil.getString("biz.session.redis.namespace") + ":sessions:expires:";
        Map<String, Object> columnMap = InstanceUtil.newHashMap();
        columnMap.put("ENABLE_", 1);
        List<BizSession> sessions = mapper.selectByMap(columnMap);
        for (BizSession bizSession : sessions) {
            logger.info("检查BIZ_SESSION : {}", bizSession.getSessionId());
            if (!CacheUtil.getCache().exists(key + bizSession.getSessionId())) {
                logger.info("修改BIZ_SESSION : {}", bizSession.getSessionId());
                Date startTime = bizSession.getStartTime();
                Date updateTime = bizSession.getUpdateTime();
                if (!StringUtils.isEmpty(startTime)) {
                    if (!StringUtils.isEmpty(updateTime)) {
                        //如果更新时间 已经过期sessionExpireTime毫秒
                        if (System.currentTimeMillis() - updateTime.getTime() > sessionExpireTime) {
                            bizSessionUpdate(bizSession);
                        }
                    } else if (System.currentTimeMillis() - startTime.getTime() > sessionExpireTime) {
                        bizSessionUpdate(bizSession);
                    }
                }
            }
        }
    }

    private void bizSessionUpdate(BizSession bizSession) {
        bizSession.setSessionState(5);
        bizSession.setEnable(0);
        bizSession.setRemark("定时任务修改session状态");
        bizSession.setUpdateTime(new Date());
        mapper.updateById(bizSession);
        logger.info("修改BIZ_SESSION成功 : {}", bizSession.getSessionId());
    }


}