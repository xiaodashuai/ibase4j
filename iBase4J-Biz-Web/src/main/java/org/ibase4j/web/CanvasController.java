package org.ibase4j.web;

import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ibase4j.core.base.BaseController;
import org.ibase4j.core.util.BizWebUtil;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.provider.BizCntProvider;
import org.ibase4j.service.CanvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "工具接口", description = "工具接口")
@RequestMapping(value = "/bizUtil")
public class CanvasController extends BaseController {

    @Autowired
    private CanvasService canvasService;

    /**
     * 将字符串加密存储
     * @param params
     * @return
     */
    @ApiOperation(value = "存储快照文件")
    @PostMapping(value = "/read/savCanvas")
    public boolean savCanvas(ModelMap modelMap,@RequestBody Map<String, Object> params) {
        params.put("createUser",BizWebUtil.getCurrentUser());
        return canvasService.savCanvas(params);
    }

    /**
     * 读取文件，返回前台base64图片字符串结果集
     * @param params
     * @return
     */
    @ApiOperation(value = "读取快照文件")
    @PostMapping(value = "/read/readCanvas")
    public List<String> readCanvas(ModelMap modelMap, @RequestBody Map<String, Object> params) {
        return canvasService.readCanvas(params);
    }


}
