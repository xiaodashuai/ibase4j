package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizClassifyLevel;

import java.util.List;

public interface BizClassifyLevelMapper extends BaseMapper<BizClassifyLevel> {
	
	List<Long> selectIdPage(@Param("cm") BizClassifyLevel bizClassifyLevel);
}