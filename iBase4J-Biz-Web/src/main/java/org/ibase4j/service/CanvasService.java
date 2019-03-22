package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizCanvas;
import org.ibase4j.provider.BizCntProvider;
import org.ibase4j.provider.CanvasProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CanvasService extends BaseService<CanvasProvider,BizCanvas> {

    @Reference
    public void setCanvasProvider(CanvasProvider provider) {
        this.provider = provider;
    }
    @Reference
    private BizCntProvider bizCntProvider;

    public boolean savCanvas(Map<String, Object> params){
        String imgStr =  StringUtil.objectToString(params.get("imgStr"));
        String bizCode =  StringUtil.objectToString(params.get("bizcode"));
        String type =  StringUtil.objectToString(params.get("type"));
        String extra =  StringUtil.objectToString(params.get("extra"));
        String num =  StringUtil.objectToString(params.get("num"));
        String createUser =  StringUtil.objectToString(params.get("createUser"));

        if(!"".equals(imgStr) && !"".equals(bizCode) && !"".equals(type)){
            BizCanvas canvas = new BizCanvas();
            canvas.setBizcode(bizCode);
            canvas.setType(type);
            canvas.setCreateBy(Long.parseLong(createUser));
            canvas.setUpdateBy(Long.parseLong(createUser));
            if("".equals(num)){
                canvas.setNum(1);
            }
            else{
                canvas.setNum(Integer.valueOf(num));
            }
            if(null!=extra){
                canvas.setExtra(extra);
            }else{
                //存业务号码时，记录变更号（暂未使用）
                if("A".equals(type) || "B".equals(type)){
                    canvas.setExtra(bizCode.substring(bizCode.length()-3));
                }
            }
            return provider.savCanvas(imgStr,canvas);
        }else{
            return false;
        }
    }

    public List<String> readCanvas(Map<String, Object> params){
        String bizCode =  StringUtil.objectToString(params.get("bizcode"));
        String type =  StringUtil.objectToString(params.get("type"));
        String extra =  StringUtil.objectToString(params.get("extra"));
        String num =  StringUtil.objectToString(params.get("num"));

        if(!"".equals(bizCode) && !"".equals(type))
        {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("bizcode",bizCode);
            map.put("type",type);
            if(!"".equals(num)){
                map.put("num",Integer.valueOf(num));
            }
            if(!"".equals(extra)){
                map.put("extra",extra);
            }
            return provider.queryImgList(map);
        }else{
            logger.error("读取快照文件异常，查询参数=params("+params+")");
            return null;
        }

    }
}
