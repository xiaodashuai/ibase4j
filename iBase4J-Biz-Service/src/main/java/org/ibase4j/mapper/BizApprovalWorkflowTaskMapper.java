package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizApprovalWorkflowTask;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author lwh
 * @date 2018/05/08
 */
public interface BizApprovalWorkflowTaskMapper extends BaseMapper<BizApprovalWorkflowTask> {
    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
}
