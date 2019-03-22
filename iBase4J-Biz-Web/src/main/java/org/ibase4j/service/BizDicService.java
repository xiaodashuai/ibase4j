package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.model.SysDic;
import org.ibase4j.provider.ISysDicProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BizDicService {
	@Reference
	private ISysDicProvider provider;

	public Page<SysDic> queryDic(Map<String, Object> params) {
		return provider.queryDic(params);
	}

	public SysDic queryDicById(Long id) {
		return provider.queryById(id);
	}

	public Map<String, String> queryDicByDicIndexKey(String key) {
		Assert.notNull(key, "KEY");
		return provider.queryDicByDicIndexKey(key);
	}

    public List<SysDic> queryDicByCode(String type) {
        return provider.queryCodeList(type);
    }
	
	public List<SysDic> queryDicByType(String type) {
		Assert.notNull(type, "KEY");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		params.put("orderBy", "sort_no");
		return provider.queryList(params);
	}
	
	/**
	 * 描述 ： 根据码表类型和编码查询对象 
	 * @param type 类型
	 * @param code 编码 
	 * @return
	 */
	public SysDic queryByTypeAndCode(String type,String code) {
		Map<String, Object> params = InstanceUtil.newHashMap();
		params.put("type", type);
		params.put("code", code);
		List<SysDic> sysDicList = provider.queryList(params);
		if(sysDicList!=null && !sysDicList.isEmpty()){
			return sysDicList.get(0);
		}
		return null;
	}

	public SysDic queryByCodeText(String name) {
		SysDic sysDic =	provider.queryByCodeText(name);
		return sysDic;
	}

}
