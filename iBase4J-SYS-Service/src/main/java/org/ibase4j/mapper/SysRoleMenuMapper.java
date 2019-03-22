package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysRoleMenu;

import java.util.List;
import java.util.Map;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
	
	List<Long> queryMenuIdsByRoleId(@Param("roleId") Long roleId);

	List<Map<String, Object>> queryPermissions(@Param("roleId") Long roleId);

	List<String> queryPermission(@Param("roleId") Long id);

	List<Map<String, Object>> queryAllMenu();

	List<Map<String, Object>> queryButtonByPrentId(@Param("pId") Long pId);

	List<Long> queryMenuIdByRoleId(@Param("roleId") Long roleId);
	
	void deleteByRole(@Param("roleId") Long roleId);
}
