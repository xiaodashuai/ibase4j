package org.ibase4j.web;

/*
* 债项概要
* @author xiaoshuiquan
* @date  2018.5.15
* */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizCust;
import org.ibase4j.model.BizCustomer;
import org.ibase4j.service.BizUseLetterService;
import org.ibase4j.vo.ProductBussinessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "用信主体", description = "用信主体")
@RequestMapping(value = "useLetter")
public class BizUseLetterController extends BaseController{
    @Autowired
    private BizUseLetterService useLetterService;

    @ApiOperation(value = "查询客户")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/getCustomerList/list")
    public Object getCustomerList(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
        String custNo=customer.get("customerNum").toString();
        BizCust cust=useLetterService.getCustomerList(custNo);
        return setSuccessModelMap(modelMap,cust);
    }

    @ApiOperation(value = "保存选择的客户")
    @RequiresPermissions("before.base.reponse.read")
    @PostMapping(value = "/saveCustomer/save")
    public Object saveCustomer(ModelMap modelMap, @RequestBody BizCustomer customer) {
        useLetterService.update(customer);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "回显客户详情")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/selectCustomerList/list")
    public Object selectCustomerList(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
        List<BizCustomer> cusList=useLetterService.queryList(customer);
        BizCustomer cust=cusList.get(0);
        return setSuccessModelMap(modelMap,cust);
    }

    @ApiOperation(value = "回显选择客户")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/getCustList/list")
    public Object getCustList(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
        Map<String,Object>cusMap=new HashMap();
        Long debtNum=Long.valueOf(customer.get("debtNum").toString());
        cusMap.put("debtNum", debtNum);
        List<BizCustomer> cusList=useLetterService.queryList(cusMap);
        if (cusList.size()!=0){
            return setSuccessModelMap(modelMap,cusList);
        }
        return  setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "查询为客户绑定的产品")
    @RequiresPermissions("before.base.reponse.read")
    @PutMapping(value = "/queryCusPro/list")
    public Object queryCusPro(ModelMap modelMap, @RequestBody Map<String, Object> customer) {
        List<ProductBussinessVo> cusProList=useLetterService.queryCusPro(customer);
        if (cusProList.size()!=0){
            return setSuccessModelMap(modelMap,cusProList);
        }
        return  setSuccessModelMap(modelMap);
    }



}

