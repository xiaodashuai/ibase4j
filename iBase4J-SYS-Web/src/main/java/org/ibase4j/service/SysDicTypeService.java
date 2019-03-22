package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysDicType;
import org.ibase4j.provider.ISysDicTypeProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class SysDicTypeService extends BaseService<ISysDicTypeProvider, SysDicType> {
	@Reference
	private ISysDicTypeProvider provider;

	public Page<SysDicType> queryDic(Map<String, Object> params) {
		return provider.queryDicType(params);
	}

	public void addDicType(SysDicType record) {
		record.setCreateBy(SysWebUtil.getCurrentUser());
		record.setCreateTime(new Date());
		record.setEnable(1);
		provider.updateDicType(record);
	}

	public void updateDicType(SysDicType record) {
		Assert.notNull(record.getId(), "ID");
		record.setUpdateBy(SysWebUtil.getCurrentUser());
		record.setUpdateTime(new Date());
		record.setUpdateBy(SysWebUtil.getCurrentUser());
		provider.updateDicType(record);
	}

	public void deleteDicType(Long id) {
		Assert.notNull(id, "ID");
		provider.deleteDicType(id);
	}

	public SysDicType queryDicTypeById(Long id) {
		return provider.queryById(id);
	}

	public Map<String, String> queryDicByDicIndexKey(String key) {
		Assert.notNull(key, "KEY");
		return provider.queryDicByDicTypeIndexKey(key);
	}

	public Boolean queryByCode(String account) {
		SysDicType sysDicType = provider.queryByCode(account);
		return sysDicType != null;
	}

	public Boolean queryByCodeText(String account) {
		SysDicType sysDicType = provider.queryByCodeText(account);
		return sysDicType != null;
	}

}
