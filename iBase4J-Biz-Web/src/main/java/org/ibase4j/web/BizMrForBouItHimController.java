package org.ibase4j.web;

/*
* 福费廷(包买他行/自行买入)
* @author xiaoshuiquan
* @date  2018.6.20
* */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.service.BizMrForBouItHimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Api(value = "福费廷(包买他行/自行买入)", description = "福费廷(包买他行/自行买入)")
@RequestMapping(value = "mrForBouItHim")
public class BizMrForBouItHimController extends BaseController{

    @Autowired
    private BizMrForBouItHimService mrForBouItHimService;

    @ApiOperation(value = "查询用户信贷员")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/getUserList/list")
    public Object getUserList(ModelMap modelMap, @RequestBody Map<String, Object> sysDept) {

        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "查询用户所在部门")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/getDeptList/list")
    public Object getDeptList(ModelMap modelMap, @RequestBody Map<String, Object> sysDept) {

        return setSuccessModelMap(modelMap);
    }


}
