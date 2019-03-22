package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.util.SysWebUtil;
import org.ibase4j.model.SysUser;
import org.ibase4j.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员管理控制器
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:12:12
 */
@RestController
@Api(value = "管理员管理", description = "管理员管理")
@RequestMapping(value = "/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;

	// 新增管理员
	@ApiOperation(value = "新增管理员")
	@RequiresPermissions("sys.base.user.update")
	@PostMapping
	public Object updateSys(ModelMap modelMap, @RequestBody SysUser sysUser) {
		Assert.isNotBlank(sysUser.getAccount(), "ACCOUNT");
		Assert.length(sysUser.getAccount(), 3, 15, "ACCOUNT");
		String deptCode = sysUser.getDeptCode();
		String substring = deptCode.substring(0, 5);
		sysUser.setOrgCode(substring+"00");
		sysUser.setUserType("2");
		sysUserService.updateUserInfo(sysUser);
		return setSuccessModelMap(modelMap);
	}
	// 新增柜员
	@PostMapping("update/counter")
	@ApiOperation(value = "新增柜员")
	@RequiresPermissions("sys.base.user.update")
	public Object updateCounter(ModelMap modelMap, @RequestBody SysUser sysUser) {
		Assert.isNotBlank(sysUser.getAccount(), "ACCOUNT");
		Assert.length(sysUser.getAccount(), 3, 15, "ACCOUNT");
		String deptCode = sysUser.getDeptCode();
		String substring = deptCode.substring(0, 5);
		sysUser.setOrgCode(substring+"00");
		sysUser.setUserType("1");
		sysUserService.updateUserInfo(sysUser);
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "修改个人信息")
	@RequiresPermissions("sys.base.user.update")
	@PostMapping(value = "/update/person")
	public Object updatePerson(ModelMap modelMap, @RequestBody SysUser sysUser) {
        sysUser.setId(SysWebUtil.getCurrentUser());
		Assert.isNotBlank(sysUser.getAccount(), "ACCOUNT");
		Assert.length(sysUser.getAccount(), 3, 15, "ACCOUNT");
		sysUserService.updateUserInfo(sysUser);
		return setSuccessModelMap(modelMap);
	}

	// @ApiOperation(value = "修改管理员头像")
	// @RequiresPermissions("sys.base.user.update")
	// @PostMapping(value = "/update/avatar")
	// public Object updateAvatar(HttpServletRequest request, ModelMap modelMap)
	// {
	// List<String> fileNames = UploadUtil.uploadImage(request, false);
	// if (fileNames.size() > 0) {
	// SysUser sysUser = new SysUser();
    // sysUser.setId(SysWebUtil.getCurrentUser());
	// String filePath = UploadUtil.getUploadDir(request) + fileNames.get(0);
	// // String avatar = UploadUtil.remove2DFS("sysUser", "user" +
	// // sysUser.getId(), filePath).getRemotePath();
	// // String avatar = UploadUtil.remove2Sftp(filePath, "user" +
	// // sysUser.getId());
	// sysUser.setAvatar(filePath);
	// sysUserService.updateUserInfo(sysUser);
	// return setSuccessModelMap(modelMap);
	// } else {
	// setModelMap(modelMap, HttpCode.BAD_REQUEST);
	// modelMap.put("msg", "请选择要上传的文件！");
	// return modelMap;
	// }
	// }

	// 修改密码
	@ApiOperation(value = "修改密码")
	@RequiresPermissions("sys.base.user.update")
	@PostMapping(value = "/update/password")
	public Object updatePassword(ModelMap modelMap, @RequestBody SysUser sysUser) {
        sysUserService.updatePassword(SysWebUtil.getCurrentUser(), sysUser.getOldPassword(), sysUser.getPassword());
		return setSuccessModelMap(modelMap);
	}

	// 查询管理员
	@ApiOperation(value = "查询管理员")
	@RequiresPermissions("sys.base.user.read")
	@PostMapping(value = "/read/list")
	public Object getSystemUser(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long currentUserId = SysWebUtil.getCurrentUser();
		SysUser sysUser = sysUserService.queryById(currentUserId);
		String deptCode1 = sysUser.getDeptCode();
		String substring = deptCode1.substring(0, 5);
		if (!"10000".equals(substring)) {
			params.put("deptCode" , substring);
		}
		Page<?> list = sysUserService.getList(params);
		return setSuccessModelMap(modelMap, list);
	}

	// 管理员详细信息
	@ApiOperation(value = "管理员详细信息")
	@RequiresPermissions("sys.base.user.read")
	@PostMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody SysUser sysUser) {
		sysUser = sysUserService.queryById(sysUser.getId());
		if (sysUser != null) {
			sysUser.setPassword(null);
		}
		return setSuccessModelMap(modelMap, sysUser);
	}

	// 检查管理员是否重复
	@ApiOperation(value = "检查管理员是否重复")
	@RequiresPermissions("sys.base.user.read")
	@GetMapping(value = "/read/checkOut")
	@ResponseBody
	public Object checkOut(ModelMap modelMap, String account) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysUserService.queryByAccount(account);
		resultMap.put("accounts", b);
		return resultMap;
	}

	// 检查工号是否重复
	@ApiOperation(value = "检查工号是否重复")
	@RequiresPermissions("sys.base.user.read")
	@GetMapping(value = "/read/staffNo")
	@ResponseBody
	public Object staffNo(ModelMap modelMap, String account) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean b = sysUserService.queryByStaffNo(account);
		resultMap.put("accounts", b);
		return resultMap;
	}

	// 管理员详细信息
	@ApiOperation(value = "删除管理员")
	@RequiresPermissions("sys.base.user.delete")
	@DeleteMapping
	public Object delete(ModelMap modelMap, @RequestBody SysUser sysUser) {
		sysUserService.delete(sysUser.getId());
		return setSuccessModelMap(modelMap);
	}

	// 当前管理员
	@ApiOperation(value = "当前管理员信息")
	@GetMapping(value = "/read/current")
	public Object current(ModelMap modelMap) {
        Long id = SysWebUtil.getCurrentUser();
		SysUser sysUser = sysUserService.queryById(id);
		if (sysUser != null) {
			sysUser.setPassword(null);
		}
		return setSuccessModelMap(modelMap, sysUser);
	}

	// 当前管理员
	@ApiOperation(value = "启用禁止管理员")
	@PostMapping(value = "/update/state")
	public Object state(ModelMap modelMap, @RequestBody SysUser sysUser) {
		sysUserService.update(sysUser);
		return setSuccessModelMap(modelMap, sysUser);
	}
}
