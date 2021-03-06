package org.ibase4j.core.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.ibase4j.core.exception.LoginException;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysSession;
import org.ibase4j.model.SysUser;
import org.ibase4j.service.SysAuthorizeService;
import org.ibase4j.service.SysSessionService;
import org.ibase4j.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 权限检查类
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:44:45
 */
public class Realm extends AuthorizingRealm {
    private final Logger logger = LogManager.getLogger();

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSessionService sysSessionService;
    @Autowired
    private SysAuthorizeService sysAuthorizeService;
//	@Autowired
//	private WFLogsService wFLogsService;

    // 权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Long userId = SysWebUtil.getCurrentUser();
        List<String> list = sysAuthorizeService.queryPermissionByUserId(userId);
        for (String permission : list) {
            if (StringUtils.isNotBlank(permission)) {
                // 添加基于Permission的权限信息
                info.addStringPermission(permission);
            }
        }
        // 添加用户权限
        info.addStringPermission("user");
        return info;
    }

    // 登录验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("countSql", 0);
        params.put("enable", 1);
        params.put("account", token.getUsername());
        SysUser user = sysUserService.queryOne(params);
        if (user != null) {
            SysWebUtil.saveCurrentUser(user.getId());
//			SysWebUtil.saveCurrentUserObject(user);
            //创建以及更新bizSession
            saveSession(user.getAccount(), token.getHost());
            //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
            String password = new String(token.getPassword());
//            盐值是用户名  和 未加密的密码
            String salt = token.getUsername() + password;
//            logger.debug(salt);
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.getAccount(), //用户名
                    user.getPassword(), //数据库密码
                    ByteSource.Util.bytes(salt),//salt=username+password
                    super.getName() //realm name
            );
            // matrix 登录认证
//				Long userId = user.getId();
//				String deptCode = user.getDeptCode();
//				List<String> deptIds = new ArrayList<String>();
//				deptIds.add(deptCode);
//				List<String> totalDeptIds = sysAuthorizeService.queryDeptParentIdByDeptCode(deptCode);
//				List<String> mfRoles = sysAuthorizeService.getMFRole(userId);
            // 通知工作流引擎登录成功
//				wFLogsService.signon(userId.toString(), deptIds, totalDeptIds, mfRoles);
            return authenticationInfo;
        } else {
            logger.warn("No user: {}", token.getUsername());
            return null;
        }
    }

    /**
     * 保存session
     */
    private void saveSession(String account, String host) {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String currentSessionId = session.getId().toString();
			// 踢出用户
//			sysSessionService.deleteByAccount(account, currentSessionId);
			SysSession record = new SysSession();
			record.setAccount(account);
			record.setSessionId(currentSessionId);
			record.setIp(StringUtils.isBlank(host) ? session.getHost() : host);
			record.setStartTime(session.getStartTimestamp());
            record.setSessionState(0);
            record.setEnable(1);
			sysSessionService.update(record);
		} catch (Exception ex) {
			logger.error(new LoginException("save session error:" + ex.getMessage()));
		}
    }
}
