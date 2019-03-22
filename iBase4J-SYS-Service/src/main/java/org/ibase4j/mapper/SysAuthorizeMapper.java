package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.model.SysMenu;

import java.util.List;

public interface SysAuthorizeMapper {

	void deleteUserRole(@Param("userId") Long userId);

	void deleteRoleMenu(@Param("roleId") Long roleId, @Param("permission") String permission);

	List<Long> getAuthorize(@Param("userId") Long userId,@Param("userType") String userType);

	List<String> queryPermissionByUserId(@Param("userId") Long userId);

	List<SysMenu> queryMenusPermission();

}
