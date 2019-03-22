package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;

import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.model.BizProductTypesDept;
import org.ibase4j.model.SysChargeType;
import org.ibase4j.provider.IBizProductTypeProvider;
import org.ibase4j.vo.SysProductTypesCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 描述：产品种类管理 日期：2018/7/19
 * 
 * @author czm
 * @version 1.0
 */
@Service
public class SysProductTypeService extends BaseService<IBizProductTypeProvider, BizProductTypes> {

	@Reference
	public void setProvider(IBizProductTypeProvider iBizProductTypeProvider) {
		this.provider = iBizProductTypeProvider;
	}

	public List<SysProductTypesCheckVo> queryProductTypeByDeptId(long deptId) {
		List<SysProductTypesCheckVo> voList = new ArrayList<>();
		// 从部门和产品种类中间表中查询出已经绑定的产品种类code值
		List<String> deptProductTypeList = provider.getProductTypeByDeptId(deptId);
		// 查询父类产品种类数,遍历封装到vo类中
		Map<String, Object> sysProductType = new HashMap<String, Object>();
		sysProductType.put("parentCode", "0");
		List<BizProductTypes> productTypelist = provider.queryList(sysProductType);
		if (productTypelist.size() > 0) {
			for (BizProductTypes sysProductTypes : productTypelist) {
				SysProductTypesCheckVo vo = new SysProductTypesCheckVo();
				vo.setCode(sysProductTypes.getCode());
				vo.setName(sysProductTypes.getName());
				vo.setParentCode(sysProductTypes.getParentCode());
				vo.setRemark(sysProductTypes.getRemark());
				vo.setChecked(false);
				if (deptProductTypeList.contains(sysProductTypes.getCode())) {
					vo.setChecked(true);
				}
				// 查询出此父类下的子类产品，封装到vo类中
				Map<String, Object> sysProductTypeOrgMap = new HashMap<String, Object>();
				sysProductTypeOrgMap.put("parentCode", sysProductTypes.getCode());
				List<BizProductTypes> sonProductList = provider.queryList(sysProductTypeOrgMap);
				List<SysProductTypesCheckVo> sonList = new ArrayList<>();
				for (BizProductTypes s : sonProductList) {
					SysProductTypesCheckVo v = new SysProductTypesCheckVo();
					v.setCode(s.getCode());
					v.setName(s.getName());
					v.setParentCode(s.getParentCode());
					v.setRemark(s.getRemark());
					v.setChecked(false);
					if (deptProductTypeList.contains(s.getCode())) {
						v.setChecked(true);
					}
					sonList.add(v);
				}
				vo.setSonList(sonList);
				voList.add(vo);
			}
		} else {
			for (BizProductTypes sysProductTypes : productTypelist) {
				SysProductTypesCheckVo vo = new SysProductTypesCheckVo();
				vo.setCode(sysProductTypes.getCode());
				vo.setName(sysProductTypes.getName());
				vo.setParentCode(sysProductTypes.getParentCode());
				vo.setRemark(sysProductTypes.getRemark());
				vo.setChecked(false);
				voList.add(vo);
			}
		}
		return voList;
	}

	// 保存机构权限
	public boolean saveDeptProductType(String productTypesIds, Long deptId) {
		if (StringUtils.isNotBlank(productTypesIds)) {
			List<BizProductTypesDept> deptProductTypesList = new ArrayList<BizProductTypesDept>();
			String[] ids = StringUtils.split(productTypesIds, ",");
			Long createId = SysWebUtil.getCurrentUser();
			for (String id : ids) {
				if (StringUtils.isNotBlank(id)) {
					BizProductTypesDept sysProductTypesOrg = new BizProductTypesDept();
					sysProductTypesOrg.setCreateBy(createId);
					sysProductTypesOrg.setCreateTime(new Date());
					sysProductTypesOrg.setEnable(1);
					sysProductTypesOrg.setRemark("组id:" + deptId + "与产品种类code:" + id);
					deptProductTypesList.add(sysProductTypesOrg);
				}
			}
			// TODO
			// save();
		}
		return false;
	}

	public List<BizProductTypes> queryProductList(Map<String, Object> params) {
		params.put("parentCode", 0);
		List<BizProductTypes> productTypeList = provider.queryProductList(params);
		return productTypeList;
	}

	public Boolean queryByNoticeTitle(String account) {
		BizProductTypes sysProductTypes = provider.queryByNoticeTitle(account);
		return sysProductTypes != null;
	}

	public List<BizProductTypes> queryByIschild(String isChild) {
		List<BizProductTypes> queryProductList = provider.queryChildProduct();
		return queryProductList;
	}

	public BizProductTypes queryByCode(String productTyepsCode) {
		return provider.queryByCode(productTyepsCode);
	}

	public BizProductTypes queryByParentCode(String parentCode) {
		return provider.queryByParentCode(parentCode);
	}

	public List<BizProductTypes> queryChild() {
		List<BizProductTypes> list = provider.queryChildProduct();
		for (BizProductTypes bizProductTypes : list) {
			BizProductTypes queryByParentCode = queryByParentCode(bizProductTypes.getParentCode());
			if(!"PT0600000000".equals(bizProductTypes.getCode())){
				StringBuffer names = new StringBuffer();
				if(queryByParentCode != null){
					String name = bizProductTypes.getName();
					names.append(name);
					names.insert(0, queryByParentCode.getName() + "-");
					getParent(queryByParentCode, names);
				}
				String string = names.toString();
				bizProductTypes.setNames(string);
			}else{
				StringBuffer names = new StringBuffer();
				names.append(bizProductTypes.getName());
				String string = names.toString();
				bizProductTypes.setNames(string);
			}

		}
		return list;
	}

	public void getParent(BizProductTypes bizProductTypes, StringBuffer names) {
		if (bizProductTypes.getLeaf() != 1 && bizProductTypes.getIsChild() != 0) {
			BizProductTypes x = queryByParentCode(bizProductTypes.getParentCode());
			names.insert(0, x.getName() + "-");
			getParent(x, names);
		}
	}
}
