package org.ibase4j.core.support.http.builder;

import java.util.Map;


public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
