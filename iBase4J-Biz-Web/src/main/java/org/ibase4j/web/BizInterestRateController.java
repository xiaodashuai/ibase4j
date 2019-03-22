package org.ibase4j.web;

import io.swagger.annotations.Api;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.BizInterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理信息
 * 
 * @author Xxiaoshuiquan
 * @date 2018/07/17
 */

@RestController
@Api(value = "管理信息", description = "管理信息")
@RequestMapping(value = "/InterestRate")
public class BizInterestRateController extends BaseController {
	@Autowired
	private BizInterestRateService bizInterestRateService;
	
	/*@ApiOperation(value = "客户静态信息查询")
	@RequiresPermissions("biz.base.cust.read")
	@PutMapping(value = "/read/list")
	public Object get(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		 Page<BizCust> query = bizCustService.query(param);
		return setSuccessModelMap(modelMap, query);
	}
	@ApiOperation(value = "客户详情查询")
	@RequiresPermissions("biz.base.cust.read")
	@PutMapping(value = "/read/detail")
	public Object getById(ModelMap modelMap, @RequestBody Map<String, Object> param) {
		List<BizCust> queryList = bizCustService.queryList(param);
		BizCust cust = new BizCust();
		if(queryList.size() == 1){
			cust = queryList.get(0);
		}
		return setSuccessModelMap(modelMap, cust);
	}*/

	
}
