package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.model.BizCustomer;
import org.ibase4j.provider.BizCustomerProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xy
 * @date 2018/05/09
 */
@Service
public class BizCustomerService extends BaseService<BizCustomerProvider, BizCustomer> {
	@Reference
	public void setProvider(BizCustomerProvider provider) {
		this.provider = provider;
	}

	/**
	 * 功能：查询所有客户授信
	 * 
	 * @author xy
	 * @return 返回所有有效的客户授信
	 */
	public List<BizCustomer> getAllCheckPlan() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("enable", SysConstant.ENABLE_YES);
		// 查询所有状态为1(是)的检查计划配置
		List<BizCustomer> customerList = provider.queryList(params);
		return customerList;
	}
	
	public List<BizCustomer> getBizCustomerList(Map<String,Object> params){
		return provider.getBizCustomerList(params);
	}

	public void add(BizCustomer bizCustomer) {
		bizCustomer.setEnable(1);
		provider.update(bizCustomer);
	}

	public List queryByCustNo(Map<String, Object> param) {
		String custNo = param.get("custNo").toString();
		List customer = provider.queryByCustNo(custNo);
		logger.debug(custNo);
		return customer;
	}
	public List queryByCustNo(String custNo) {
		List customer = provider.queryByCustNo(custNo);
		return customer;
	}
	
}
