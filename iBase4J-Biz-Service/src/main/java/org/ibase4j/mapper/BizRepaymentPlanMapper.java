/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizRepaymentPlan;

import java.util.List;
import java.util.Map;

/**
 * 功能：债项发放申请
 * @author czm 
 * 日期：2018/7/6
 */
public interface BizRepaymentPlanMapper extends BaseMapper<BizRepaymentPlan>{
    @Override
    List<Long> selectIdPage(@Param("cm")Map<String, Object> params);

    /**
     * @Description: 查询还款计划相关信息
     * @Param: [param]
     * @return: java.util.Map
     */
    Map getDebtInfoForRepayment(@Param("cm") Map<String, Object> param);
}
