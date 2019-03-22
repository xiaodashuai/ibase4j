package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizProStatement;

import java.util.List;


/**
 * <p>
 * Mapper接口
 * </p>
 * @author mac19
 */
public interface BizProstaMapper extends BaseMapper<BizProStatement> {
	List<Long> selectIdPage(@Param("cm") BizProStatement leave);
}