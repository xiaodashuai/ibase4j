
package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.mapper.SysDicMapper;
import org.ibase4j.model.SysDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "SysDic")
@Service(interfaceClass = ISysDicProvider.class)
public class SysDicProviderImpl extends BaseProviderImpl<SysDic> implements ISysDicProvider {
	@Autowired
	private SysDicMapper dicMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CachePut(value = "sysDic")
	public void updateDic(SysDic record) {
		record.setUpdateTime(new Date());
		if (record.getId() == null) {
			record.setCreateTime(new Date());
			dicMapper.insert(record);
		} else {
			dicMapper.updateById(record);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "sysDic")
	public void deleteDic(Long id) {
		dicMapper.deleteById(id);
	}

	@Cacheable(value = "sysDic")
	public SysDic queryDicById(Long id) {
		return dicMapper.selectById(id);
	}

	@Override
	@Cacheable(value = "sysDics")
	public Map<String, Map<String, String>> getAllDic() {
		Map<String, Object> params = InstanceUtil.newHashMap();
		params.put("orderBy", "type_,sort_no");
		List<SysDic> list = queryList(params);
		Map<String, Map<String, String>> resultMap = InstanceUtil.newHashMap();
		for (SysDic sysDic : list) {
			if (sysDic != null) {
				String key = sysDic.getType();
				if (resultMap.get(key) == null) {
					Map<String, String> dicMap = InstanceUtil.newHashMap();
					resultMap.put(key, dicMap);
				}
				if (StringUtils.isNotBlank(key)) {
					resultMap.get(key).put(key + sysDic.getCode(), sysDic.getCodeText());
				} else {
					resultMap.get(key).put(sysDic.getCode(), sysDic.getCodeText());
				}
			}
		}
		return resultMap;
	}

	@Override
	@Cacheable(value = "sysDicMap")
	public Map<String, String> queryDicByDicIndexKey(String key) {
		return applicationContext.getBean(ISysDicProvider.class).getAllDic().get(key);
	}

	@Override
	public Page<SysDic> queryDic(Map<String, Object> params) {
		Page<Long> page = getPage(params);
		page.setRecords(mapper.selectIdPage(page, params));
		return getPage(page);
	}

	@Override
	public List<SysDic> queryCodeList(String type) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("type", type);
		List<SysDic> list = dicMapper.selectCodeList(params);
		return list;
	}

	@Override
	public SysDic queryByCode(String account) {
		SysDic sysDic = dicMapper.queryByCode(account);
		return sysDic;
	}

	@Override
	public SysDic queryByCodeText(String account) {
		SysDic sysDic = dicMapper.queryByCodeText(account);
		return sysDic;
	}

}
