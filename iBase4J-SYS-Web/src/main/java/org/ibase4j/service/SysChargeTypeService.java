package org.ibase4j.service;

import java.util.List;
import java.util.Map;

import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.model.SysChargeType;
import org.ibase4j.provider.ISysChargeTypeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 费用类型管理
 * 
 * @author XiaoYu
 */
@Service
public class SysChargeTypeService extends BaseService<ISysChargeTypeProvider, SysChargeType> {
	@Reference
	public void setProvider(ISysChargeTypeProvider provider) {
		this.provider = provider;
	}

	@Autowired
	private SysProductTypeService sysProductTypeService;

	public Page<?> queryAll(Map<String, Object> params) {
		Page<SysChargeType> query = provider.query(params);
		List<SysChargeType> records = query.getRecords();
		for (SysChargeType sysChargeType : records) {
			String productTyepsCode = sysChargeType.getProductTyepsCode();
			BizProductTypes bizProductTypes = sysProductTypeService.queryByCode(productTyepsCode);
			if(!"PT0600000000".equals(bizProductTypes.getCode())){
				BizProductTypes queryByParentCode = sysProductTypeService.queryByParentCode(bizProductTypes.getParentCode());
				StringBuffer names = new StringBuffer();
				if(queryByParentCode != null){
					String name = bizProductTypes.getName();
					names.append(name);
					names.insert(0, queryByParentCode.getName()+"-");
					getParent(queryByParentCode, names);
				}
				String string = names.toString();
				sysChargeType.setProductTypesName(string);
				names = new StringBuffer();
			}else{
				StringBuffer names = new StringBuffer();
				names.append(bizProductTypes.getName());
				String string = names.toString();
				sysChargeType.setProductTypesName(string);
			}


		}
//		// 查询所有末级产品信息
//		List<BizProductTypes> child = sysProductTypeService.queryByIschild("0");
//		for (BizProductTypes bizProductTypes : child) {
//			String name = bizProductTypes.getName();
//			StringBuffer names = new StringBuffer();
//			names.append(names);
//			getParent(bizProductTypes, names);
//			
//		}
//		return null;
		return query;
	}

	public void getParent(BizProductTypes bizProductTypes, StringBuffer names) {
		if (bizProductTypes.getLeaf() != 1 && bizProductTypes.getIsChild() != 0 ) {
			BizProductTypes x = sysProductTypeService.queryByParentCode(bizProductTypes.getParentCode());
			names.insert(0, x.getName()+"-");
			getParent(x, names);
		}
	}
}
