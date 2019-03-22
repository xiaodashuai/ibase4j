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
import java.util.*;

public abstract class CreditInterCallback<T> extends Callback<T>
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
        List<T> list= new ArrayList<T>();
        String value = response.body().string();
        System.out.println(value);
        JSONObject object = JSON.parseObject(value.replaceAll("0\\(", "").replaceAll("\\)", "").replaceAll("\n", ""));
        String errorcode=StringUtil.objectToString(object.get("errorcode"));
        if(errorcode.equalsIgnoreCase("R0000"))
        {
            if(object.containsKey("data"))
            {
                JSONArray array=new JSONArray(object.getJSONArray("data"));
              String interStatus=  StringUtil.objectToString(array.get(0));

              //取出返回的接口信息
              InvokeInfErrInfoVo errInfoVo=JSON.parseObject(interStatus,InvokeInfErrInfoVo.class);
               //移除接口信息
                array.remove(0);
                Iterator<Object> iterator= array.iterator();
                while (iterator.hasNext())
                {
                    String content=StringUtil.objectToString(iterator.next());
                    T vo= JSON.parseObject(content,getTClass());
                    list.add(vo);
                }
                return list;
            }else
            {
                logger.debug("===CreditInterCallback===parseNetworkResponse==action={}===返回接口格式有问题==",object.getString("action"));
                return null;
            }

        }else if(errorcode.equalsIgnoreCase("01301"))
        {
            logger.debug("===CreditInterCallback===parseNetworkResponse==action={}===请求param有问题",object.getString("action"));
            return null;
        }
        return null;
    }
}
