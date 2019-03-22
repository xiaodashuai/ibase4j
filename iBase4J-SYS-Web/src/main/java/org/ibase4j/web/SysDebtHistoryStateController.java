package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizDebtSummary;
import org.ibase4j.service.SysDebtHistoryStateService;
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
@Api(value = "债项方案历史数据迁移状态", description = "债项方案历史数据迁移状态")
@RequestMapping(value = "debtHistoryState")
public class SysDebtHistoryStateController extends BaseController {
    @Autowired
    private SysDebtHistoryStateService sysDebtHistoryStateService;

    @ApiOperation(value = "债项方案历史数据迁移状态列表")
    @RequiresPermissions("historyState.debt.read")
    @RequestMapping(value = "/read/list", method = RequestMethod.POST)
    public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Page<?> list = sysDebtHistoryStateService.queryPage(params);
        return setSuccessModelMap(modelMap, list);
    }
    @ApiOperation(value = "修改方案数据迁移状态")
    @RequiresPermissions("historyState.debt.update")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Object debtStateUpdate(ModelMap modelMap, @RequestBody BizDebtSummary bizDebtSummary) {
        sysDebtHistoryStateService.update(bizDebtSummary);
        return setSuccessModelMap(modelMap);
    }

}
