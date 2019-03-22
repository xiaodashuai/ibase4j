package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizHistoryCommentInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: dj
 * @CreateDate: 2018-06-14
 */
public interface BizHistoryCommentInfoMapper extends BaseMapper<BizHistoryCommentInfo>{
    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
}
