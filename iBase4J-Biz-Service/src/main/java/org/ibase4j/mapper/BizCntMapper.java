package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCnt;

public interface BizCntMapper extends BaseMapper<BizCnt> {

    /**
     * 根据字段名称获取id
     * @param name
     * @return
     */
    BizCnt findByName(String name);

}
