package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizGlobalProductRules;

import java.util.List;

public interface BizGlobalProductRulesMapper extends BaseMapper<BizGlobalProductRules> {
	
	List<Long> selectIdPage(@Param("cm") BizGlobalProductRules bizGlobalProductRules);
}