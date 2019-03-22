package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
	/** 获取所有权限 */
	public List<Map<String, String>> getPermissions();

	@Override
    public List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	public List<SysMenu> queryParents(@Param("parentId")Long parentId );
	
	public SysMenu queryByMenuName(String account);

	public List<SysMenu> queryMenuList(@Param("enable")Integer enable,@Param("powerType") String powerType);

	public List<SysMenu> queryByParentId(@Param("parentId")Long menuId);
}