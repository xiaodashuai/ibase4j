package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysDic;

import java.util.List;
import java.util.Map;

public interface SysDicMapper extends BaseMapper<SysDic> {
	
   @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
    
    List<SysDic> selectCodeList(Map<String, Object> params);

	SysDic queryByCode(@Param("account")String account);

	SysDic queryByCodeText(@Param("account")String account);
}