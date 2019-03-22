package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends BaseMapper<SysRole> {
	@Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
	
	SysRole queryByAccount(String account);

	List<SysRole> queryByPowerType(Long sysRole);

	List<SysRole> queryByRoleType(Integer roleType);

	SysRole queryByCode(String code);
	
}