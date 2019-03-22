/**
 * 
 */
package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.service.BizDeptGrantService;
import org.ibase4j.service.BizGrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 描述：债项发放数据迁移
 * 
 * @author czm
 * @date 2018/9/24
 * @version 1.0
 */
@RestController
@Api(value = "债项发放申请数据迁移", description = "债项发放申请")
@RequestMapping(value = "/migratioin")
public class BizGrantMigrationController extends BaseController {

	// 债项发放
	@Autowired
	private BizGrantService bizGrantService;
	// 债项发放
	@Autowired
	private BizDeptGrantService bizDeptGrantService;

	@ApiOperation(value = "发放数据迁移列表")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/read/list")
	public Object getList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Page<?> list = bizDeptGrantService.queryMigrList(params);
		return setSuccessModelMap(modelMap, list);
	}
	
	@ApiOperation(value = "迁移的发放数据进行修改")
	@RequiresPermissions("event.base.grant.read")
	@PutMapping(value = "/getGrant")
	public Object getGrant(ModelMap modelMap, @RequestBody Map<String, Object> params) {
		Map<String, Object> map = bizGrantService.getEditGrant(params);
		return setSuccessModelMap(modelMap, map);
	}

	
	@ApiOperation(value = "保存迁移的数据")
	@RequiresPermissions("event.base.grant.read")
	@PostMapping(value = "/save")
	public Object saveChange(ModelMap modelMap, @RequestBody Map<String, Object> record) {
		BizDebtGrant model = bizGrantService.updateNoFw(record);
		return setSuccessModelMap(modelMap);
	}
	

}
