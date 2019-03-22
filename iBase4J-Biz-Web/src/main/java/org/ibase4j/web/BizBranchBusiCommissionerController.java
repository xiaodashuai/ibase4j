package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.ibase4j.service.BizBranchBusiCommissionerService;

import java.util.Map;

/**
 * @Description: 分行业务处长相关
 * @Author: dj
 * @Version: 1.0
 * @CreateDate: 2018-06-12
 */
@RestController
@Api(value = "分行业务处长相关", description = "分行业务处长相关")
@RequestMapping("/branchBusiCommissioner")
public class BizBranchBusiCommissionerController extends BaseController {

    @Autowired
    private BizBranchBusiCommissionerService bizBranchBusiCommissionerService;

    @ApiOperation(value = "获得分行业务待办提交的check值")
    @RequiresPermissions("biz.workflow.read")
    @PutMapping(value = "/read/getCheckValues")
    public Object getCheckValues(ModelMap modelMap,@RequestBody Map<String,Object> params){
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        Map<String, Object> checkValues = bizBranchBusiCommissionerService.getCheckValues(params);
        return setSuccessModelMap(modelMap,checkValues);
    }

    @ApiOperation(value = "分行业务处长提交表单")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/update/submitCheckForm")
    public Object submitCheckForm(ModelMap modelMap,@RequestBody Map<String,Object> params){
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        Map<String, Object> checkValues = bizBranchBusiCommissionerService.getCheckValues(params);
        return setSuccessModelMap(modelMap,checkValues);
    }

}
