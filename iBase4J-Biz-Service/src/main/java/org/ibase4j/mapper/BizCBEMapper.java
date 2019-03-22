/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCBE;

import java.util.Map;

/**
 * 功能：余额信息表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizCBEMapper extends BaseMapper<BizCBE>{

    /**
    * @Description: 本金累计收回金额（原币）
    * @Param: [params]
    * @return: java.util.Map
    */
    Map getGrantAMTInALL(@Param("cm") Map<String, Object> params);
}
