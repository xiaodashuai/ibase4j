/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizFEC;

import java.util.List;
import java.util.Map;

/**
 * 功能：余额信息表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizFECMapper extends BaseMapper<BizFEC>{

    /**
     * @Description: 根据利率基准code查询名称
     * @Param: [irBk]
     * @return: java.lang.String
     */
    String getIrBkStringByCode(@Param("cm")Map<String, Object> params);
    /**
     * @Description: 查询产品费用信息集合
     * @Param: [params]
     * @return: java.util.List
     */
    List getBizFECByINR(@Param("cm")Map<String, Object> params);
}
