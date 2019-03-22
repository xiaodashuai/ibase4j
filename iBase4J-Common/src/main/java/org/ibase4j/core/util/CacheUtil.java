package org.ibase4j.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.cache.CacheManager;

/**
 * @author ShenHuaJie
 * @since 2018年5月24日 下午6:37:31
 */

public final class CacheUtil {
    private static Logger logger = LogManager.getLogger();
    private static CacheManager cacheManager;
    private static CacheManager lockManager;

    public static void setCacheManager(CacheManager cacheManager) {
        CacheUtil.cacheManager = cacheManager;
    }

    public static void setLockManager(CacheManager cacheManager) {
        CacheUtil.lockManager = cacheManager;
    }

    public static CacheManager getCache() {
        return cacheManager;
    }

    public static CacheManager getLockManager() {
        return lockManager;
    }

    /**
     * 默认锁定一分钟
     */
    public static boolean getLock(String key, String requestId) {
        return getLock(key, requestId, 3);
    }

    public static boolean getLock(final String key, String requestId, final int seconds) {
        return tryLock(key, requestId, seconds);
    }

    /**
     * 获取锁
     * @param key
     * @param requestId
     * @param seconds
     * @return
     */
    private static boolean tryLock(final String key, String requestId, final int seconds) {
        logger.debug("TOLOCK : key={}, requestId={}",key,requestId);
        try {
            return lockManager.lock(key, requestId, seconds);
        } catch (Exception e) {
            logger.error("从redis获取锁信息失败", e);
            return false;
        }

    }

    /**
     * 删除锁
     * @param key
     * @param requestId
     */
    public static void unLock(String key, String requestId) {
        logger.debug("UNLOCK : key={}, requestId={}",key,requestId);
        try {
            lockManager.unlock(key, requestId);
        } catch (Exception e) {
            logger.error("从redis删除锁信息失败", e);
        }
    }
}
