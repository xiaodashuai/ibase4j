package org.ibase4j.provider;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.core.base.BaseProviderImpl;
import org.ibase4j.mapper.BizCustomerMapper;
import org.ibase4j.model.BizCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;
import java.util.Map;
/**
 * @author xy
 * @date 2018/05/08
 */
@CacheConfig(cacheNames = "bizCustomer")
@Service(interfaceClass = BizCustomerProvider.class)
public class BizCustomerProviderImpl extends BaseProviderImpl<BizCustomer> implements BizCustomerProvider {
	@Autowired
	private BizCustomerMapper bizCustomerMapper;

	@Override
	public List queryByCustNo(String custNo) {
		List customer = bizCustomerMapper.queryByCustNo(custNo);
		logger.debug(customer);
		return customer;
	}

	@Override
	public List<BizCustomer> getBizCustomerList(Map<String, Object> params) {
		return bizCustomerMapper.getBizCustomerList(params);
	}
	
}
