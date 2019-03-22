package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysDataRule;
import org.ibase4j.model.SysDeptDataRule;
import org.ibase4j.model.SysUserDataRule;

import java.util.List;

public interface ISysDataRuleProvider extends BaseProvider<SysDataRule> {
	/**
	 * 功能：查询绑定了组权限的数据
	 * @param
	 * @return
	 */
	List<SysDeptDataRule> getDataRuleByDeptId(long groupId);

	//查询用户绑定的数据权限
	List<SysUserDataRule> getDataRuleByUserId(long userId);
	
	//保存组权限
	boolean saveDeptDataRule(List<SysDeptDataRule> deptDataRule, Long deptId);

	//保存用户与数据权限
	boolean saveUserDataRule(List<SysUserDataRule> sysUserDataRules, Long userId);

	SysDataRule queryByCode(String account);

}
