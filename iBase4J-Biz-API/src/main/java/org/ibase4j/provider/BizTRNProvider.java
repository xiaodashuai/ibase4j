/**
 * 
 */
package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizTRN;

import java.util.Map;

/**
 * 功能：交易流水表
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizTRNProvider extends BaseProvider<BizTRN> {


    /** 
    * @Description: 保存债项发放审批流水 
    * @Param: [params] 
    * @return: void 
    */ 
    void saveDebtGrantTRN(Map<String,Object> params);
    /**
     * @Description: 保存债项方案审批流水
     * @Param: [params]
     * @return: void
     */
    void saveDebtMainTRN(Map<String, Object> params);

    /**
    * @Description: 保存放款操作流水
    * @Param: [params]
    * @return: void
    */
    void saveMakeLoansTRN(Map<String,Object> params);

    /**
     * @Description: 查看债项方案
     * @Param: [params]
     * @return: void
     */
    Page<BizTRN> checkDebtSchem(String u,String bizTRN);



}
