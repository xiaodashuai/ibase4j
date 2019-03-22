package org.ibase4j.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizTemporarySave;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BizTemporarySaveProvider extends BaseProvider<BizTemporarySave> {

    /**
     * 暂存文件，支持自定义查询参数
     * @param o
     * @param params
     * @return
     */
    boolean saveTemporary(Object o,Map<String, Object> params);
    /**
     * 方案暂存
     * @param o
     * @param params
     * @return
     */
    boolean saveSchemeTemporary(Object o,Map<String, Object> params);

    /**
     * 条件暂存
     * @param t
     * @param params
     * @return
     */
    boolean saveGrantTemporary(Object t, Map params);

    /**
     * 默认查询为BizTemporarySave
     * @param params
     * @return
     */
    Object getTemporary(Map<String, Object> params);

    /**
     * 将本地存储的文件返回
     * @param params
     * @return
     */
    <T extends Serializable> Object getTemporary(Map<String, Object> params,Class<T> clazz);
    
    /**
     * 通过编号查暂存文件内容
     * @param id
     * @return
     */
    Object getById(Long id);
    
    /**
     * 根据查询条件删除暂存文件;
     * @param params
     */
    int deleteTemporaryByParam(Map<String,Object> params);

    /**
     * 删除暂存文件;
     * @param params
     */
    boolean delTemporary(Map params);
    
    /**
     * 删除暂存文件数据以及服务器对应的文件;
     * @param params
     */
    boolean delById(Long id);

    /**
     * 获取缓存列表
     * @param params
     * @return
     */
    List<BizTemporarySave> getTemporaryList(Map<String, Object> params);

    /**
     * 获取方案缓存列表
     * @param params
     * @return
     */
    List<BizTemporarySave> queryScheme(EntityWrapper<BizTemporarySave> ew);
}
