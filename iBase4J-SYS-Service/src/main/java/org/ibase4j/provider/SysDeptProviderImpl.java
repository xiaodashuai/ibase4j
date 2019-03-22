package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.plugins.Page;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.SysDeptMapper;
import org.ibase4j.model.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
@CacheConfig(cacheNames = "sysDept")
@Service(interfaceClass = ISysDeptProvider.class)
public class SysDeptProviderImpl extends BaseProviderImpl<SysDept> implements ISysDeptProvider {
	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Override
	public SysDept queryByDeptName(String deptName) {
		SysDept sysDpet = sysDeptMapper.queryByDeptName(deptName);
		return sysDpet;
	}

	@Override
	public List<Long> getByEditDeptName(SysDept sysDept) {
		List<Long> sizeList = sysDeptMapper.getByEditDeptName(sysDept);
		return sizeList;
	}

	@Override
	public List<SysDept> getByParentCode(String deptCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentCode", deptCode);
		return this.queryList(params);
	}

	@Override
	public SysDept queryDeptByCode(String code) {
		SysDept sysDpet = sysDeptMapper.queryDeptByCode(code);
		return sysDpet;
	}
	
	@Override
	public List<Long> getDeptPage(Page page,Map<String,Object> params){
		List<Long> ids = sysDeptMapper.selectIdPage(page,params);
		return ids;
	}
}
