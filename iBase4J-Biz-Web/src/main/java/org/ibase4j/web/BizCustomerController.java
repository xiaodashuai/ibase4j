package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizCust;
import org.ibase4j.service.BizCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 客户主体信息
 * 
 * @author xy
 * @date 2018/05/14
 */

@RestController
@Api(value = "客户主体信息", description = "客户主体信息")
@RequestMapping(value = "/customer")
public class BizCustomerController extends BaseController {
	@Autowired
	private BizCustomerService bizCustomerService;
	
	// 调用核心系统接口通过ID查询客户主体信息
	@ApiOperation(value = "调用核心系统接口通过ID查询客户主体信息")
	@RequiresPermissions("biz.customer.read")
	@PutMapping(value = "/read/detail")
	public Object detail(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
		//先查询本地数据库,看是否有该客户信息.
		Page<BizCust> record = bizCustomerService.query(customer);
		if(record.getRecords().size() == 0){
			//如果本地数据库没有该客户信息,调用核心接口查询
			Page<BizCust> recordForCore = bizCustomerService.query(customer);
			return setSuccessModelMap(modelMap, recordForCore);
			}
		return setSuccessModelMap(modelMap, record);
	}
	// 将获取的客户主体信息保存到数据库
	@PostMapping
	@ApiOperation(value = "将获取的客户主体信息保存到数据库")
	@RequiresPermissions("biz.customer.add")
	public Object add(ModelMap modelMap, @RequestBody BizCust bizCustomer) {
		bizCustomerService.add(bizCustomer);
		return setSuccessModelMap(modelMap);
	}
	// 查询客户主体详细信息
		@ApiOperation(value = "调用核心系统接口通过ID查询客户主体详细信息")
		@RequiresPermissions("biz.customer.read")
		@PutMapping(value = "/read/detail/details")
		public Object details(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
			//先查询本地数据库,看是否有该客户信息.
			List record = bizCustomerService.queryByCustNo(customer);
			logger.debug(customer.get("custNo"));
			return setSuccessModelMap(modelMap, record);
		}
		// 更新本地信息
		@ApiOperation(value = "调用核心系统接口更新本地已保存的客户信息")
		@RequiresPermissions("biz.customer.update")
		@PutMapping(value = "/read/detail/update")
		public Object updateCustomer(ModelMap modelMap, @RequestBody BizCust customer) {
			//去核心系统查询客户主体信息,然后update
			List queryByCustNo = bizCustomerService.queryByCustNo(customer.getCustNo());
			bizCustomerService.update((BizCust)queryByCustNo.get(0));
			return setSuccessModelMap(modelMap);
		}
}
