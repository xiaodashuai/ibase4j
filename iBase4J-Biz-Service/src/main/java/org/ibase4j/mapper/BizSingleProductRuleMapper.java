package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizSingleProductRule;
import org.ibase4j.vo.ProductVo;

import java.util.List;

public interface BizSingleProductRuleMapper extends BaseMapper<BizSingleProductRule> {
	
	List<Long> selectIdPage(@Param("cm") BizSingleProductRule bizSingleProductRule);
	/**
	 * 功能：查询一个方案中的多个产品信息
	 * @param debtCode 方案编号
	 * @return
	 */
	List<ProductVo> getProductPairByDebtCode(@Param("debtCode")String debtCode);
	
	/**
	 * 功能：查询一个产品信息
	 * @param sprId 产品号
	 * @return
	 */
	ProductVo getProductPairBySingleProductRuleId(@Param("sprId")Long sprId);
}