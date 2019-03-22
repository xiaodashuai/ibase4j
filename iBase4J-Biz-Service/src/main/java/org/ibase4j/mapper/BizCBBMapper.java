/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCBB;

import java.util.Map;

/**
 * 功能：金额流水信息表
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizCBBMapper extends BaseMapper<BizCBB>{

    /** 
    * @Description: 根据截止日和类型取余额 
    * @Param: [params] 
    * @return: java.util.Map 
    */
    String getCBBbyCBCType(@Param("cm") Map<String, Object> params);
}
