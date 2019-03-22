package org.ibase4j.web;

import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.model.SysMsgConfig;
import org.ibase4j.service.SysMsgConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信模版管理控制类
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "短信模版管理", description = "短信模版管理")
@RequestMapping(value = "msgConfig")
public class SysMsgConfigController extends BaseController {
    @Autowired
    private SysMsgConfigService sysMsgConfigService;

    @ApiOperation(value = "查询短信模版")
    @RequiresPermissions("sys.base.msgConfig.read")
    //@RequestMapping(value = "/read/list", method = RequestMethod.POST)
    @PostMapping(value = "/read/list")
    public Object get(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        Page<?> list = sysMsgConfigService.query(params);
        return setSuccessModelMap(modelMap, list);
    }

    @ApiOperation(value = "短信模版详情")
    @RequiresPermissions("sys.base.msgConfig.read")
    //@RequestMapping(value = "/read/detail", method = RequestMethod.POST)
    @PostMapping(value = "read/detail")
    public Object detail(ModelMap modelMap, @RequestBody SysMsgConfig params) {
        SysMsgConfig record = sysMsgConfigService.queryById(params.getId());
        return setSuccessModelMap(modelMap, record);
    }

    @ApiOperation(value = "修改短信模版")
    @RequiresPermissions("sys.base.msgConfig.add")
    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping(value = "/save")
    public Object update(ModelMap modelMap, @RequestBody SysMsgConfig record) {
        record.setEnable(1);
        sysMsgConfigService.update(record);
        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "删除短信模版")
    @RequiresPermissions("sys.base.msgConfig.delete")
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(ModelMap modelMap, @RequestBody SysMsgConfig record) {
        sysMsgConfigService.delete(record.getId());
        return setSuccessModelMap(modelMap);
    }
  //检查模版名称是否已存在
  		@ApiOperation(value = "检查模版名称是否已存在")
  		@RequiresPermissions("sys.base.msgConfig.read")
  		@GetMapping(value = "/read/msgName")
  		@ResponseBody
  		public Object selectMsgtName(ModelMap modelMap, String account) {
  			Map<String,Object> resultMap = new HashMap<String, Object>();  
  			 Boolean b = sysMsgConfigService.queryByMsgName(account);
  			 resultMap.put("accounts", b);
  			return resultMap;
  		}
}

