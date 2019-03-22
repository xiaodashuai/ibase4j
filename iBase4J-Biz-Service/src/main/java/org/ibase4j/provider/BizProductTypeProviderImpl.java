package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.mapper.BizProductTypeDeptMapper;
import org.ibase4j.mapper.BizProductTypeMapper;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.model.BizProductTypesDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：产品种类管理
 * 
 * @author czm
 * @version 1.0
 */
@CacheConfig(cacheNames = "bizProductTypes")
@Service(interfaceClass = IBizProductTypeProvider.class)
public class BizProductTypeProviderImpl extends BaseProviderImpl<BizProductTypes> implements IBizProductTypeProvider {
	@Autowired
	private BizProductTypeDeptMapper bizProductTypeDeptMapper;

	@Autowired
	private BizProductTypeMapper bizProductTypeMapper;

	@Override
	public List<String> getProductTypeByDeptId(long deptId) {
		return bizProductTypeMapper.getProductTypeIdByDeptId(deptId);
	}

	@Override
	public boolean saveDeptProductTypes(List<BizProductTypesDept> deptProductTypes, Long deptId) {
		// 1.首先删除旧关系
		if (deptId != null) {
			bizProductTypeDeptMapper.deleteByDeptId(deptId);
		}
		// 2.保存新关系
		for (BizProductTypesDept entity : deptProductTypes) {
			bizProductTypeDeptMapper.insert(entity);
		}
		return true;
	}

	@Override
	public List<BizProductTypes> queryProductList(Map<String, Object> params) {
		List<BizProductTypes> productTypeList = bizProductTypeMapper.selectProductType(params);
		return productTypeList;
	}

	@Override
	public BizProductTypes queryByNoticeTitle(String account) {
		BizProductTypes sysProductTypes = bizProductTypeMapper.queryByNoticeTitle(account);
		return sysProductTypes;
	}

	@Override
	public BizProductTypes getByCode(String code) {
		BizProductTypes entity = new BizProductTypes();
		entity.setCode(code);
		return this.selectOne(entity);
	}

	@Override
	public List<BizProductTypes> getRoot() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("leaf", BizContant.BIZ_PRODUCT_TYPE_ROOT_LEAF);
		return this.queryList(params);
	}

	@Override
	public List<BizProductTypes> getByParentCode(String parentCode) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentCode", parentCode);
		return this.queryList(params);
	}

	@Override
	public List<BizProductTypes> queryChildProduct() {
		List<BizProductTypes> list = bizProductTypeMapper.queryChildProduct();
		return list;
	}

	@Override
	public BizProductTypes queryByCode(String productTyepsCode) {
		BizProductTypes selectOne = bizProductTypeMapper.queryByCode(productTyepsCode);
		return selectOne;
	}

	@Override
	public BizProductTypes queryByParentCode(String parentCode) {
		BizProductTypes selectOne = bizProductTypeMapper.queryByParentCode(parentCode);
		return selectOne;
	}
}
