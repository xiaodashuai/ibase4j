package org.ibase4j.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.model.BizCanvas;
import org.ibase4j.provider.CanvasProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CanvasService extends BaseService<CanvasProvider,BizCanvas> {

    @Reference
    public void setCanvasProvider(CanvasProvider provider) {
        this.provider = provider;
    }


    public boolean savCanvas(Map<String, Object> params){
        String imgStr =  StringUtil.objectToString(params.get("imgStr"));
        String bizcode =  StringUtil.objectToString(params.get("bizcode"));
        String type =  StringUtil.objectToString(params.get("type"));
        String extra =  StringUtil.objectToString(params.get("extra"));
        String num =  StringUtil.objectToString(params.get("num"));
        if(!"".equals(imgStr) && !"".equals(bizcode) && !"".equals(type)){// && !"".equals(extra)
            BizCanvas canvas = new BizCanvas();
            canvas.setBizcode(bizcode);
            canvas.setType(type);
            if("".equals(num))
                canvas.setNum(1);
            else
                canvas.setNum(Integer.valueOf(num));
            canvas.setExtra(extra);
            return provider.savCanvas(imgStr,canvas);
        }else
            return false;
    }

    public List<String> readCanvas(Map<String, Object> params){
        return provider.queryImgList(params);
    }
}
