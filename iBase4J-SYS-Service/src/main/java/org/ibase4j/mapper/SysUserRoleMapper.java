package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysUserRole;

import java.util.List;
import java.util.Map;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
	
	List<String> queryRoleCodesByUserId(@Param("userId") Long userId);
	
	List<Long> queryRoleIdsByUserId(@Param("userId") Long userId);
	
	boolean deleteByUser(@Param("userId") Long userId);

	List<SysUserRole> queryRoleIds(@Param("cm") Map<String, Object> params);
}