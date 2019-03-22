package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysChargeType;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author XiaoYu
 * @since 2018-08-23
 */
public interface SysChargeTypeMapper extends BaseMapper<SysChargeType> {
	@Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
}
