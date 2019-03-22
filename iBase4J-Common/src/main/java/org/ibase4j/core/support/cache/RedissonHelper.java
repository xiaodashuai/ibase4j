package org.ibase4j.core.support.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.ExceptionUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.PropertiesUtil;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 */
public class RedissonHelper implements CacheManager {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private RedissonClient redissonClient;


    private final Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        CacheUtil.setLockManager(this);
    }

    private RBucket<Object> getRedisBucket(String key) {
        return redissonClient.getBucket(key);
    }

    @Override
    public final Object get(final String key) {
        RBucket<Object> temp = getRedisBucket(key);
        return temp.get();
    }

    @Override
    public Object get(String key, int seconds) {
        RBucket<Object> temp = getRedisBucket(key);
        return temp.get();
    }

    @Override
    public final void set(final String key, final Serializable value) {
        RBucket<Object> temp = getRedisBucket(key);
        temp.set(value);
    }

    @Override
    public final void set(final String key, final Serializable value, int seconds) {
        RBucket<Object> temp = getRedisBucket(key);
        temp.set(value);
    }

    public final void multiSet(final Map<String, Object> temps) {
        redissonClient.getBuckets().set(temps);
    }

    @Override
    public final Boolean exists(final String key) {
        RBucket<Object> temp = getRedisBucket(key);
        return temp.isExists();
    }

    @Override
    public final void del(final String key) {
        redissonClient.getKeys().delete(key);
    }

    @Override
    public final void delAll(final String pattern) {
        redissonClient.getKeys().deleteByPattern(pattern);
    }

    @Override
    public final String type(final String key) {
        RType type = redissonClient.getKeys().getType(key);
        if (type == null) {
            return null;
        }
        return type.getClass().getName();
    }

    private final void expire(final RBucket<Object> bucket, final int seconds) {
        bucket.expire(seconds, TimeUnit.SECONDS);
    }

    @Override
    public final Boolean expireAt(final String key, final long unixTime) {
        return redissonClient.getBucket(key).expireAt(new Date(unixTime));
    }

    @Override
    public final Long ttl(final String key) {
        RBucket<Object> rBucket = getRedisBucket(key);
        return rBucket.remainTimeToLive();
    }

    @Override
    public final Object getSet(final String key, final Serializable value) {
        RBucket<Object> rBucket = getRedisBucket(key);
        return rBucket.getAndSet(value);
    }

    @Override
    public Set<Object> getAll(String pattern) {
        Set<Object> set = InstanceUtil.newHashSet();
        Iterable<String> keys = redissonClient.getKeys().getKeysByPattern(pattern);
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            set.add(getRedisBucket(key).get());
        }
        return set;
    }

    @Override
    public Boolean expire(String key, int seconds) {
        RBucket<Object> bucket = getRedisBucket(key);
        return true;
    }

    @Override
    public void hset(String key, Serializable field, Serializable value) {
        redissonClient.getMap(key).put(field, value);
    }

    @Override
    public Object hget(String key, Serializable field) {
        return redissonClient.getMap(key).get(field);
    }

    @Override
    public void hdel(String key, Serializable field) {
        redissonClient.getMap(key).remove(field);
    }

    @Override
    public void sadd(String key, Serializable value) {
        redissonClient.getSet(key).add(value);
    }

    @Override
    public Set<Object> sall(String key) {
        return redissonClient.getSet(key).readAll();
    }

    @Override
    public boolean sdel(String key, Serializable value) {
        return redissonClient.getSet(key).remove(value);
    }

    @Override
    public boolean lock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(15, 5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return false;
    }

    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    @Override
    public boolean setnx(String key, Serializable value) {
        try {
            return redissonClient.getLock(key).tryLock(Long.valueOf(value.toString()), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void putMap(String key, String field, Serializable obj) {
        RMapCache<String, Object> map = redissonClient.getMapCache(key);
        map.put(field, obj);
    }

    @Override
    public Object getMap(String key, String field) {
        RMapCache<String, Object> map = redissonClient.getMapCache(key);
        if (map.isEmpty()) {
            return null;
        } else {
            return map.get(field);
        }
    }

    @Override
    public boolean unlock(String key, String requestId) {
        List<Object> list = InstanceUtil.newArrayList();
        list.add(key);
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        return redissonClient.getScript().eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.BOOLEAN,
                list, requestId);
    }

    @Override
    public boolean lock(String key, String requestId, int seconds) {
        return redissonClient.getBucket(key).trySet(requestId, seconds, TimeUnit.SECONDS);
    }
<<<<<<< HEAD
=======

    @Override
    public Long zadd(String key, Set<ZSetOperations.TypedTuple<Serializable>> tuples) {
        return null;
    }

    @Override
    public Boolean zadd(String key, Serializable value, double score) {
        return null;
    }

    @Override
    public Set<Serializable> zget(String key, Long Smin, Long Smax, int offset, int count) {
        return null;
    }

    @Override
    public Set<Serializable> zgetDesc(String key, Long Smin, Long Smax, int offset, int count) {
        return null;
    }

    @Override
    public List zgetAll(String key) {
        return null;
    }
>>>>>>> 058ce521fe683b2266ba3db1a9cfae778303501a
}
