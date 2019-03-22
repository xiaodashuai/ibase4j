package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.config.Resources;
import org.ibase4j.core.exception.LoginException;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.support.ISysEventService;
import org.ibase4j.core.support.login.LoginHelper;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.DateUtil;
import org.ibase4j.model.BizSession;
import org.ibase4j.model.SysEvent;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.SysUser;
import org.ibase4j.service.BizAuthorizeService;
import org.ibase4j.service.BizSessionService;
import org.ibase4j.service.BizUserService;
import org.ibase4j.service.WFLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ibase4j.core.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:11:21
 */
@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends BaseController {
    @Autowired
    private BizUserService bizUserService;
    @Autowired
    private BizSessionService bizSessionService;
    @Autowired
    private WFLogsService wFLogsService;
    @Autowired
    private BizAuthorizeService authorizeService;
    @Autowired
    private ISysEventService sysEventService;

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody SysUser sysUser, ModelMap modelMap,
                        HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assert.notNull(sysUser.getAccount(), "ACCOUNT");
        Assert.notNull(sysUser.getPassword(), "PASSWORD");
        String password=new String(Base64.decode(new String(Base64.decode(sysUser.getPassword().trim()))));
        //查询账号信息
        SysUser queryByAccount = bizUserService.getByAcct(sysUser.getAccount());
        String clientIp = (String) request.getSession().getAttribute(Constants.USER_IP);
        //验证登录
        if (LoginHelper.login(sysUser.getAccount(), password, clientIp)) {
            //判断账号类型  是否是前台普通管理员
            if (BizContant.BIZUSER.equals(queryByAccount.getUserType())) {
                //查询登录日志  判断登录时间
                List<SysEvent> list = sysEventService.queryByAccount(queryByAccount.getAccount());
                //session管理 修改session状态为失效  结束时间 修改时间
            /*    BizSession bizSession = bizSessionService.queryOneByAccount(sysUser.getAccount());
                if (bizSession != null) {
//                    bizSession.setEndTime(new Date());
                    bizSession.setUpdateBy(BizWebUtil.getCurrentUser());
                    bizSession.setUpdateTime(new Date());
                    bizSession.setEnable(0);
                    bizSessionService.update(bizSession);
                    HttpSession session = MySessionContext.getSession(bizSession.getSessionId());
                    //session 失效
                    if (!StringUtils.isEmpty(session)) {
                        session.invalidate();
                    }
                }*/

                Date date = new Date();
                if (list == null || list.size() == 0) {
                    request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录失败.");
                    throw new LoginException("新账户登录前请重新设置密码");
                } else if (date.getTime() - list.get(0).getCreateTime().getTime() > 2592000000L) {
//				if(date.getTime()-list.get(0).getCreateTime().getTime() > 1L){
                    request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录失败.");
                    throw new LoginException("账户超过30天未登录,请重新设置密码");
                }
                request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录成功.");
//                long creationTime = request.getSession().getCreationTime();
//                Date date1 = new Date(creationTime);
//                String format = DateUtil.format(date1, "yyyy-MM-dd HH:mm:SS");
//                Cookie sessionBeginTime = new Cookie("sessionBeginTime", format);
//                sessionBeginTime.setMaxAge(60 * 60 * 24 * 7);
//                response.addCookie(sessionBeginTime);
//                request.getInputStream().close();
                return setSuccessModelMap(modelMap);
            } else {
                request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录失败.");
                throw new LoginException("没有登录权限");
            }
        }
        request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }


    // 登出
    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public Object logout(ModelMap modelMap, HttpServletRequest request) {
        Long id = BizWebUtil.getCurrentUser();
        if (id != null) {
            SysUser user = bizUserService.queryById(id);
            String account = user.getAccount();
            String currentSessionId = SecurityUtils.getSubject().getSession().getId().toString();
            //bizSessionService.deleteByAccount(account, currentSessionId);
            try {
//                BizSession bizSession = bizSessionService.queryOneByAccount(account);
                BizSession bizSession = bizSessionService.queryBySessionId(currentSessionId);
//                bizSession.setEndTime(new Date());
                bizSession.setUpdateBy(BizWebUtil.getCurrentUser());
                bizSession.setUpdateTime(new Date());
                bizSession.setEnable(0);
                bizSessionService.update(bizSession);
                //删除redis中对应的  "iBase4J:bizSession:数据BIZ_SESSION sessionID"
                bizSessionService.deleteFromRedis(bizSession.getId());
            } catch (Exception e) {
                logger.warn("logout 账号 {} 退出处理异常", account);
                e.printStackTrace();
            }
            // 工作流退出
            wFLogsService.quit(id.toString());
        }
        // 系统退出
        SecurityUtils.getSubject().logout();
        // 返回
        return setSuccessModelMap(modelMap);
    }

    //修改密码
    @ApiOperation(value = "修改密码")
    @PostMapping("/system/changePassword")
    public Object changePassword(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        String account = params.get("account").toString();
        String oldPassword = params.get("password").toString();
        String newPassword = params.get("newPassword").toString();
        Map<String, Object> map = new HashMap<String, Object>();
        String msg = "";
        String flag = bizUserService.updatePassword(account, oldPassword, newPassword);
        if (flag == "1") {
            SysEvent sysEvent = new SysEvent();
            sysEvent.setAccount(account);
            SysUser sysUser = bizUserService.queryOne(params);
            sysEvent.setCreateBy(sysUser.getId());
            sysEvent.setCreateTime(new Date());
            sysEvent.setEnable(1);
            sysEventService.update(sysEvent);
            msg = "修改成功,请用新密码登录系统";
        } else if (flag == "2") {
            msg = "修改失败,登录名或原密码输入错误";
        } else {
            msg = "服务器故障,请联系系统管理员";
        }
        map.put("massage", msg);
        return setSuccessModelMap(modelMap, map);
    }

    @ApiOperation(value = "session过期监控")
    @PostMapping("/sessionEnd")
    public Object sessionEnd(ModelMap modelMap, HttpServletRequest request) {
        Long currentUser = BizWebUtil.getCurrentUser();
        SysUser sysUser = bizUserService.queryById(currentUser);
//        BizSession bizSession = bizSessionService.queryOneByAccount(sysUser.getAccount());
        String currentSessionId = SecurityUtils.getSubject().getSession().getId().toString();
        BizSession bizSession = bizSessionService.queryBySessionId(currentSessionId);

        bizSession.setUpdateBy(BizWebUtil.getCurrentUser());
        bizSession.setUpdateTime(new Date());
        bizSession.setEnable(0);
        bizSessionService.update(bizSession);
        return setSuccessModelMap(modelMap);
    }

    // 注册
    @ApiOperation(value = "用户注册")
    @PostMapping("/regin")
    public Object regin(HttpServletRequest request, ModelMap modelMap, @RequestBody SysUser sysUser) {
        Assert.notNull(sysUser.getAccount(), "ACCOUNT");
        Assert.notNull(sysUser.getPassword(), "PASSWORD");
        sysUser.setPassword(bizUserService.encryptPassword(sysUser.getPassword()));
        bizUserService.update(sysUser);
        String clientIp = (String) request.getSession().getAttribute(Constants.USER_IP);
        if (LoginHelper.login(sysUser.getAccount(), sysUser.getPassword(), clientIp)) {
            return setSuccessModelMap(modelMap);
        }
        throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 当前用户
    @ApiOperation(value = "当前用户信息")
    @PostMapping(value = "/read/promission")
    public Object promission(ModelMap modelMap) {
        Long id = BizWebUtil.getCurrentUser();
        SysUser sysUser = bizUserService.queryById(id);
        if (sysUser != null) {
            sysUser.setPassword(null);
        }
        List<SysMenu> menus = authorizeService.queryAuthorizeByUserId(id, sysUser.getUserType());
        List<String> roleCodes = authorizeService.queryRoleCodesByUserId(sysUser.getId());
        modelMap.put("roleCodes", roleCodes);
        modelMap.put("user", sysUser);
        modelMap.put("menus", menus);
        return setSuccessModelMap(modelMap);
    }

    // 没有登录
    @ApiOperation(value = "没有登录")
    @RequestMapping("/unauthorized")
    public Object unauthorized(ModelMap modelMap) {
        SecurityUtils.getSubject().logout();
        return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
    }

    // 没有权限
    @ApiOperation(value = "没有权限")
    @RequestMapping("/forbidden")
    public Object forbidden(ModelMap modelMap) {
        return setModelMap(modelMap, HttpCode.FORBIDDEN);
    }
}
