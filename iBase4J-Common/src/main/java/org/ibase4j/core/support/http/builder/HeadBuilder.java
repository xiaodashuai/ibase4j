package org.ibase4j.core.support.http.builder;


import org.ibase4j.core.support.http.OkHttpUtils;
import org.ibase4j.core.support.http.request.OtherRequest;
import org.ibase4j.core.support.http.request.RequestCall;


public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
