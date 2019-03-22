package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizLeave;

import java.util.List;


/**
 * <p>
 * Mapper接口-请假申请
 * </p>
 *
 * @author czm
 * @since 2018-01-22
 */
public interface BizLeaveMapper extends BaseMapper<BizLeave> {
	List<Long> selectIdPage(@Param("cm") BizLeave leave);
}