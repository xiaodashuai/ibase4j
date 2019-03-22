package org.ibase4j.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.support.http.callback.Callback;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.vo.InvokeInfErrInfoVo;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class KernelInterCallback<T>  extends Callback<T>
{
    private final Logger logger = LogManager.getLogger();
    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    public List<T> parseNetworkResponse(Response response, int id) throws IOException
    {
        String value = response.body().string();
        System.out.println(value);
        JSONObject object = JSON.parseObject(value.replaceAll("0\\(", "").replaceAll("\\)", "").replaceAll("\n", ""));
        String errorcode= StringUtil.objectToString(object.get("errorcode"));
        if(errorcode.equalsIgnoreCase("R0000"))
        {
            if(object.containsKey("data"))
            {
                List<T> list = JSON.parseArray(object.getString("data"), getTClass());
                return list;
            }else
            {
                logger.debug("===KernelInterCallback===parseNetworkResponse==action={}===返回接口格式有问题==",object.getString("action"));
                return null;
            }

        }else if(errorcode.equalsIgnoreCase("01301"))
        {
            logger.debug("===KernelInterCallback===parseNetworkResponse==action={}===请求param有问题",object.getString("action"));
            return null;
        }
        return null;
    }
}