package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.config.BizContant;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.BizDebtGrant;
import org.ibase4j.model.BizMakeLoans;
import org.ibase4j.model.SysUser;
import org.ibase4j.service.BizDeptGrantService;
import org.ibase4j.service.BizMakeLoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 放款操作
 * @Author: dj
 * @CreateDate: 2018-08-02
 */
@RestController
@Api(value = "放款操作", description = "放款操作")
@RequestMapping("/makeLoans")
public class BizMakeLoansController extends BaseController{

    @Autowired
    private BizDeptGrantService bizDebtGrantService;
    @Autowired
    private BizMakeLoansService bizMakeLoansService;


    @ApiOperation(value = "查询满足放款条件的债项发放方案")
    @RequiresPermissions("biz.makeloans.read")
    @PutMapping(value = "/read/getMakeLoansDebts")
    public Object getMakeLoansDebts(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId",userId);
        params.put("enable","1");
        Page makeLoansDebts = bizDebtGrantService.getMakeLoansDebts(params);
        return setSuccessModelMap(modelMap,makeLoansDebts);
    }

    @ApiOperation(value = "查询债项方案信息")
    @RequiresPermissions("biz.makeloans.read")
    @PutMapping(value = "/read/getMakeLoansDebtInfo")
    public Object getMakeLoansDebtInfo(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Map makeLoansDebtInfo = bizMakeLoansService.getMakeLoansDebtInfo(params);
        return setSuccessModelMap(modelMap,makeLoansDebtInfo);
    }

    @ApiOperation(value = "查询还款计划信息")
    @RequiresPermissions("biz.makeloans.read")
    @PutMapping(value = "/read/getRepaymentInfo")
    public Object getRepaymentInfo(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Map repaymentInfo = bizMakeLoansService.getRepaymentInfo(params);
        return setSuccessModelMap(modelMap,repaymentInfo);
    }

    @ApiOperation(value = "请求发放相关接口")
    @RequiresPermissions("biz.makeloans.read")
    @PostMapping(value = "/update/requestMakeLoansInterface")
    public Object makeLoans(ModelMap modelMap, @RequestBody Map<String,Object> params) {
        SysUser sysUser = BizWebUtil.getCurrentUserObject();
        params.put("sysUser",sysUser);
        String result = bizMakeLoansService.requestMakeLoansInterface(params);
        Map resultMap = new HashMap();
        resultMap.put("result",result);
        return setSuccessModelMap(modelMap,resultMap);
    }

    @ApiOperation(value = "发送还款计划")
    @RequiresPermissions("biz.makeloans.read")
    @PostMapping(value = "/update/sendRepaymentPlan")
    public Object sendRepaymentPlan(ModelMap modelMap, @RequestBody Map<String,Object> params) {
        params.put("userId", BizWebUtil.getCurrentUser());
        params.put("sysUser", BizWebUtil.getCurrentUserObject());
        String result = bizMakeLoansService.sendRepaymentPlan(params);
        Map resultMap = new HashMap();
        resultMap.put("result",result);
        return setSuccessModelMap(modelMap,resultMap);
    }

    @ApiOperation(value = "发放审核更改以后发送还款计划")
    @RequiresPermissions("biz.makeloans.read")
    @PostMapping(value = "/update/updateRepaymentPlan")
    public Object updateRepaymentPlan(ModelMap modelMap, @RequestBody Map<String,Object> params) {
        params.put("userId", BizWebUtil.getCurrentUser());
        params.put("sysUser", BizWebUtil.getCurrentUserObject());
        String result = bizMakeLoansService.updateRepaymentPlan(params);
        Map resultMap = new HashMap();
        resultMap.put("result",result);
        return setSuccessModelMap(modelMap,resultMap);
    }

    @ApiOperation(value = "查询废弃放款信息")
    @RequiresPermissions("biz.makeloans.read")
    @PostMapping(value = "/read/getDiscardMakeLoansInfo")
    public Object getGrantDiscardInfo(ModelMap modelMap, @RequestBody Map<String,Object> params) {
        Map resultMap = bizMakeLoansService.getDiscardMakeLoansInfo(params);
        return setSuccessModelMap(modelMap,resultMap);
    }

    @ApiOperation(value = "废弃放款")
    @RequiresPermissions("biz.makeloans.read")
    @PostMapping(value = "/update/discardMakeLoans")
    public Object discardMakeLoans(ModelMap modelMap, @RequestBody Map<String,Object> params) {
        params.put("userId", BizWebUtil.getCurrentUser());
        String result = bizMakeLoansService.discardMakeLoans(params);
        Map resultMap = new HashMap();
        resultMap.put("result",result);
        return setSuccessModelMap(modelMap,resultMap);
    }
}
