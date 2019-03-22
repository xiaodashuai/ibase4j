package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysDicType;

import java.util.List;
import java.util.Map;

public interface SysDicTypeMapper extends BaseMapper<SysDicType> {
    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	SysDicType queryByCode(String account);

	SysDicType queryByCodeText(String account);
}