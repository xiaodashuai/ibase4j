package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysDept;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDept> {
	List<Long> selectIdPage(@Param("cm") SysDept sysDept);

	List<Long> getByEditDeptName(SysDept sysDept);
	
	SysDept queryByDeptName(@Param("deptName") String deptName);

	SysDept queryDeptByCode(@Param("code") String code);
}