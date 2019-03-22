package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysDic;
import org.ibase4j.provider.ISysDicProvider;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysDicService extends BaseService<ISysDicProvider, SysDic> {
	@Reference
	private ISysDicProvider provider;

	public Page<SysDic> queryDic(Map<String, Object> params) {
		return provider.queryDic(params);
	}

	public void addDic(SysDic record) {
		record.setCreateBy(SysWebUtil.getCurrentUser());
		record.setCreateTime(new Date());
		record.setEnable(1);
		provider.updateDic(record);
	}

	public void updateDic(SysDic record) {
		Assert.notNull(record.getId(), "ID");
		record.setUpdateBy(SysWebUtil.getCurrentUser());
		record.setUpdateTime(new Date());
		record.setUpdateBy(SysWebUtil.getCurrentUser());
		provider.updateDic(record);
	}

	public void deleteDic(Long id) {
		Assert.notNull(id, "ID");
		provider.deleteDic(id);
	}

	public SysDic queryDicById(Long id) {
		return provider.queryById(id);
	}

	public Map<String, String> queryDicByDicIndexKey(String key) {
		Assert.notNull(key, "KEY");
		return provider.queryDicByDicIndexKey(key);
	}

    public List<SysDic> queryDicByCode(String code) {
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("parent_code", code);
//        params.put("orderBy", "sort_no");
        return provider.queryCodeList(code);
    }
	
	public List<SysDic> queryDicByType(String type) {
		Assert.notNull(type, "KEY");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		params.put("orderBy", "sort_no");
		return provider.queryList(params);
	}

	public Boolean queryByCode(String account) {
		SysDic sysDic =	provider.queryByCode(account);
		return sysDic != null;
	}

	public Boolean queryByCodeText(String account) {
		SysDic sysDic =	provider.queryByCodeText(account);
		return sysDic != null;

	}

}
