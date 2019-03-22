/**
 * 
 */
package org.ibase4j.provider;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizFEP;

import java.util.List;
import java.util.Map;

/**
 * 功能： 收息收费表 
 * 日期：2018/7/6
 * @author czm
 */
public interface BizFEPProvider extends BaseProvider<BizFEP> {

    /** 
    * @Description: 通过对象表INR获取收息收费集合 
    * @Param: [params] 
    * @return: java.util.List
    */ 
   List getBizFEPByINR(Map<String,Object> params);
   
   /**
    * 功能：删除发放对应的所有费用
    * @param grantId 发放审核id 
    * @return
    */
   int deleteByGrantCode(@Param("grantId") String grantId);
}
