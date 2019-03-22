/**
 *
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.BizSession;

import java.util.List;

/**
 * @author ShenHuaJie
 * @version 2016年5月15日 上午11:21:21
 */
public interface IBizSessionProvider extends BaseProvider<BizSession> {
    void deleteBySessionId(final String sessionId);

    List<String> querySessionIdByAccount(String account);

    void cleanExpiredSessions();

    Long queryIdByAccount(String account);

    BizSession queryOneBySessionId(String id);

    BizSession queryOneByAccount(String account);
}
