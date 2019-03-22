package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizCustomer;

import java.util.List;
import java.util.Map;
/**
 * 描述：客户主体信息
 * @author xy
 * @date 2018/05/14
 */
public interface BizCustomerProvider extends BaseProvider<BizCustomer> {
	//通过客户编号查询
	List queryByCustNo(String custNo);
	/**
	 * 功能：查询方案中的客户主体信息
	 * @param params
	 * @return
	 */
	List<BizCustomer> getBizCustomerList(Map<String,Object> params);


}
