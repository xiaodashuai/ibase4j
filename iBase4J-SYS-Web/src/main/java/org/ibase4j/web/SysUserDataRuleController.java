package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysUserDataRule;
import org.ibase4j.service.SysUserDataRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Api(value = "权限", description = "权限")
@RequestMapping(value = "/userDataRule")
public class SysUserDataRuleController extends BaseController {
    @Autowired
    private SysUserDataRuleService sysUserDataRuleService;

    @ApiOperation(value = "权限")
    @RequiresPermissions("sys.permisson.userGroup.read")
    @PostMapping(value = "/read/list")
    public Object get(ModelMap modelMap, @RequestBody Map<String, Object> sysGroup) {
        Page<?> list = sysUserDataRuleService.query(sysGroup);
        return setSuccessModelMap(modelMap, list);
    }

    @PostMapping
    @ApiOperation(value = "保存")
    @RequiresPermissions("sys.permisson.userGroup.update")
    public Object update(ModelMap modelMap, @RequestBody SysUserDataRule record) {
    	sysUserDataRuleService.update(record);
        return setSuccessModelMap(modelMap);
    }

}
