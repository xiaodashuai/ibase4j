package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizProductTypes;

import java.util.List;
import java.util.Map;


/**
 * @author gby
 */
public interface BizProductTypeMapper extends BaseMapper<BizProductTypes> {

    List<String> getProductTypeIdByDeptId(@Param("deptId") Long deptId);

	List<BizProductTypes> selectProductType(@Param("product") Map<String, Object> params);

	BizProductTypes queryByNoticeTitle(String account);

	List<BizProductTypes> queryChildProduct();

	BizProductTypes queryByCode(@Param("code")String productTyepsCode);

	BizProductTypes queryByParentCode(@Param("parentCode")String parentCode);

}
