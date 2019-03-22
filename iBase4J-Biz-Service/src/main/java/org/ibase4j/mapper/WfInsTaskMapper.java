package org.ibase4j.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WfInsTaskMapper {

    Map selectRoleId(@Param("cm")Map<String, Object> params);

    Map selectRoleIdPrevious(@Param("cm")Map<String, Object> params);

    Map selectUserIdStart(@Param("cm")Map<String, Object> params);

    void updateInfo(@Param("cm")Map<String, Object> params);

    void updateAllInfo(@Param("cm")Map<String, Object> params);

    /**
     * 根据piid查询task
     * @param params
     * @return
     */
    List<Map> selectWfInsTaskListByPiid(@Param("cm") Map<String, Object> params);
}
