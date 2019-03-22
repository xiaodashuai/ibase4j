/**
 *
 */
package org.ibase4j.provider;

import org.ibase4j.core.base.BaseProvider;
import org.ibase4j.model.SysSession;

import java.util.List;

/**
 * @author ShenHuaJie
 * @version 2016年5月15日 上午11:21:21
 */
public interface ISysSessionProvider extends BaseProvider<SysSession> {
    public void deleteBySessionId(final String sessionId);

    public SysSession querySysSessionBySessionId(final String sessionId);

    public Long queryIdByAccount(String account);

    public Long queryPrimaryIdByAccountAndSessionId(String account,String sessionId);

    public List<String> querySessionIdByAccount(String account);

    public void cleanExpiredSessions();


}
