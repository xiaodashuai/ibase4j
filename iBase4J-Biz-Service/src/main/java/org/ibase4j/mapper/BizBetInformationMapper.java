package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizBetInformation;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author XiaoYshuiqaun
 * @date 2018/08/26
 */
public interface BizBetInformationMapper extends BaseMapper<BizBetInformation> {
	List<Long> selectIdPage(@Param("cm") BizBetInformation bizBetInformation);
}
