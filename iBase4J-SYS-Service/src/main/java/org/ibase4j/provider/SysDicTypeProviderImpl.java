package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysDicTypeMapper;
import org.ibase4j.model.SysDicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "SysDicType")
@Service(interfaceClass = ISysDicTypeProvider.class)
public class SysDicTypeProviderImpl extends BaseProviderImpl<SysDicType> implements ISysDicTypeProvider {
	@Autowired
	private SysDicTypeMapper dicTypeMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CachePut(value = "sysDicType")
	public void updateDicType(SysDicType record) {
		record.setUpdateTime(new Date());
		if (record.getId() == null) {
			record.setCreateTime(new Date());
			dicTypeMapper.insert(record);
		} else {
			dicTypeMapper.updateById(record);
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = "sysDicType")
	public void deleteDicType(Long id) {
		dicTypeMapper.deleteById(id);
	}

	@Cacheable(value = "sysDicType")
	public SysDicType queryDicTypeById(Long id) {
		return dicTypeMapper.selectById(id);
	}

//	@Cacheable(value = "sysDicTypes")
//	public Map<String, Map<String, String>> getAllDicType() {
//		Map<String, Object> params = InstanceUtil.newHashMap();
////		params.put("orderBy", "type_,sort_no");
//		List<SysDicType> list = queryList(params);
//		Map<String, Map<String, String>> resultMap = InstanceUtil.newHashMap();
////		for (SysDic sysDic : list) {
////			if (sysDic != null) {
////				String key = sysDic.getType();
////				if (resultMap.get(key) == null) {
////					Map<String, String> dicMap = InstanceUtil.newHashMap();
////					resultMap.put(key, dicMap);
////				}
////				if (StringUtils.isNotBlank(sysDic.getParentCode())) {
////					resultMap.get(key).put(sysDic.getParentCode() + sysDic.getCode(), sysDic.getCodeText());
////				} else {
////					resultMap.get(key).put(sysDic.getCode(), sysDic.getCodeText());
////				}
////			}
////		}
//		return resultMap;
//	}

	@Override
    @Cacheable(value = "sysDicTypeMap")
	public Map<String, String> queryDicByDicTypeIndexKey(String key) {
		return applicationContext.getBean(ISysDicProvider.class).getAllDic().get(key);
	}

	@Override
    public Page<SysDicType> queryDicType(Map<String, Object> params) {
		Page<Long> page = getPage(params);
		page.setRecords(mapper.selectIdPage(page, params));
		return getPage(page);
	}


	@Override
	public SysDicType queryByCode(String account) {
		SysDicType sysDicType =	dicTypeMapper.queryByCode(account);
		return sysDicType;
	}


	@Override
	public SysDicType queryByCodeText(String account) {
		SysDicType sysDicType =	dicTypeMapper.queryByCodeText(account);
		return sysDicType;
	}

}
