package org.ibase4j.web;

/*
* 保函担保
* @author xiaoshuiquan
* @date  2018.6.20
* */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.service.BizGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@Api(value = "保函担保", description = "保函担保")
@RequestMapping(value = "guarantee")
public class BizGuaranteeController extends BaseController{

    @Autowired
    private BizGuaranteeService guaranteeService;

    @ApiOperation(value = "查询勾选的产品")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/proList/list")
    public Object getproList(ModelMap modelMap, @RequestBody Map<String, Object> pro) {
      List<BizProductTypes> productList=guaranteeService.getproList(pro);
        return setSuccessModelMap(modelMap,productList);
    }

    @ApiOperation(value = "查询用户所在部门")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/getDeptList/list")
    public Object getDeptList(ModelMap modelMap, @RequestBody Map<String, Object> sysDept) {

        return setSuccessModelMap(modelMap);
    }


}
