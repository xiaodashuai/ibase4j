package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizGuaranteeContract;

import java.util.List;
import java.util.Map;

/**
 * @author lwh
 * @date 2018/8/16
 */
public interface BizGuaranteeContractMapper extends BaseMapper<BizGuaranteeContract> {
    @Override
    List<Long> selectIdPage(@Param("cm")Map<String, Object> params);
}
