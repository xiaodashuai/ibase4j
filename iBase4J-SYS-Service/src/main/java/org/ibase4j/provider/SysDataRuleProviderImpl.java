package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysDataRuleMapper;
import org.ibase4j.mapper.SysDeptDataRuleMapper;
import org.ibase4j.mapper.SysUserDataRuleMapper;
import org.ibase4j.model.SysDataRule;
import org.ibase4j.model.SysDeptDataRule;
import org.ibase4j.model.SysUserDataRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoshuiquan
 * @version 1.0 2018.3.2
 * 
 */
@CacheConfig(cacheNames = "sysDataRule")
@Service(interfaceClass = ISysDataRuleProvider.class)
public class SysDataRuleProviderImpl extends BaseProviderImpl<SysDataRule> implements ISysDataRuleProvider {
	@Autowired
	private SysDeptDataRuleMapper sysDeptDataRuleMapper;
	@Autowired
	private SysUserDataRuleMapper sysUserDataRuleMapper;
	@Autowired
	private SysDataRuleMapper sysDataRuleMapper;
	@Override
	public List<SysDeptDataRule> getDataRuleByDeptId(long deptId) {
		Map<String, Object> columnMap = new HashMap<String, Object>(1);
		columnMap.put("dept_id", deptId);
		return sysDeptDataRuleMapper.selectByMap(columnMap);
	}

	@Override
	public List<SysUserDataRule> getDataRuleByUserId(long userId) {
		Map<String, Object> columnMap = new HashMap<String, Object>(1);
		columnMap.put("user_id", userId);
		return sysUserDataRuleMapper.selectByMap(columnMap);
	}

	@Override
	public boolean saveDeptDataRule(List<SysDeptDataRule> deptDataRules, Long deptId) {
		// 1.首先删除旧关系
		Map<String, Object> columnMap = new HashMap<String, Object>(1);
		columnMap.put("dept_id", deptId);
		// 1.删除旧关系
		sysDeptDataRuleMapper.deleteByMap(columnMap);
		// 2.保存新关系
		for (SysDeptDataRule entity : deptDataRules) {
			sysDeptDataRuleMapper.insert(entity);
		}
		return true;
	}

	@Override
	public boolean saveUserDataRule(List<SysUserDataRule> sysUserDataRules, Long userId) {
		// 1.首先删除旧关系
		Map<String, Object> columnMap = new HashMap<String, Object>(1);
		columnMap.put("user_id", userId);
		// 1.删除旧关系
		sysUserDataRuleMapper.deleteByMap(columnMap);
		// 2.保存新关系
		for (SysUserDataRule entity : sysUserDataRules) {
			sysUserDataRuleMapper.insert(entity);
		}
		return true;
	}

	@Override
	public SysDataRule queryByCode(String account) {
		SysDataRule sysDataRule = sysDataRuleMapper.queryByCode(account);
		return sysDataRule;
	}

}
