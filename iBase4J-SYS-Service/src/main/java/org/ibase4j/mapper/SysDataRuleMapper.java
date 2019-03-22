package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysDataRule;

public interface SysDataRuleMapper extends BaseMapper<SysDataRule> {

	SysDataRule queryByCode(String account);
}