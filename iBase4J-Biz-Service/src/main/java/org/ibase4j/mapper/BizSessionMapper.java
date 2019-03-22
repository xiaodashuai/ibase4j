package org.ibase4j.mapper;

import org.ibase4j.core.base.BaseMapper;
import org.ibase4j.model.BizSession;

import java.util.List;

public interface BizSessionMapper extends BaseMapper<BizSession> {

    void deleteBySessionId(String sessionId);
    Long queryIdByAccount(String account);
    Long queryBySessionId(String sessionId);
    List<String> querySessionIdByAccount(String account);
    BizSession queryOneBySessionId(String id);
    BizSession queryOneByAccount(String account);

}