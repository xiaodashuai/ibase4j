package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.service.BizProstaService;
import org.ibase4j.service.BizWorkflowService;
import org.ibase4j.vo.FileNameVo;
import org.ibase4j.vo.ProstatAuditVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 工作流
 * @Author: dj
 * @Version: 1.0
 * @CreateDate: 2018-05-31
 */
@RestController
@Api(value = "工作流", description = "工作流")
@RequestMapping(value = "/workflow")
public class BizWorkflowController extends BaseController {

    @Autowired
    private BizWorkflowService bizWorkflowService;

    @Autowired
    private BizProstaService bizProstaService;

    @ApiOperation(value = "查询发放待办任务")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getGrantToDoTask")
    public Object getGrantToDoTask(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        Map map = bizWorkflowService.getGrantToDoTask(params);
        return setSuccessModelMap(modelMap, map);
    }
    @ApiOperation(value = "查询方案待办任务")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getSchemeToDoTask")
    public Object getSchemeToDoTask(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        Map map = bizWorkflowService.getSchemeToDoTask(params);
        return setSuccessModelMap(modelMap, map);
    }

    @ApiOperation(value = "查询发放已办任务")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getGrantHaveDoneTask")
    public Object getGrantHaveDoneTask(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        Map map = bizWorkflowService.getGrantHaveDoneTask(params);
        return setSuccessModelMap(modelMap, map);
    }
    @ApiOperation(value = "查询方案已办任务")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getSchemeHaveDoneTask")
    public Object getSchemeHaveDoneTask(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        Map map = bizWorkflowService.getSchemeHaveDoneTask(params);
        return setSuccessModelMap(modelMap, map);
    }

    @ApiOperation(value = "待选步骤")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getNextTask")
    public Object getNextTask(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long userId = BizWebUtil.getCurrentUser();
        String piid =  StringUtil.objectToString(params.get("piid"));
        String ptid = StringUtil.objectToString(params.get("ptid"));
        String adid =  StringUtil.objectToString(params.get("adid"));
        List task = bizWorkflowService.getAvailableTransitions(piid,ptid,adid,userId.toString());
        return setSuccessModelMap(modelMap,task);
    }

    @ApiOperation(value = "根据流转方向完成任务")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/update/audit")
    public Object audit(ModelMap modelMap, @RequestBody ProstatAuditVo prostat) {
        Long uid = BizWebUtil.getCurrentUser();
        prostat.setUserId(uid.toString());
        prostat.setUserName("审核员");
        bizWorkflowService.auditProSta(prostat);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "创建并启动流程实例")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/update/createAndStartProcess")
    public Object createAndStartProcess(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long uid = BizWebUtil.getCurrentUser();
        params.put("userId", uid.toString());
        // 由事前尽职调查生成债项方案编码
        String busiCode = "1";
        params.put("mBizId",busiCode);
        bizWorkflowService.createAndStartProcess(params);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "获取发放编码")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/read/getGrantCode")
    public Object getGrantCode(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        String piid = StringUtil.objectToString(params.get("piid"));
        Long userId = BizWebUtil.getCurrentUser();
        String uId = StringUtil.objectToString(userId);
        String grantCode = bizProstaService.getProcessInsBizId(uId,piid);
        FileNameVo vo = new FileNameVo();
        vo.setFileName(grantCode);
        return setSuccessModelMap(modelMap,vo);
    }

}
