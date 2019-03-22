package org.ibase4j.provider;

import java.util.List;
import java.util.Map;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysDept;

import com.baomidou.mybatisplus.plugins.Page;

public interface ISysDeptProvider extends BaseProvider<SysDept> {

	/**
	 * 功能: 新增部门时判断部门名称是否唯一
	 * 
	 * @param deptName
	 * @return 如果返回对象不为空,则表示重复;否则表示唯一
	 */
	SysDept queryByDeptName(String deptName);

	/**
	 * 功能: 修改部门时判断部门名称是否唯一
	 * 
	 * @param sysDept
	 * @return 如果返回集合大小大于0,则表示重复;否则表示唯一
	 */
	List<Long> getByEditDeptName(SysDept sysDept);

	SysDept queryDeptByCode(String code);

	List<SysDept> getByParentCode(String deptCode);
	
	List<Long> getDeptPage(Page page,Map<String,Object> params);
}
