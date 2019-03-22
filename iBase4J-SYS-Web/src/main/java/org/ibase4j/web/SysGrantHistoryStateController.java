package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.service.SysGrantHistoryStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 历史数据迁移状态控制类
 *
 * @author XiaoYu
 * @version 2018年11月4日
 */
@RestController
@Api(value = "债项发放历史数据迁移状态", description = "债项发放历史数据迁移状态")
@RequestMapping(value = "grantHistoryState")
public class SysGrantHistoryStateController extends BaseController {
    @Autowired
    private SysGrantHistoryStateService sysGrantHistoryStateService;


    @ApiOperation(value = "债项发放历史数据迁移状态列表")
    @RequiresPermissions("historyState.grant.read")
    @RequestMapping(value = "/read/list", method = RequestMethod.POST)
    public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Page<?> list = sysGrantHistoryStateService.queryPage(params);
        return setSuccessModelMap(modelMap, list);
    }
    @ApiOperation(value = "修改债项发放数据迁移状态")
    @RequiresPermissions("historyState.grant.update")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object grantStateUpdate(ModelMap modelMap, @RequestBody BizDebtGrant sysDebtGrant) {
        sysGrantHistoryStateService.update(sysDebtGrant);
        return setSuccessModelMap(modelMap);
    }

}
