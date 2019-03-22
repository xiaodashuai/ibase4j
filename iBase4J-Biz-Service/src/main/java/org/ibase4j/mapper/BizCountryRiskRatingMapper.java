package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCountryRiskRating;

import java.util.List;
/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author XiaoYu
 * @date 2018/06/27
 */
public interface BizCountryRiskRatingMapper extends BaseMapper<BizCountryRiskRating> {
	List<Long> selectIdPage(@Param("cm") BizCountryRiskRating bizCountryRiskRating);
}
