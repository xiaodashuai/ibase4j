package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.WFLogs;

import java.util.List;


/**
 * <p>
 * Mapper接口-流程日志
 * </p>
 *
 * @author czm
 * @since 2018-01-22
 */
public interface WFLogsMapper extends BaseMapper<WFLogs> {
	List<Long> selectIdPage(@Param("cm") WFLogs logs);
}