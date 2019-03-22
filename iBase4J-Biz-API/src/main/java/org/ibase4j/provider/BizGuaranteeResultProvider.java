/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizGuaranteeResult;

import java.util.List;
import java.util.Map;

/**
 * 功能： 发放流程中的担保信息 
 * 日期：2018/7/6
 * @author czm
 */
public interface BizGuaranteeResultProvider extends BaseProvider<BizGuaranteeResult> {

    /** 
    * @Description: 通过业务inr获取担保信息集合 
    * @Param: [params] 
    * @return: java.util.List<org.ibase4j.model.BizGuaranteeResult> 
    */ 
    List<BizGuaranteeResult> getBizGuaranteeResultList(Map<String, Object> params);
    
    /**
     * 功能：根据发放审核编号删除对应担保合同信息
     * @param grantCode
     * @return
     */
    int deleteByGrantCode(String grantCode);
    
    /** 
    * @Description: 额度占用接口参数 
    * @Param: [params] 
    * @return: java.util.List 
    */ 
    List getGuaranteeInfoList(Map<String, Object> params);
}
