package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.BizProductTypes;
import org.ibase4j.service.SysProductTypeService;
import org.ibase4j.vo.SysProductTypesCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gby
 */
@RestController
@Api(value = "通知管理", description = "通知管理")
@RequestMapping(value = "productType")
public class SysProductTypeController extends BaseController {

    @Autowired
    private SysProductTypeService sysProductTypeService;

    @ApiOperation(value = "分页查询产品分类")
    @RequiresPermissions("sys.base.proType.read")
    @RequestMapping(value = "/read/list", method = RequestMethod.POST)
    public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        params.put("enable",1);
        Page<?> list = sysProductTypeService.query(params);
        return setSuccessModelMap(modelMap, list);
    }
    //根据isChild查询所有产品
    @ApiOperation(value = "根据isChild查询所有产品")
    @RequiresPermissions("sys.base.proType.read")
    @RequestMapping(value = "/read/queryChildList", method = RequestMethod.POST)
    public Object getChild(ModelMap modelMap) {
    	List<BizProductTypes> list = sysProductTypeService.queryChild();
    	return setSuccessModelMap(modelMap, list);
    }

    @ApiOperation(value = "查询产品分类列表")
    @RequiresPermissions("sys.base.proType.read")
    @RequestMapping(value = "/read/parentList", method = RequestMethod.POST)
    public Object getProdType(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        List<BizProductTypes> productTypesList = sysProductTypeService.queryList(params);
        return setSuccessModelMap(modelMap, productTypesList);
    }

    @ApiOperation(value = "产品分类详情")
    @RequiresPermissions("sys.base.proType.read")
    @RequestMapping(value = "/read/detail", method = RequestMethod.POST)
    public Object detail(ModelMap modelMap, @RequestBody BizProductTypes params) {
        BizProductTypes record = sysProductTypeService.queryById(params.getId());
        return setSuccessModelMap(modelMap, record);
    }

    @PostMapping
    @ApiOperation(value = "修改产品分类")
    @RequiresPermissions("sys.base.proType.update")
    public Object update(ModelMap modelMap, @RequestBody BizProductTypes record) {
        if(record.getParentCode()==null){
            record.setParentCode("0");
        }
        record.setEnable(1);
        sysProductTypeService.update(record);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除产品分类")
    @RequiresPermissions("sys.base.proType.delete")
    @PostMapping(value="/update/deleteproductType")
    public Object deleteproductType(ModelMap modelMap, @RequestBody BizProductTypes record) {
        sysProductTypeService.update(record);
        return setSuccessModelMap (modelMap);
    }

    @ApiOperation(value = "查询已绑定的机构产品种类")
    @RequiresPermissions("sys.base.proType.read")
    @PostMapping(value = "/read/getProductType")
    public Object getDeptProductType(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long deptId = Long.valueOf(params.get("deptId").toString());
        List<SysProductTypesCheckVo> list = sysProductTypeService.queryProductTypeByDeptId(deptId);
        return setSuccessModelMap(modelMap, list);
    }

    @ApiOperation(value = "保存机构产品种类")
    @RequiresPermissions("sys.base.proType.update")
    @PostMapping(value="/read/saveDeptProductType")
    public Object saveDeptProductType(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Long deptId = Long.valueOf(params.get("deptId").toString());
        String productTypesIds=(String) params.get("productTypesIds");
        sysProductTypeService.saveDeptProductType(productTypesIds, deptId);
        return setSuccessModelMap(modelMap);
    }

    //检查产品名称是否已存在
  	@ApiOperation(value = "检查产品名称是否已存在")
  	@RequiresPermissions("sys.base.proType.read")
  	@GetMapping(value = "/read/noticeTitle") 
  	@ResponseBody
  	public Object selectNoticeByNoticeTitle(ModelMap modelMap, String account) {
  		Map<String,Object> resultMap = new HashMap<String, Object>();  
  		 Boolean b = sysProductTypeService.queryByNoticeTitle(account);
  		 resultMap.put("accounts", b);
  		return resultMap;
  	}
}
