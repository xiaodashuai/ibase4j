package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizInterfaceResult;

import java.util.Map;

/**
 * @Description: 接口返回结果
 * @Author: dj
 * @CreateDate: 2018-12-13
 */
public interface BizInterfaceResultMapper extends BaseMapper<BizInterfaceResult> {

    /**
    * @Description: 查询接口响应结果 0 失败 1 成功
    * @Param: [params]
    * @return: java.util.Map
    */
    Map getInterfaceResponseStatus(@Param("cm")Map<String, Object> params);
}
