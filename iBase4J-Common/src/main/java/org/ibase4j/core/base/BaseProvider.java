package org.ibase4j.core.base;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.exception.BusinessException;
import org.ibase4j.core.exception.ValidateException;

import java.util.List;
import java.util.Map;

public interface BaseProvider<T extends BaseModel> {
    Page<Map<String, Object>> queryMap(Map<String, Object> params);

    <K> Page<K> getPage(Page<Long> ids, Class<K> cls);

    /**
     * 支持多表连接查询，返回字段名称为key的map组合
     ***/
//    Page<Map<String, Object>> queryMapPage(Map<String, Object> params);

    Page<T> queryFromDB(Map<String, Object> params);

    Page<T> queryFromDB(T entity, Page<T> rowBounds);

    Page<T> query(Map<String, Object> params);

    Page<T> query(T entity, Page<T> rowBounds);


    Boolean insert(T entity);

    Boolean insertBatch(List<T> entityList);

    Boolean insertBatch(List<T> entityList, int batchSize);


    T selectOne(Wrapper<T> wrapper);

    T update(T record) throws BusinessException, ValidateException;

    T queryById(Long id);


    T selectOne(T entity);

    T updateAllColumn(T record) throws BusinessException, ValidateException;

    Boolean updateAllColumnBatch(List<T> entityList) throws BusinessException, ValidateException;

    Boolean updateAllColumnBatch(List<T> entityList, int batchSize) throws BusinessException, ValidateException;

    Boolean updateBatch(List<T> entityList) throws BusinessException, ValidateException;

    Boolean updateBatch(List<T> entityList, int batchSize) throws BusinessException, ValidateException;


    void del(List<Long> ids, Long userId) throws BusinessException, ValidateException;

    void del(Long id, Long userId) throws BusinessException, ValidateException;

    void delete(Long id) throws BusinessException, ValidateException;

    Integer deleteByEntity(T t) throws BusinessException, ValidateException;

    Integer deleteByMap(Map<String, Object> columnMap) throws BusinessException, ValidateException;

    Integer count(T entity) throws BusinessException, ValidateException;


    Integer deleteByParams(Map<String, Object> params);


    List<T> queryList(Map<String, Object> params);

    List<T> queryList(List<Long> ids);

    List<T> selectList(Wrapper<T> entity);

    <K> List<K> queryList(List<Long> ids, Class<K> cls);

    List<T> queryListFromDB(Map<String, Object> params);

    List<T> queryListFromDB(T entity);

}
