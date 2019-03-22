package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizProductDefinition;

import java.util.Map;

/**
 * @Description: 产品序号
 * @Author: dj
 * @CreateDate: 2019-01-09
 */
public interface BizProductDefinitionMapper extends BaseMapper<BizProductDefinition> {
    /**
    * @Description: 查询产品序号
    * @Param: [param]
    * @return: java.lang.String
    */
    String selectProductSeqnum(@Param("cm") Map<String,Object> param);
}
