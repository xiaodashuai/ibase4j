package org.ibase4j.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizHistoryCommentInfo;
import org.ibase4j.service.BizHistoryCommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description: 历史意见信息
 * @Author: dj
 * @CreateDate: 2018-06-14
 */
@RestController
@Api(value = "历史意见信息", description = "历史意见信息")
@RequestMapping("/historyCommentInfo")
public class BizHistoryCommentInfoController extends BaseController {

    @Autowired
    private BizHistoryCommentInfoService bizHistoryCommentInfoService;

    @ApiOperation(value = "保存历史意见")
    @RequiresPermissions("biz.workflow.read")
    @PostMapping(value = "/update/saveCommentInfo")
    public Object saveCommentInfo(ModelMap modelMap, @RequestBody Map<String,Object> params){
        Long userId = BizWebUtil.getCurrentUser();
        params.put("userId", userId.toString());
        bizHistoryCommentInfoService.saveCommentInfo(params);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "根据债项编码查询历史意见")
    @RequiresPermissions("biz.workflow.read")
    @PutMapping(value = "/read/getHistoryCommentInfo")
    public Object getHistoryCommentInfo(ModelMap modelMap, @RequestBody Map<String,Object> params){
        String busiCode = StringUtil.objectToString(params.get("busiCode"));
        List<BizHistoryCommentInfo> historyCommentInfo = bizHistoryCommentInfoService.getHistoryCommentInfo(busiCode);
        return setSuccessModelMap(modelMap,historyCommentInfo);
    }

}
