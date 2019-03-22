/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizFEP;

import java.util.List;
import java.util.Map;

/**
 * 功能： 收息收费表 
 * 日期：2018/7/6
 * @author czm
 */
public interface BizFEPMapper extends BaseMapper<BizFEP>{
	
    /** 
    * @Description: 查询产品费用信息集合 
    * @Param: [params] 
    * @return: java.util.List 
    */ 
    List getBizFEPByINR(@Param("cm")Map<String, Object> params);
}
