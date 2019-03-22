package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.model.BizLocking;
import org.ibase4j.service.BizLockingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 锁表
 */
@RestController
@Api(value = "锁表", description = "锁表")
@RequestMapping(value = "/locking")
public class BizLockingContrller extends BaseController {
    @Autowired
    private BizLockingService bizLockingService;

    @ApiOperation(value = "保存用户记录加锁")
    @PostMapping(value = "/submitUserLog")
    public Object submitUserLog(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        BizLocking bizLocking = bizLockingService.submitUserLog(params);
        return setSuccessModelMap(modelMap,bizLocking);
    }
    @ApiOperation(value = "解锁")
    @PostMapping(value = "/unlockUser")
    public Object unlockUser(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        bizLockingService.unlockUser(params);
        return setSuccessModelMap(modelMap);
    }
}
