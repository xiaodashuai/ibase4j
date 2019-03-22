package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizEmail;

import java.util.List;
/**
 * <p>
 * 邮件发送记录Mapper接口
 * </p>
 *
 * @author xy
 * @date 2018/05/25
 */
public interface BizEmailMapper extends BaseMapper<BizEmail> {
	List<Long> selectIdPage(@Param("cm") BizEmail bizEmail);
}
