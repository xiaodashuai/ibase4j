/**
 * 
 */
package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizProductTypesDept;

import java.util.List;

/**
 * 描述: 部门与数据访问权限关系
 * 
 * @author Administrator
 *
 */
public interface BizProductTypeDeptMapper extends BaseMapper<BizProductTypesDept> {
	
	List<String> queryMenuIdsByRoleId(@Param("deptId") Long deptId);
	
	void deleteByDeptId(@Param("deptId") Long deptId);
	
}
