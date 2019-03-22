package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.SysSession;

import java.util.List;

public interface SysSessionMapper extends BaseMapper<SysSession> {

    void deleteBySessionId(String sessionId);

    Long queryIdByAccount(String account);

    Long queryBySessionId(String sessionId);

    /**
     * 查询SysSession的主键  根据账号和 sessionId
     * @param account
     * @param sessionId
     * @return
     */
    Long queryPrimaryIdByAccountAndSessionId(@Param("account") String account, @Param("sessionId") String sessionId);

    /**
     * 根据sessionId查询syssession 信息
     * @param sessionId
     * @return
     */
    SysSession querySysSessionBySessionId(String sessionId);

    List<String> querySessionIdByAccount(String account);
}