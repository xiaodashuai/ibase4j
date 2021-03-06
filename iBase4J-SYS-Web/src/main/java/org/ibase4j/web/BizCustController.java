package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizCust;
import org.ibase4j.service.BizCustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
/**
 * 客户静态信息查询
 * 
 * @author XiaoYu
 * @date 2018/06/27
 */

@RestController
@Api(value = "客户静态信息查询", description = "客户静态信息查询")
@RequestMapping(value = "/cust")
public class BizCustController extends BaseController {
	@Autowired
	private BizCustService bizCustService;
	
	@ApiOperation(value = "客户静态信息查询")
	@RequiresPermissions("sys.base.cust.read")
	@PostMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		 Page<BizCust> query = bizCustService.query (param);
		return setSuccessModelMap(modelMap, query);
	}
	@ApiOperation(value = "客户详情查询")
	@RequiresPermissions("sys.base.cust.read")
	@PostMapping(value = "/read/detail")
	public Object getById(ModelMap modelMap, @RequestBody Map<String , Object> params) {
		BizCust queryById = bizCustService.queryOne(params);
		return setSuccessModelMap(modelMap, queryById);
	}

	
}
