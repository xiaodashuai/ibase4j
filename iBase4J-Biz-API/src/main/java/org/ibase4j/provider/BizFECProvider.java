/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizFEC;

import java.util.List;
import java.util.Map;

/**
 * 功能：利率表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizFECProvider extends BaseProvider<BizFEC> {

    /** 
    * @Description: 通过业务inr查询利率信息集合 
    * @Param: [params] 
    * @return: java.util.List
    */ 
    List getBizFECByINR(Map<String, Object> params);
    
    /**
     * 功能：删除发放申请对应的所有FEC
     * @param grantId
     * @return
     */
    int deleteByGrantId(Long grantId);
}
