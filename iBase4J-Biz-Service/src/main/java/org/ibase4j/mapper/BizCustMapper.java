package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCust;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author XiaoYu
 * @date 2018/06/27
 */
public interface BizCustMapper extends BaseMapper<BizCust> {

	List<Long> selectIdPage(@Param("cm") BizCust bizCust);

	List queryByCustNo(String custNo);

	List<BizCust> getBizCustomerList(@Param("cm")Map<String, Object> params);
	
}
