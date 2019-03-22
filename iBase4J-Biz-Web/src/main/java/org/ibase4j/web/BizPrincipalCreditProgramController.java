package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.BizPrincipalCreditProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * 客户授信主体信息
 * 
 * @author xy
 * @date 2018/05/14
 */

@RestController
@Api(value = "客户授信主体信息", description = "客户授信主体信息")
@RequestMapping(value = "/principalCreditProgram")
public class BizPrincipalCreditProgramController extends BaseController {
	@Autowired
	private BizPrincipalCreditProgramService bizPrincipalCreditProgramService;
	
	// 调用核心系统接口通过客户ID查询客户的所有授信信息并保存到数据库
		@ApiOperation(value = "调用核心系统接口通过客户ID查询客户的所有授信信息")
		@RequiresPermissions("biz.principalCreditProgram.read")
		@PutMapping(value = "/read/list")
		/*
		 * @Param  
		 * param中获取的参数是custNo
		 */
		public Object detail(ModelMap modelMap, @RequestBody Map<String , Object> param) {
			Page<?> allPrincipalCreditProgram = bizPrincipalCreditProgramService.getAllPrincipalCreditProgram(param);
			return setSuccessModelMap(modelMap, allPrincipalCreditProgram);
		}
		// 将获取的客户授信信息保存到数据库
		@PostMapping
		@ApiOperation(value = "将获取的客户授信信息保存到数据库")
		@RequiresPermissions("biz.principalCreditProgram.add")
		public Object add(ModelMap modelMap, @RequestBody Map<String , Object> bizPrincipalCreditProgramMap) {
			bizPrincipalCreditProgramService.addBizPrincipalCreditPrograms(bizPrincipalCreditProgramMap);
			return setSuccessModelMap(modelMap);
		}
}
