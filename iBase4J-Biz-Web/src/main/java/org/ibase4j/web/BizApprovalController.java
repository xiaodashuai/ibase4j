package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;

import org.ibase4j.model.BizApprovalWorkflowTask;
import org.ibase4j.service.BizApprovalService;
import org.ibase4j.vo.SumInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;

/**
 * @Description: 审批相关
 * @Author: dj
 * @CreateDate: 2018-08-08
 */
@RestController
@Api(value = "审批相关", description = "审批相关")
@RequestMapping(value = "/approval")
public class BizApprovalController extends BaseController{

    @Autowired
    private BizApprovalService bizApprovalService;
    
    @ApiOperation(value = "保存债项发放审批check项的值及修改状态")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/update/submitApprovalAndUpdateStatus")
    public Object submitApprovalAndUpdateStatus(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        String resply = bizApprovalService.submitApprovalForm(params);
        modelMap.put("resply",resply);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "查询历史记录")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getHistoryCommentInfo")
    public Object getHistoryCommentInfo(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Page<BizApprovalWorkflowTask> historyCommentPage = bizApprovalService.getHistoryCommentPage(params);
        return setSuccessModelMap(modelMap,historyCommentPage);
    }

    @ApiOperation(value = "根据审批流程id查询check项的值")
    @RequiresPermissions("biz.workflow.read")
    @PutMapping(value = "/read/getCkeckValueByApprovalId")
    public Object getCkeckValueByApprovalId(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Map<String, Object> cheakValues = bizApprovalService.getCheakValuesByApprovalId(params);
        return setSuccessModelMap(modelMap,cheakValues);
    }
    @ApiOperation(value = "已办详情check项的值")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getCkeckValueDetails")
    public Object getCkeckValueDetails(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Map<String, Object> DetailsCheakValues = bizApprovalService.getCheakValuesDetails(params);
        return setSuccessModelMap(modelMap,DetailsCheakValues);
    }
    @ApiOperation(value = "经办驳回意见")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getCkeckValueJBBH")
    public Object getCkeckValueJBBH(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long uid = BizWebUtil.getCurrentUser();
        params.put("userId", uid.toString());
        String beforeTaskId = bizApprovalService.getBeforeTaskId(params);
        Map<String, Object> param =new HashMap<>();
        param.put("taskId",beforeTaskId);
        Map<String, Object> DetailsCheakValues = bizApprovalService.getCheakValuesDetails(param);
        return setSuccessModelMap(modelMap,DetailsCheakValues);
    }
    @ApiOperation(value = "接口调用")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/invokeInfHX")
    public Object invokeInf(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long uid = BizWebUtil.getCurrentUser();
        params.put("userId", uid.toString());
        bizApprovalService.invokeInf(params);
        return setSuccessModelMap(modelMap);

    }
    @ApiOperation(value = "修改已办状态")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/UpdateHaveDone")
    public Object UpdateHaveDone(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        bizApprovalService.UpdateHaveDone(params);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "发放审核得到概要信息")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getInformation")
    public Object getInformation(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        SumInformationVo information = bizApprovalService.getInformation(params);
        return setSuccessModelMap(modelMap,information);
    }

}
