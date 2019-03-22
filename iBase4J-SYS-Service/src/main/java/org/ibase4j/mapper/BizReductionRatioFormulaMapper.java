package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizReductionRatioFormula;

import java.util.List;


/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author xy
 * @date 2018/05/08
 */
public interface BizReductionRatioFormulaMapper extends BaseMapper<BizReductionRatioFormula> {
	List<Long> selectIdPage(@Param("cm") BizReductionRatioFormula bizReductionRatioFormula);
}
