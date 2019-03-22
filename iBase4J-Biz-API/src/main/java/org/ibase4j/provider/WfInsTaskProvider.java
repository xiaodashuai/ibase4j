package org.ibase4j.provider;


import java.util.List;
import java.util.Map;

public interface WfInsTaskProvider {
    /**
     * 工作流取值
     * @param params
     * @return
     */

    Map selectRoleId(Map<String,Object> params);

    Map selectRoleIdPrevious(Map<String,Object> params);

    Map selectUserIdStart(Map<String,Object> params);

    /**
     * 修改第一节点状态
     * @param params
     */
    void updateInfo(Map<String,Object> params);

    /**
     * 修改所有节点状态
     * @param params
     */
    void updateAllInfo(Map<String,Object> params);
}
