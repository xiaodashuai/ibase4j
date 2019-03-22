package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCustomer;

import java.util.List;
import java.util.Map;

public interface BizCustomerMapper extends BaseMapper<BizCustomer> {

	List<Long> selectIdPage(@Param("cm") BizCustomer bizCustomer);

	List queryByCustNo(String custNo);
	
	List<BizCustomer> getBizCustomerList(@Param("cm")Map<String, Object> params);

}
