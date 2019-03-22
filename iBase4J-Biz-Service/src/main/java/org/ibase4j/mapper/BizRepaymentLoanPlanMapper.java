/**
 * 
 */
package org.ibase4j.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizRepaymentLoanPlan;

import java.util.List;
import java.util.Map;

/**
 * 功能：还息计划表
 * @author lwh
 * 日期：2018/7/6
 */
public interface BizRepaymentLoanPlanMapper extends BaseMapper<BizRepaymentLoanPlan>{
    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    /**
     * @Description: 查询还息计划相关信息
     * @Param: [param]
     * @return: java.util.Map
     */
    List getDebtInfoForRepaymentLoan(@Param("cm") Map<String, Object> params);

    List<Long> selectIdPage(Page page, @Param("cm") Map<String, Object> params);
}
