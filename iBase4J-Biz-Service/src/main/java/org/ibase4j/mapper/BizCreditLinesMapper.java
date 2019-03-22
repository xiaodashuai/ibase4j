package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizCreditLines;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author XiaoYu
 * @date 2018/06/27
 */
public interface BizCreditLinesMapper extends BaseMapper<BizCreditLines> {
	List<Long> selectIdPage(@Param("cm") BizCreditLines bizCreditLines);
}
