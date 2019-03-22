package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysUserDataRule;

import java.util.List;
import java.util.Map;

/**
 * 描述：用户与数据访问权限关联
 * @author czm
 *
 */
public interface SysUserDataRuleMapper extends BaseMapper<SysUserDataRule> {
    @Override
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);

    Integer deleteByUserId(Long id);


}
