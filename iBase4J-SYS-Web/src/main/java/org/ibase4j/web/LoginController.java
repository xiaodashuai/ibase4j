package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.ibase4j.core.Constants;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.config.Resources;
import org.ibase4j.core.config.SysConstant;
import org.ibase4j.core.exception.LoginException;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.support.login.LoginHelper;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.SysSession;
import org.ibase4j.model.SysUser;
import org.ibase4j.service.SysAuthorizeService;
import org.ibase4j.service.SysSessionService;
import org.ibase4j.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
	private SysUserService sysUserService;
	@Autowired
	private SysSessionService sysSessionService;
//	@Autowired
//	private WFLogsService wFLogsService;
	@Autowired
	private SysAuthorizeService authorizeService;

	// 登录
	@ApiOperation(value = "用户登录")
	@PostMapping("/login")
	public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody SysUser sysUser, ModelMap modelMap,
			HttpServletRequest request) {
		Assert.notNull(sysUser.getAccount(), "ACCOUNT");
		Assert.notNull(sysUser.getPassword(), "PASSWORD");
		SysUser queryByAccount = sysUserService.getByAcct(sysUser.getAccount());
		String clientIp = (String) request.getSession().getAttribute(Constants.USER_IP); // 3代表后台账户
		if (LoginHelper.login(sysUser.getAccount(), sysUser.getPassword(), clientIp)) {
			//超级管理员和系统用户可登录后台
			if(SysConstant.ADMINUSER.equals(queryByAccount.getUserType())){
				request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录成功.");
				return setSuccessModelMap(modelMap);
			}else if (SysConstant.SYSUSER.equals(queryByAccount.getUserType())) {
				request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录成功.");
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
	public Object logout(ModelMap modelMap , HttpServletRequest request) {
        Long id = SysWebUtil.getCurrentUser();
        //失效后点击退出异常 处理一下
        if(StringUtils.isEmpty(id)){
			return setSuccessModelMap(modelMap);
		}
		SysUser sysUser = sysUserService.queryById(id);
		String account = sysUser.getAccount();
		request.setAttribute("msg", "[" + sysUser.getAccount() + "]登出成功.");
		String sessionId = SecurityUtils.getSubject().getSession().getId().toString();
//		Long Id = sysSessionService.queryByAccount(account);
		Long Id = sysSessionService.queryPrimaryIdByAccountAndSessionId(account,sessionId);
        SysSession sysSession = new SysSession();
        sysSession.setAccount(account);
        sysSession.setId(Id);
//        sysSession.setEndTime(new Date());
		//会话状态  0 已登录 1 已退出 2 已过期 3 已踢出
		sysSession.setSessionState(1);
		sysSession.setEnable(0);
		sysSession.setUpdateBy(id);
		sysSession.setUpdateTime(new Date());
        sysSessionService.update(sysSession);

		if (id != null) {
//			sysSessionService.delete(id);
			// 工作流退出
//			wFLogsService.quit(id.toString());
		}
		// 系统退出
		SecurityUtils.getSubject().logout();
		// 返回
		return setSuccessModelMap(modelMap);
	}

	// 注册
	@ApiOperation(value = "用户注册")
	@PostMapping("/regin")
	public Object regin(HttpServletRequest request, ModelMap modelMap, @RequestBody SysUser sysUser) {
		Assert.notNull(sysUser.getAccount(), "ACCOUNT");
		Assert.notNull(sysUser.getPassword(), "PASSWORD");
		sysUser.setPassword(sysUserService.encryptPassword(sysUser.getPassword()));
		sysUserService.update(sysUser);
		String clientIp = (String) request.getSession().getAttribute(Constants.USER_IP);
		if (LoginHelper.login(sysUser.getAccount(), sysUser.getPassword(), clientIp)) {
			return setSuccessModelMap(modelMap);
		}
		throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
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

	// 当前管理员
	@ApiOperation(value = "当前管理员信息")
	@GetMapping(value = "/read/promission")
	public Object promission(ModelMap modelMap) {
        Long id = SysWebUtil.getCurrentUser();
		SysUser sysUser = sysUserService.queryById(id);
		List<SysMenu> menus = authorizeService.queryAuthorizeByUserId(id, sysUser.getUserType());
		modelMap.put("user", sysUser);
		modelMap.put("menus", menus);
		return setSuccessModelMap(modelMap);
	}
}
