package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysEvent;

import java.util.List;

public interface SysEventMapper extends BaseMapper<SysEvent> {

	List<SysEvent> queryByAccount(@Param("account")String account);
}