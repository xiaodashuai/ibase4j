/**
 * 
 */
package org.ibase4j.provider;

import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizRepaymentPricinalPlan;

import java.util.List;
import java.util.Map;

/**
 * 功能：还本计划表
 * 
 * @author lwh
 * 日期：2018/7/6
 */
public interface BizRepaymentPricinalPlanProvider extends BaseProvider<BizRepaymentPricinalPlan> {
    /**
     * @Description: 查询还本计划表相关信息
     * @param param
     * @return
     */
    List getDebtInfoForRepaymentPricinal(Map<String,Object> param);
    
    /**
     * 功能：删除发放审核对应的还款计划
     * @param grantCode
     * @param debtCode
     * @return
     */
    int deleteByGrantCode(String grantCode,String debtCode);

    List<Long> selectIdPage(Page page, @Param("cm") Map<String, Object> params);
}
