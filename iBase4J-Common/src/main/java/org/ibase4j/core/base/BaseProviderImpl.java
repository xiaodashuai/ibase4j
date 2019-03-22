package org.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.Constants;
import org.ibase4j.core.exception.BusinessException;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.cache.CacheKey;
import org.ibase4j.core.support.generator.Sequence;
import org.ibase4j.core.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public abstract class BaseProviderImpl<T extends BaseModel> implements BaseProvider<T>, ApplicationContextAware {
    protected static  final Logger logger = LogManager.getLogger();
    @Autowired
    protected BaseMapper<T> mapper;
    protected ApplicationContext applicationContext;


    private static ExecutorService executor = ThreadUtil.threadPool(1, 1000, 30);
    private int threadSleep = PropertiesUtil.getInt("db.reader.list.threadWait", 10);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    protected static Boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }


    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }


    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenricType(getClass(), 1);
    }


    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 分页查询
     */
    public static Page<Long> getPage(Map<String, Object> params) {
        logger.debug("=========BaseProviderImpl========getPage====params={}", JSON.toJSONString(params));
//        Integer current = 1;
//        Integer size = 10;
//        String orderBy = "id_";
//        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
//            current = Integer.valueOf(params.get("pageNum").toString());
//        }
//        if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
//            current = Integer.valueOf(params.get("pageIndex").toString());
//        }
//        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
//            size = Integer.valueOf(params.get("pageSize").toString());
//        }
//        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
//            orderBy = (String) params.get("orderBy");
//            params.remove("orderBy");
//        }
//        if (size == -1) {
//            Page<Long> page = new Page<Long>();
//            page.setOrderByField(orderBy);
//            page.setAsc(false);
//            return page;
//        }
//        Page<Long> page = new Page<Long>(current, size, orderBy);
//        page.setAsc(false);
        Page<Long> page = PageUtil.getPage(params);
        return page;
    }

    /**
     * 非id缓存的分页
     */
//    public static Page<Map<String, Object>> getMapPage(Map<String, Object> params) {
//        logger.debug("=========BaseProviderImpl========getMapPage====params={}", JSON.toJSONString(params));
//        Integer current = 1;
//        Integer size = 10;
//        String orderBy = "id_";
//        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
//            current = Integer.valueOf(params.get("pageNum").toString());
//        }
//        if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
//            current = Integer.valueOf(params.get("pageIndex").toString());
//        }
//        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
//            size = Integer.valueOf(params.get("pageSize").toString());
//        }
//        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
//            orderBy = (String) params.get("orderBy");
//            params.remove("orderBy");
//        }
//        if (size == -1) {
//            Page<Map<String, Object>> page = new Page<Map<String, Object>>();
//            page.setOrderByField(orderBy);
//            page.setAsc(false);
//            return page;
//        }
//        Page<Map<String, Object>> page = new Page<Map<String, Object>>(current, size, orderBy);
//        page.setAsc(false);
//        return page;
//    }




    /**
     * 根据Id查询(默认类型T)
     */
    public Page<Map<String, Object>> getPageMap(final Page<Long> ids) {
        logger.debug("=========BaseProviderImpl========getPageMap====ids={}", JSON.toJSONString(ids));
        if (ids != null) {
            Page<Map<String, Object>> page = new Page<Map<String, Object>>(ids.getCurrent(), Constants.PAGINATION_DEFAULT_SIZE);
            page.setTotal(ids.getTotal());
            final List<Map<String, Object>> records = InstanceUtil.newArrayList();

            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            records.set(index, InstanceUtil.transBean2Map(queryById(ids.getRecords().get(index))));
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTraceAsString(e));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtil.getStackTraceAsString(e));
                }
            }
            page.setRecords(records);
            return page;
        }
        return new Page<Map<String, Object>>();
    }


    /**
     * 根据结果集查询(类型Map)
     */
    public Page<Map<String, Object>> getResultPage(final Page<Map<String, Object>> result) {
        logger.debug("=========BaseProviderImpl========getResultPage====result={}", JSON.toJSONString(result));
        if (result != null) {
            Page<Map<String, Object>> page = new Page<Map<String, Object>>(result.getCurrent(), Constants.PAGINATION_DEFAULT_SIZE);
            page.setTotal(result.getTotal());
            final List<Map<String, Object>> records = InstanceUtil.newArrayList();
            for (int i = 0; i < result.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            for (int i = 0; i < result.getRecords().size(); i++) {
                final int index = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            records.set(index, result.getRecords().get(index));
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTraceAsString(e));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtil.getStackTraceAsString(e));
                }
            }
            page.setRecords(records);
            return page;
        }

        return new Page<Map<String, Object>>();
    }

    /**
     * 根据Id查询(默认类型T)
     */
    public Page<T> getPage(final Page<Long> ids) {
        logger.debug("=========BaseProviderImpl========getPage====ids={}", JSON.toJSONString(ids));
        if (ids != null) {
            Page<T> page = new Page<T>(ids.getCurrent(), Constants.PAGINATION_DEFAULT_SIZE);
            page.setTotal(ids.getTotal());
            final List<T> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            records.set(index, queryById(ids.getRecords().get(index)));
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTraceAsString(e));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtil.getStackTraceAsString(e));
                }
            }
            page.setRecords(records);
            return page;
        }
        return new Page<T>();
    }

    /**
     * 根据参数分页查询
     */
	@Override
    public Page<T> query(Map<String, Object> params) {
        Page<Long> page = PageUtil.getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page);
    }

    /**
     * 根据实体参数分页查询
     */
	@Override
    public Page<T> query(T entity, Page<T> rowBounds) {
        Page<Long> page = new Page<Long>();
        try {
            PropertyUtils.copyProperties(page, rowBounds);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        List<Long> ids = mapper.selectIdPage(page, entity);
        page.setRecords(ids);
        return getPage(page);
    }

    /**
     * 根据实体参数分页查询
     */
	@Override
    public Page<T> queryFromDB(T entity, Page<T> rowBounds) {
        Page<T> page = new Page<T>();
        try {
            PropertyUtils.copyProperties(page, rowBounds);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        Wrapper<T> wrapper = new EntityWrapper<T>(entity);
        List<T> list = mapper.selectPage(page, wrapper);

//		list.forEach(t -> saveCache(t));
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T record = iterator.next();
            saveCache(record);
        }
        Page<T> pager = new Page<T>(page.getCurrent(), page.getSize());
        pager.setRecords(list);
        pager.setTotal(page.getTotal());
        return pager;
    }

    	@Override
    public Page<T> queryFromDB(Map<String, Object> params) {
        Page<Long> page = PageUtil.getPage(params);
        List<T> list = mapper.selectPage(page, params);
//		list.forEach(t -> saveCache(t));
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T record = iterator.next();
            saveCache(record);
        }
        Page<T> pager = new Page<T>(page.getCurrent(), page.getSize());
        pager.setRecords(list);
        pager.setTotal(page.getTotal());
        return pager;
    }

    	@Override
    public List<T> queryListFromDB(Map<String, Object> params) {
        return mapper.selectByMap(params);
    }

    	@Override
    public List<T> queryListFromDB(T entity) {
        Wrapper<T> wrapper = new EntityWrapper<T>(entity);
        return mapper.selectList(wrapper);
    }


    	@Override
    public Integer count(T entity) {
        Wrapper<T> wrapper = new EntityWrapper<T>(entity);
        return mapper.selectCount(wrapper);
    }

    public Integer count(Map<String, Object> params) {
        return mapper.selectCount(params);
    }



    /**
     * 根据Id查询(默认类型T)
     */

    @Override
    public T queryById(Long id) {
        Assert.notNull(id, "id is not null");
        return queryById(id, 1);
    }


    private T queryById(Long id, int times) {
        logger.debug("=========BaseProviderImpl========queryById====id={}",id);
        CacheKey key = CacheKey.getInstance(getClass());
        T record = null;
        if (key != null) {
            try {
                logger.debug("=========BaseProviderImpl========queryById====record={}",JSON.toJSONString(CacheUtil.getCache().get(key.getValue() + ":" + id, key.getTimeToLive())));
                record = (T)CacheUtil.getCache().get(key.getValue() + ":" + id, key.getTimeToLive());

            } catch (Exception e) {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
            }
        }
        if (record == null) {
            String lockKey = getLockKey(id);
            String requestId = Sequence.next().toString();
            if (CacheUtil.getLock(lockKey, requestId)) {
                try {
                    record = mapper.selectById(id);
                    saveCache(record);
                } finally {
                    CacheUtil.unLock(lockKey, requestId);
                }
            } else {
                if (times > 3) {
                    record = mapper.selectById(id);
                    saveCache(record);
                } else {
                    logger.info(getClass().getSimpleName() + ":" + id + " retry getById.");
                    ThreadUtil.sleep(1, 20);
                    return queryById(id, times + 1);
                }
            }
        }
        return record;
    }


//    @Override
//    public Page<Map<String, Object>> queryMapPage(Map<String, Object> params) {
//        Page<Map<String, Object>> page = getMapPage(params);
//        //TODO 添加查询数据权限
//        page.setRecords(mapper.selectResultPage(page, params));
//        return getResultPage(page);
//    }

        @Override
    public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPageMap(page);
    }



    protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
        Page<Long> page = PageUtil.getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page, cls);
    }




    /**
     * 根据Id查询(cls返回类型Class)
     */
	@Override
    public <K> Page<K> getPage(final Page<Long> ids, final Class<K> cls) {
        if (ids != null) {
            Page<K> page = new Page<K>(ids.getCurrent(), Constants.PAGINATION_DEFAULT_SIZE);
            page.setTotal(ids.getTotal());
            final List<K> records = InstanceUtil.newArrayList();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                records.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            for (int i = 0; i < ids.getRecords().size(); i++) {
                final int index = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            T t = queryById(ids.getRecords().get(index));
                            K k = InstanceUtil.to(t, cls);
                            records.set(index, k);
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTraceAsString(e));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < records.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtil.getStackTraceAsString(e));
                }
            }
            page.setRecords(records);
            return page;
        }
        return new Page<K>();
    }





    @Override
    public List<T> queryList(Map<String, Object> params) {
	    logger.debug("=========BaseProviderImpl========queryList====params={}", JSON.toJSONString(params));
        if (DataUtil.isEmpty(params.get("orderBy"))) {
            params.put("orderBy", "id_");
        }
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = queryList(ids);
        return list;
    }

    /**
     * 根据Id查询(默认类型T)
     */
    @Override
    public List<T> queryList(final List<Long> ids) {
        logger.debug("=========BaseProviderImpl========queryList====ids={}", JSON.toJSONString(ids));
        final List<T> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                list.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            for (int i = 0; i < ids.size(); i++) {
                final int index = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            list.set(index, queryById(ids.get(index)));
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTraceAsString(e));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < list.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtil.getStackTraceAsString(e));
                }
            }
        }
        return list;
    }

    /**
     * 根据Id查询(cls返回类型Class)
     */
    @Override
    public <K> List<K> queryList(final List<Long> ids, final Class<K> cls) {
        logger.debug("=========BaseProviderImpl========queryList====ids={}=====cls={}", JSON.toJSONString(ids),cls);
        final List<K> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (int i = 0; i < ids.size(); i++) {
                list.add(null);
            }
            final Map<Integer, Object> thread = InstanceUtil.newConcurrentHashMap();
            for (int i = 0; i < ids.size(); i++) {
                final int index = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            T t = queryById(ids.get(index));
                            K k = InstanceUtil.to(t, cls);
                            list.set(index, k);
                        } catch (Exception e) {
                            logger.error(ExceptionUtil.getStackTraceAsString(e));
                        } finally {
                            thread.put(index, 0);
                        }
                    }
                });
            }
            while (thread.size() < list.size()) {
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    logger.error(ExceptionUtil.getStackTraceAsString(e));
                }
            }
        }
        return list;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert(T entity) {
        Assert.notNull(entity, "entity is not null");
        return retBool(mapper.insert(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertBatch(List<T> entityList) {
        return insertBatch(entityList, 30);
    }

    /**
     * 批量插入
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertBatch(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int size = entityList.size();
            String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
            for (int i = 0; i < size; i++) {
                batchSqlSession.insert(sqlStatement, entityList.get(i));
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
        }
        return true;
    }

    /**
     * 逻辑批量删除
     */
	@Override
    @Transactional
    public void del(List<Long> ids, Long userId) {
        logger.debug("=========BaseProviderImpl========del====ids={}=====userId={}", JSON.toJSONString(ids),userId);
        Iterator<Long> iterator = ids.listIterator();
        while (iterator.hasNext()) {
            Long id =iterator.next();
            del(id, userId);
        }
    }

    /**
     * 逻辑删除
     */
    @Override
    @Transactional
    public void del(Long id, Long userId) {
        logger.debug("=========BaseProviderImpl========del====ids={}=====userId={}", id,userId);
        try {
            T record = this.queryById(id);
            record.setEnable(0);
            record.setUpdateTime(new Date());
            record.setUpdateBy(userId);
            mapper.updateById(record);
            final CacheKey key = CacheKey.getInstance(getClass());
            deleteCache(key.getValue() + ":" + id, 1);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 物理删除
     */
	@Override
    @Transactional
    public Integer deleteByEntity(T t) {
        Wrapper<T> wrapper = new EntityWrapper<T>(t);
        return mapper.delete(wrapper);
    }

    /**
     * 物理删除
     */
	@Override
    @Transactional
    public Integer deleteByMap(Map<String, Object> columnMap) {
        logger.debug("=========BaseProviderImpl========deleteByMap====columnMap={}", columnMap);
        return mapper.deleteByMap(columnMap);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        try {
            mapper.deleteById(id);
            final CacheKey key = CacheKey.getInstance(getClass());
            deleteCache(key.getValue() + ":" + id, 1);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Integer deleteByParams(Map<String, Object> params) {
        logger.debug("=========BaseProviderImpl========deleteByParams====params={}", params);
        int size = 0;
        try {
            List<T> result = queryList(params);
            if (result != null && !result.isEmpty()) {
                for (T entity : result) {
                    //判断不为空再删除
                    if (entity != null) {
                        Long id = entity.getId();
                        delete(id);
                        size++;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException(e.getMessage(), e);
        }
        return size;
    }

    @Override
    @Transactional
    public T update(T record) {
        logger.debug("=========BaseProviderImpl========update====record={}", JSON.toJSONString(record));
        try {
            record.setUpdateTime(new Date());
            if (record.getId() == null) {
                record.setCreateTime(new Date());
                mapper.insert(record);
            } else {
                String requestId = Sequence.next().toString();
                String lockKey = getLockKey("U" + record.getId());
                if (CacheUtil.getLock(lockKey, requestId)) {
                    try {
                        mapper.updateById(record);
                    } finally {
                        CacheUtil.unLock(lockKey, requestId);
                    }
                } else {
                    throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
                }
            }
            record = mapper.selectById(record.getId());
            saveCache(record);
        } catch (DuplicateKeyException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new BusinessException("已经存在相同的记录.");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException(ExceptionUtil.getStackTraceAsString(e));
        }
        return record;
    }



    private Boolean updateBatch(List<T> entityList, int batchSize, boolean selective) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (int i = 0; i < entityList.size(); i++) {
                if (selective) {
                    update(entityList.get(i));
                } else {
                    updateAllColumn(entityList.get(i));
                }
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new RuntimeException("Error: Cannot execute insertOrUpdateBatch Method. Cause", e);
        }
        return true;
    }



    /**
     * 批量修改所有字段/新增
     */
    @Override
    public Boolean updateAllColumnBatch(List<T> entityList) {
        return updateAllColumnBatch(entityList, 30);
    }

    /**
     * 批量修改所有字段/新增
     */
    @Override
    public Boolean updateAllColumnBatch(List<T> entityList, int batchSize) {
        return updateBatch(entityList, batchSize, false);
    }

    /**
     * 批量修改/新增
     */
    @Override
    public Boolean updateBatch(List<T> entityList) {
        return updateBatch(entityList, 30);
    }

    /**
     * 批量修改/新增
     */
    @Override
    public Boolean updateBatch(List<T> entityList, int batchSize) {
        return updateBatch(entityList, batchSize, true);
    }


    /**
     * 修改所有字段/新增
     */
    @Override
    @Transactional
    public T updateAllColumn(T record) {
        try {
            record.setUpdateTime(new Date());
            if (record.getId() == null) {
                record.setCreateTime(new Date());
                mapper.insert(record);
            } else {
                String requestId = Sequence.next().toString();
                String lockKey = getLockKey("U" + record.getId());
                if (CacheUtil.getLock(lockKey, requestId)) {
                    try {
                        mapper.updateAllColumnById(record);
                    } finally {
                        CacheUtil.unLock(lockKey, requestId);
                    }
                } else {
                    throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
                }
            }
            record = mapper.selectById(record.getId());
            saveCache(record);
        } catch (DuplicateKeyException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException("已经存在相同的记录.");
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            throw new RuntimeException(ExceptionUtil.getStackTraceAsString(e));
        }
        return record;
    }


    @Override
    /** 根据实体参数查询一条记录 */
    public T selectOne(T entity) {
        T t = mapper.selectOne(entity);
        saveCache(t);
        return t;
    }

    @Override
    public T selectOne(Wrapper<T> wrapper) {
        return SqlHelper.getObject(mapper.selectList(wrapper));
    }

    @Override
    public List<T> selectList(Wrapper<T> entity) {
        return mapper.selectList(entity);
    }

    /**
     * 获取缓存键值
     */
    protected String getCacheKey(Object id) {
        CacheKey cacheKey = CacheKey.getInstance(getClass());
        StringBuilder sb = new StringBuilder();
        if (cacheKey == null) {
            sb.append(getClass().getName());
        } else {
            sb.append(cacheKey.getValue());
        }
        return sb.append(":").append(id).toString();
    }

    /**
     * 获取缓存键值
     */
    protected String getLockKey(Object id) {
        CacheKey cacheKey = CacheKey.getInstance(getClass());
        StringBuilder sb = new StringBuilder();
        if (cacheKey == null) {
            sb.append(getClass().getName());
        } else {
            sb.append(cacheKey.getValue());
        }
        return sb.append(":LOCK:").append(id).toString();
    }



    /**
     * 保存缓存
     */
    private void saveCache(final T record) {
        if (record == null) {
            return;
        }
        final CacheKey key = CacheKey.getInstance(getClass());
        if (key != null) {
            try {
                CacheUtil.getCache().set(key.getValue() + ":" + record.getId(), record, key.getTimeToLive());
            } catch (Exception e) {
                logger.error(ExceptionUtil.getStackTraceAsString(e));
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        deleteCache(key.getValue() + ":" + record.getId(), 1);
                    }
                });
            }
        }
    }

    private void deleteCache(String key, int times) {
        try {
            CacheUtil.getCache().del(key);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            ThreadUtil.sleep(10, Math.min(Integer.MAX_VALUE, times * 100));
            deleteCache(key, times + 1);
        }
    }
}
