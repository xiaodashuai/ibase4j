package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizDebtProduct;

import java.util.List;
import java.util.Map;

public interface BizDebtProductMapper extends BaseMapper<BizDebtProduct> {

    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
	
}
