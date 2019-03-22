package org.ibase4j.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizDebtSummary;
import org.ibase4j.vo.BizDebtInfo;
import org.ibase4j.vo.GrantRuleVerifVo;

import java.util.List;
import java.util.Map;

public interface BizDebtSummaryMapper extends BaseMapper<BizDebtSummary> {

	@Override
	List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    @Override
    List<Long> selectIdPage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);

	/**
	 * 功能：发放产品前需要验证发放的规则
	 * @return
	 */
	List<GrantRuleVerifVo> getGrantRuleVo(@Param("cm") Map<String, Object> params);

	List<BizDebtInfo> getDebtInfo(Pagination page, @Param("cm") Map<String, Object> params);

	/** 
	* @Description: 方案信息台账 方案信息明细
	* @Param: [params] 
	* @return: java.util.Map 
	*/ 
	Map getDebtInfoForStandingBook(@Param("cm") Map<String, Object> params);

	/**
	 * @Description: 发放信息台账 发放信息明细
	 * @Param: [params]
	 * @return: java.util.Map
	 */
	Map getGrantInfoForStandingBook(@Param("cm") Map<String, Object> params);

	/**
	 * @Description: 查询全部的客户
	 * @Param: xiaoshuiquan
	 * @return: java.util.Map
	 */

    /**
     * @Description: 通过债项方案编码查询债项方案主键
     * @Param: [debtCode]
     * @return: java.lang.Long
     */
    Long selectDebtIdByDebtCode(@Param("debtCode") String debtCode);

}




