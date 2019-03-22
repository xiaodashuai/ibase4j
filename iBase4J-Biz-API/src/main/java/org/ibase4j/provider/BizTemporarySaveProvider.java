package org.ibase4j.provider;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizTemporarySave;

import java.util.List;
import java.util.Map;

public interface BizTemporarySaveProvider extends BaseProvider<BizTemporarySave> {

    /**
     * 存储内容到本地
     * @param o
     * @param params
     * @return
     */
    boolean saveTemporary(Object o,Map<String, Object> params);
    /**
     * 存储内容到本地
     * @param o
     * @param params
     * @return
     */
    boolean saveSchemeTemporary(Object o,Map<String, Object> params);

    /**
     * 将本地存储的文件返回
     * @param params
     * @return
     */
    Object getTemporary(Map<String, Object> params);
    
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
    void delTemporary(Map params);
    
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
