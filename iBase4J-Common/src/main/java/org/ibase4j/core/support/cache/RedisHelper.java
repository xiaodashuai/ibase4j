package org.ibase4j.core.support.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 *
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:17:22
 */
public final class RedisHelper implements CacheManager {
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    private RedisConnection redisConnection;
    private Logger logger = LogManager.getLogger();
    private final Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisConnection = RedisConnectionUtils.getConnection(redisTemplate.getConnectionFactory());
        CacheUtil.setCacheManager(this);
    }

    @Override
    public final Object get(final String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public final Object get(final String key, int seconds) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public final Set<Object> getAll(final String pattern) {
        Set<Object> values = InstanceUtil.newHashSet();
        Set<Serializable> keys = redisTemplate.keys(pattern);
        for (Serializable key : keys) {
            values.add(redisTemplate.opsForValue().get(key));
        }
        return values;
    }

    public final Set<Object> getCustAll(final String pattern) {
        Set<Object> values = InstanceUtil.newHashSet();
        Set<Serializable> keys = redisTemplate.keys(pattern);
        for (Serializable key : keys) {
            values.add(get(key.toString()));
        }
        return values;
    }

    public final void deleteHistoryState(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    @Override
    public final void set(final String key, final Serializable value, int seconds) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public final void set(final String key, final Serializable value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public final Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public final void del(final String key) {
        redisTemplate.delete(key);
    }

    @Override
    public final void delAll(final String pattern) {
        redisTemplate.delete(redisTemplate.keys(pattern));
    }

    @Override
    public final String type(final String key) {
        return redisTemplate.type(key).getClass().getName();
    }
    @Override
    public final Boolean expire(final String key, final int seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
    @Override
    public final Boolean expireAt(final String key, final long unixTime) {
        return redisTemplate.expireAt(key, new Date(unixTime));
    }

    @Override
    public final Long ttl(final String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public final void setrange(final String key, final long offset, final String value) {
        redisTemplate.boundValueOps(key).set(value, offset);
    }

    public final String getrange(final String key, final long startOffset, final long endOffset) {
        return redisTemplate.boundValueOps(key).get(startOffset, endOffset);
    }

    @Override
    public final Object getSet(final String key, final Serializable value) {
        return redisTemplate.boundValueOps(key).getAndSet(value);
    }

    @Override
    public boolean setnx(String key, Serializable value) {
        return redisConnection.setNX(rawKey(key), rawValue(value));
    }

    @Override
    public boolean lock(String key) {
        return redisConnection.setNX(rawKey(key), rawValue("0"));
    }

    @Override
    public void unlock(String key) {
        del(key);
    }

    @Override
    public void hset(String key, Serializable field, Serializable value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    @Override
    public Object hget(String key, Serializable field) {
        return redisTemplate.boundHashOps(key).get(field);
    }

    @Override
    public void hdel(String key, Serializable field) {
        redisTemplate.boundHashOps(key).delete(field);
    }

    @Override
    public void sadd(String key, Serializable value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    @Override
    public Set<?> sall(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    @Override
    public boolean sdel(String key, Serializable value) {
        return redisTemplate.boundSetOps(key).remove(value) == 1;
    }

    @Override
    public void putMap(String key, String field, Serializable value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public Object getMap(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public boolean unlock(String key, String requestId) {
        List<Object> list = InstanceUtil.newArrayList();
        list.add(key);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor(redisTemplate);
        return (boolean) scriptExecutor.execute(new DefaultRedisScript<Boolean>(script, Boolean.class), list, requestId);

    }

    @Override
    public boolean lock(final String key, final String requestId, final int seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(rawKey(key), rawValue(requestId), Expiration.seconds(seconds),
                        RedisStringCommands.SetOption.ifAbsent());
                return connection.exists(rawKey(key));
            }
        });
    }


    private byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        if (keySerializer == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return keySerializer.serialize(key);
    }

    private byte[] rawValue(Object value) {
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        if (valueSerializer == null && value instanceof byte[]) {
            return (byte[]) value;
        }
        return valueSerializer.serialize(value);
    }


}