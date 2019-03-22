package org.ibase4j;

import okhttp3.Call;
import org.ibase4j.callback.KernelInterCallback;
import org.ibase4j.core.support.http.OkHttpUtils;
import org.ibase4j.core.util.StringUtil;
import org.ibase4j.vo.CjdkhtxyVo;

import java.util.ArrayList;
import java.util.List;

public class OkHttpUtilsTest {
    public static void main(String[] args) {
     String url="http://192.168.11.68:8082/anbangInf";
        String actionSet="cjdkhtxy";
        String action="cjdkhtxy";
        List<Object> argsList=new ArrayList<Object>();
        argsList.add(101);

        OkHttpUtils.post()
                .url(url)
                .addParams("actionSet",actionSet)
                .addParams("action",action)
                .addParams("args", StringUtil.joinInterfaceParams(argsList))
                .build()
                .connTimeOut(60)
                .readTimeOut(60)
                .writeTimeOut(60)
                .execute(new KernelInterCallback<CjdkhtxyVo>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onResponse(List<CjdkhtxyVo> response, int id) {
                        System.out.println(response);
                    }
                });

    }
}
