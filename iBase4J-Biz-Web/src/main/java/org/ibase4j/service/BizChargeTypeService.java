/**
 * 
 */
package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.BizChargeType;
import org.ibase4j.model.NameValuePair;
import org.ibase4j.provider.BizChargeTypeProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：前台使用收费类型组件
 * @author czm
 * @date 2018/7/13
 * @version 1.0
 */
@Service
public class BizChargeTypeService extends BaseService<BizChargeTypeProvider,BizChargeType> {
	@Reference
    public void setProvider(BizChargeTypeProvider provider){
		this.provider = provider;
	}
	
	/**
	 * 功能：根据业务编号查询可用的收费类型
	 * @param productTypesCode 业务品种编号
	 * @return
	 */
	public List<NameValuePair> getChargeTypeByProductCode(String productTypesCode){
		Map<String,Object> params =new HashMap<String,Object>();
		params.put("productTypesCode", productTypesCode);
		List<BizChargeType> list = queryList(params);
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		for(BizChargeType node: list){
			NameValuePair pair = new NameValuePair();
			pair.setName(node.getChargeName());
			pair.setValue(node.getChargeCode());
			result.add(pair);
		}
		return result;
	}
	
}
