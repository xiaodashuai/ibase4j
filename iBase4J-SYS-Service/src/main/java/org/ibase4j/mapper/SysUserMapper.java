package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends BaseMapper<SysUser> {

	@Override
	List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

	/** 修改启用状态 **/
	Long updateState(@Param("cm") Map<String, Object> params);

	SysUser queryByAccount(String account);

	SysUser queryByStaffNo(String account);

}
