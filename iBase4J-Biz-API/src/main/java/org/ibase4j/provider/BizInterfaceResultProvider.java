package org.ibase4j.provider;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizInterfaceResult;

import java.util.Map;

/**
 * @Description: 接口请求结果
 * @Author: dj
 * @CreateDate: 2018-12-13
 */
public interface BizInterfaceResultProvider extends BaseProvider<BizInterfaceResult>{
    /**
     * @Description: 查询接口响应结果 0 失败 1 成功
     * @Param: [params]
     * @return: java.util.Map
     */
    Map getInterfaceResponseStatus(Map<String, Object> params);
}
