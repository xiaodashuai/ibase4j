package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysDataRule;
import org.ibase4j.service.SysDataRuleService;
import org.ibase4j.vo.SysDataRuleCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数管理
 * 
 * @author xiaoshuiquan
 * @version 1.0
 * @date 2018.2.06 
 */
@RestController
@Api(value = "访问控制", description = "访问控制")
@RequestMapping(value = "dataRule")
public class SysDataRuleController extends BaseController {
	@Autowired
	private SysDataRuleService sysDataRuleService;

	@PostMapping(value = "/read/list")
	@ApiOperation(value = "查询数据权限")
	@RequiresPermissions("sys.permisson.dataRule.read")
	public Object get(ModelMap modelMap, @RequestBody(required = false) Map<String, Object> sysDataRule) {
		Page<?> list = sysDataRuleService.query(sysDataRule);
		return setSuccessModelMap(modelMap, list);
	}

	@PostMapping(value = "/read/detail")
	@ApiOperation(value = "数据权限详情")
	@RequiresPermissions("sys.permisson.dataRule.read")
	public Object detail(ModelMap modelMap, @RequestBody SysDataRule dataRule) {
		SysDataRule record = sysDataRuleService.queryById(dataRule.getId());
		return setSuccessModelMap(modelMap, record);
	}

	@PostMapping
	@ApiOperation(value = "修改数据权限")
	@RequiresPermissions("sys.permisson.dataRule.update")
	public Object update(@RequestBody SysDataRule record, ModelMap modelMap) {
		sysDataRuleService.update(record);
		return setSuccessModelMap(modelMap);
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除数据权限")
	@RequiresPermissions("sys.permisson.dataRule.delete")
	public Object delete(@RequestBody SysDataRule dataRule, ModelMap modelMap) {
		sysDataRuleService.delete(dataRule.getId());
		return setSuccessModelMap(modelMap);
	}

	@ApiOperation(value = "启用数据权限")
	@RequiresPermissions("sys.permisson.dataRule.update")
	@PostMapping(value="/update/changeDataRule")
	public Object changeDateRule( ModelMap modelMap, @RequestBody SysDataRule record) {
		sysDataRuleService.update(record);
		return setSuccessModelMap(modelMap);
	}
	
	@ApiOperation(value = "查询已绑定的机构访问权限")
	@RequiresPermissions("sys.permisson.dataRule.read")
	@PostMapping(value = "/read/getDeptDatarule")
	public Object getDeptDatarule(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long deptId = Long.valueOf(params.get("deptId").toString());
		List<SysDataRuleCheckVo> list = sysDataRuleService.queryDataruleByDeptId(deptId);
		return setSuccessModelMap(modelMap, list);
	}


	/**
	 *  @author gby
	 *  date   2018-05-09
	 * @param modelMap
	 * @param params
	 * @return
	 */
	@ApiOperation(value = "查询用户已绑定的数据权限")
	@RequiresPermissions("sys.permisson.dataRule.read")
	@PostMapping(value = "/read/getUserDataRuleList")
	public Object getUserDataRule(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long userId = Long.valueOf(params.get("userId").toString());
		List<SysDataRuleCheckVo> list = sysDataRuleService.queryDataruleByUserId(userId);
		return setSuccessModelMap(modelMap, list);
	}

	/**
	 * @author   gby
	 * @date   2018-05-10
	 * @param modelMap
	 * @param params
	 * @return
	 */
	@ApiOperation(value = "保存用户与数据权限")
	@RequiresPermissions("sys.permisson.dataRule.update")
	@PostMapping(value="/read/saveUserDataRule")
	public Object saveUserDataRule(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long userId = Long.valueOf(params.get("userId").toString());
		String dataRuleIds=(String) params.get("DataRuleIds");
		sysDataRuleService.saveUserDataRule(dataRuleIds, userId);
		return setSuccessModelMap(modelMap);
	}

	
	@ApiOperation(value = "保存机构权限")
	@RequiresPermissions("sys.permisson.dataRule.update")
	@PostMapping(value="/read/saveDeptDataRule")
	public Object saveDeptDataRule(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Long deptId = Long.valueOf(params.get("deptId").toString());
		String dataRuleIds=(String) params.get("dataRuleIds");
		sysDataRuleService.saveDeptDataRule(dataRuleIds, deptId);
		return setSuccessModelMap(modelMap);
	}
	 //检查 权限code是否已存在
  	@ApiOperation(value = "检查 权限code是否已存在")
  	@RequiresPermissions("sys.permisson.dataRule.read")
  	@GetMapping(value = "read/code") 
  	@ResponseBody
  	public Object selectDataRuleByCode(ModelMap modelMap, String account) {
  		Map<String,Object> resultMap = new HashMap<String, Object>();  
  		 Boolean b = sysDataRuleService.queryByCode(account);
  		 resultMap.put("accounts", b);
  		return resultMap;
  	}

}
