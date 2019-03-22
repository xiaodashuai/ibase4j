package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizMsg;

import java.util.List;
/**
 * <p>
 * 短信发送记录Mapper接口
 * </p>
 *
 * @author xy
 * @date 2018/05/25
 */
public interface BizMsgMapper extends BaseMapper<BizMsg> {
	List<Long> selectIdPage(@Param("cm") BizMsg bizMsg);
}
