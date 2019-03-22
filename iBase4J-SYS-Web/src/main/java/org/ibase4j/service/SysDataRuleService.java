package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysDataRule;
import org.ibase4j.model.SysDeptDataRule;
import org.ibase4j.model.SysUserDataRule;
import org.ibase4j.provider.ISysDataRuleProvider;
import org.ibase4j.vo.SysDataRuleCheckVo;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiaoshuiquan
 * @version 1.0
 * @date 2018.2.28
 */
@Service
public class SysDataRuleService extends BaseService<ISysDataRuleProvider, SysDataRule> {
	@Reference
	public void setProvider(ISysDataRuleProvider provider) {
		this.provider = provider;
	}

	public List<SysDataRuleCheckVo> queryDataruleByDeptId(long deptId) {
		List<SysDataRuleCheckVo> voList = new ArrayList<>();
		// 从部门和数据权限中间表中查询出已经绑定的组权限的数据
		List<SysDeptDataRule> deptDataRuleList = provider.getDataRuleByDeptId(deptId);

		// 查询全部的数据权限,遍历封装到vo类中
		Map<String, Object> sysDataRule = new HashMap<String, Object>();
		List<SysDataRule> dataRulelist = provider.queryList(sysDataRule);
		if (dataRulelist.size() > 0) {
			for (SysDataRule dataRule : dataRulelist) {
				SysDataRuleCheckVo vo = new SysDataRuleCheckVo();
				vo.setDataruleID(dataRule.getId());
				vo.setCode(dataRule.getCode());
				vo.setSqlLevel(dataRule.getSqlLevel());
				vo.setSqlSebtence(dataRule.getSqlSebtence());
				vo.setRemark(dataRule.getRemark());
				vo.setChecked(false);
				for (SysDeptDataRule s : deptDataRuleList) {
					if (s.getDataId() == dataRule.getId()) {
						vo.setChecked(true);
					}
				}
				voList.add(vo);
			}
		} else {
			for (SysDataRule dataRule : dataRulelist) {
				SysDataRuleCheckVo vo = new SysDataRuleCheckVo();
				vo.setChecked(false);
				vo.setDataruleID(dataRule.getId());
				vo.setCode(dataRule.getCode());
				vo.setSqlLevel(dataRule.getSqlLevel());
				vo.setSqlSebtence(dataRule.getSqlSebtence());
				voList.add(vo);
			}
		}
		return voList;
	}

	/**
	 * gby
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysDataRuleCheckVo> queryDataruleByUserId(long userId) {
		List<SysDataRuleCheckVo> voList = new ArrayList<>();
		// 从用户与数据权限中间表中查询出已经绑定的组权限的数据id
		List<SysUserDataRule> userDataRuleList = provider.getDataRuleByUserId(userId);

		// 查询全部的数据权限,遍历封装到vo类中
		Map<String, Object> sysDataRule = new HashMap<String, Object>();
		List<SysDataRule> dataRulelist = provider.queryList(sysDataRule);
		if (dataRulelist.size() > 0) {
			for (SysDataRule dataRule : dataRulelist) {
				SysDataRuleCheckVo vo = new SysDataRuleCheckVo();
				vo.setDataruleID(dataRule.getId());
				vo.setCode(dataRule.getCode());
				vo.setSqlLevel(dataRule.getSqlLevel());
				vo.setSqlSebtence(dataRule.getSqlSebtence());
				vo.setRemark(dataRule.getRemark());
				vo.setChecked(false);
				for (SysUserDataRule s : userDataRuleList) {
					if (s.getDataId().equals(dataRule.getId())) {
						vo.setChecked(true);
					}
				}
				voList.add(vo);
			}
		} else {
			for (SysDataRule dataRule : dataRulelist) {
				SysDataRuleCheckVo vo = new SysDataRuleCheckVo();
				vo.setChecked(false);
				vo.setDataruleID(dataRule.getId());
				vo.setCode(dataRule.getCode());
				vo.setSqlLevel(dataRule.getSqlLevel());
				vo.setSqlSebtence(dataRule.getSqlSebtence());
				voList.add(vo);
			}
		}
		return voList;
	}

	/**
	 * @author gby
	 * @date 2018-05-10
	 * @param dataRuleIds
	 * @param userId
	 * @return
	 */
	public boolean saveUserDataRule(String dataRuleIds, Long userId) {
		if (StringUtils.isNotBlank(dataRuleIds)) {
			List<SysUserDataRule> userDataRuleList = new ArrayList<SysUserDataRule>();
			String[] ids = StringUtils.split(dataRuleIds, ",");
			Long createId = SysWebUtil.getCurrentUser();
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					SysUserDataRule sysUserDataRule = new SysUserDataRule();
					sysUserDataRule.setDataId(Long.valueOf(id));
					sysUserDataRule.setUserId(userId);
					sysUserDataRule.setCreateBy(createId);
					sysUserDataRule.setCreateTime(new Date());
					sysUserDataRule.setEnable(1);
					sysUserDataRule.setRemark("用户id:" + userId + "与数据权限id:" + id);
					userDataRuleList.add(sysUserDataRule);
				}
			}
			return provider.saveUserDataRule(userDataRuleList, userId);
		}
		return false;
	}

	// 保存机构权限
	public boolean saveDeptDataRule(String dataRuleIds, Long deptId) {
		if (StringUtils.isNotBlank(dataRuleIds)) {
			List<SysDeptDataRule> deptDataRuleList = new ArrayList<SysDeptDataRule>();
			String[] ids = StringUtils.split(dataRuleIds, ",");
			Long createId = SysWebUtil.getCurrentUser();
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					SysDeptDataRule sysDeptDataRule = new SysDeptDataRule();
					sysDeptDataRule.setDataId(Long.valueOf(id));
					sysDeptDataRule.setCreateBy(createId);
					sysDeptDataRule.setCreateTime(new Date());
					sysDeptDataRule.setEnable(1);
					sysDeptDataRule.setRemark("组id:" + deptId + "与数据权限id:" + id);
					deptDataRuleList.add(sysDeptDataRule);
				}
			}
			return provider.saveDeptDataRule(deptDataRuleList, deptId);
		}
		return false;
	}

	public Boolean queryByCode(String account) {
		SysDataRule sysDataRule = provider.queryByCode(account);
		return sysDataRule != null;
	}
}
