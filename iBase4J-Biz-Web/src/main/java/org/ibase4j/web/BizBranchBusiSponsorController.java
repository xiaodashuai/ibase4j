package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.ibase4j.service.BizBranchBusiSponsorService;

import java.util.Map;

/**
 * @Description: 分行业务经办相关
 * @Author: dj
 * @Version: 1.0
 * @CreateDate: 2018-06-12
 */
@RestController
@Api(value = "分行业务经办相关", description = "分行业务经办相关")
@RequestMapping("/branchBusiSponsor")
public class BizBranchBusiSponsorController extends BaseController {

    @Autowired
    private BizBranchBusiSponsorService bizBranchBusiSponsorService;

    @ApiOperation(value = "分行业务经办提交check表单并进行业务流转")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/update/submitCheckForm")
    public Object submitCheckForm(ModelMap modelMap,@RequestBody Map<String,Object> params){
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        bizBranchBusiSponsorService.submitCheckForm(params);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "获取债项方案概要信息")
    @RequiresPermissions("biz.workflow.read")
    @PutMapping(value = "/read/getDebtOutlineMessage")
    public Object getDebtOutlineMessage(ModelMap modelMap,@RequestBody Map<String,Object> params){
        Long userId = BizWebUtil.getCurrentUser();
        String piid =  StringUtil.objectToString(params.get("piid"));
        bizBranchBusiSponsorService.getDebtOutlineMessage(userId.toString(),piid);
        return setSuccessModelMap(modelMap);
    }
}
