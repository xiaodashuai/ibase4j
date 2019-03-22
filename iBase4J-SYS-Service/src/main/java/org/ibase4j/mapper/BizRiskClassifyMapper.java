package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizRiskClassify;

import java.util.List;

public interface BizRiskClassifyMapper extends BaseMapper<BizRiskClassify> {
	
	List<Long> selectIdPage(@Param("cm") BizRiskClassify bizRiskClassify);
}