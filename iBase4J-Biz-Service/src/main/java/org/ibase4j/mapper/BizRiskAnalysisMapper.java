package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizRiskAnalysis;

import java.util.List;

public interface BizRiskAnalysisMapper extends BaseMapper<BizRiskAnalysis> {
	
	List<Long> selectIdPage(@Param("cm") BizRiskAnalysis bizRiskAnalysis);
}