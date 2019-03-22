package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.DownloadExcelUtil;
import org.ibase4j.core.util.ExportExcelUtil;
import org.ibase4j.service.BizStandingBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 台账
 * @Author: dj
 * @CreateDate: 2018-08-30
 */
@RestController
@Api(value = "台账", description = "台账")
@RequestMapping(value = "/standingBook")
public class BizStandingBookController extends BaseController {

    @Autowired
    private BizStandingBookService bizStandingBookService;

    @ApiOperation(value = "发放审核台账所属机构")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/getDeptList")
    public Object getDeptList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        List deptList = bizStandingBookService.getDeptList();
        return setSuccessModelMap(modelMap, deptList);
    }

    @ApiOperation(value = "发放审核台账列表")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/grantStandingBookList")
    public Object grantStandingBookList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        params.put("enable",1);
        Page grantStandingBookList = bizStandingBookService.getGrantStandingBookList(params);
        return setSuccessModelMap(modelMap, grantStandingBookList);
    }

    @ApiOperation(value = "发放审核台账 金额流水明细")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/getGrantAMTDetail")
    public Object getGrantAMTDetail(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        params.put("enable",1);
        List grantAMTDetailForStandingBook = bizStandingBookService.getGrantAMTDetailForStandingBook(params);
        return setSuccessModelMap(modelMap, grantAMTDetailForStandingBook);
    }

    @ApiOperation(value = "发放审核台账 导出金额流水")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/exportExcelForAMTDetail")
    public Object exportExcelForAMTDetail(ModelMap modelMap,HttpServletResponse response, @RequestBody Map<String, Object> params) {
        params.put("enable",1);
        bizStandingBookService.exportExcelForAMTDetail(params,response);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "统计信息查询列表")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/getStatisticInfoList")
    public Object getStatisticInfoList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        params.put("enable",1);
        Page statisticInfoList = bizStandingBookService.getStatisticInfoPage(params);
        return setSuccessModelMap(modelMap, statisticInfoList);
    }

    @ApiOperation(value = "统计信息导出")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/exportExcelForStatisticInfo")
    public Object exportExcelForStatisticInfo(ModelMap modelMap,HttpServletResponse response, @RequestBody Map<String, Object> params) {
        params.put("enable",1);
        bizStandingBookService.exportExcelForStatisticInfo(params,response);
        return setSuccessModelMap(modelMap);
    }
    @ApiOperation(value = "方案信息台账列表")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/debtStandingBookList")
    public Object debtStandingBookList(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        params.put("solutionState", 6);
        Page debtStandingBookList = bizStandingBookService.getDebtStandingBookList(params);
        return setSuccessModelMap(modelMap, debtStandingBookList);
    }

    @ApiOperation(value = "方案信息台账 方案信息明细")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/getDebtInfoForStandingBook")
    public Object getDebtInfoForStandingBook(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Map debtInfoForStandingBook = bizStandingBookService.getDebtInfoForStandingBook(params);
        return setSuccessModelMap(modelMap, debtInfoForStandingBook);
    }

    @ApiOperation(value = "方案信息台账 方案信息明细")
    @RequiresPermissions("biz.standingBook.read")
    @PutMapping(value = "/read/getGrantInfoForStandingBook")
    public Object getGrantInfoForStandingBook(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Map debtInfoForStandingBook = bizStandingBookService.getGrantInfoForStandingBook(params);
        return setSuccessModelMap(modelMap, debtInfoForStandingBook);
    }

}
