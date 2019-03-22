/**
 * 
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizRepaymentPlan;

import java.util.Map;

/**
 * 功能：还款计划
 * 
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizRepaymentPlanProvider extends BaseProvider<BizRepaymentPlan> {
    /**
     * @Description: 查询还款计划表相关信息
     * @Param: [param]
     * @return: java.util.Map
     */
    Map getDebtInfoForRepayment(Map<String,Object> param);

}
