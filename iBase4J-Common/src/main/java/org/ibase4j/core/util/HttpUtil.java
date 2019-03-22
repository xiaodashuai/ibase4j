package org.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpUtil {
	private static final Logger logger = LogManager.getLogger();
    private static final MediaType CONTENT_TYPE_FORM = MediaType
            .parse("application/x-www-form-urlencoded;charset=UTF-8");
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

    private static final String PINTERFACE_URL =PropertiesUtil.getString("interface.dir");
    private static final int CONNECT_TIME_OUT = PropertiesUtil.getInt("interface.connectTimeout");
    private static final int READ_TIME_OUT = PropertiesUtil.getInt("interface.readTimeout");


	private HttpUtil() {
	}
     // 同步请求
     public static final String invokeInfExecute(String actionSet, String action, String[] args){
         OkHttpClient client = new OkHttpClient.Builder()
                 .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                 .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                 .build();
         String results = null;
         StringBuilder argsBuilder = new StringBuilder();
         argsBuilder.append("[");
         JSONObject jsonObject = new JSONObject();
         argsBuilder.append(StringUtils.join(args, ","));
         argsBuilder.append("]");
         logger.debug(argsBuilder.toString());
         logger.debug(JSON.toJSONString(argsBuilder.toString()));
         RequestBody formBody = new FormBody.Builder()
                 .add("actionSet", actionSet)
                 .add("action", action)
                 .add("args", argsBuilder.toString())
                 .build();
         logger.debug(argsBuilder.toString());
         final Request[] request = {new Request.Builder()
                 .url(PINTERFACE_URL)
                 .addHeader("content-type", "application/json")
                 .post(formBody)
                 .build()};

        Call call = client.newCall(request[0]);
         try {
             String json = call.execute().body().string();
             JSONObject object = JSON.parseObject(json.replaceAll("0\\(", "").replaceAll("\\)", "").replaceAll("\n", ""));
             results = object.getString("data");
         } catch (Exception ex) {
             logger.error(ExceptionUtil.getStackTraceAsString(ex));
                     ex.printStackTrace();
         }
         return results;
     }

    public static final Map invokeInfExecute2(String actionSet, String action, String[] args){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
        String results = null;
        // 返回结果
        Map resultMap = new HashMap();
        String code = "200";
        String msg = "";

        StringBuilder argsBuilder = new StringBuilder();
        argsBuilder.append("[");
        JSONObject jsonObject = new JSONObject();
        argsBuilder.append(StringUtils.join(args, ","));
        argsBuilder.append("]");
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("actionSet", actionSet)
                    .add("action", action)
                    .add("args", argsBuilder.toString())
                    .build();
            System.out.println(argsBuilder.toString());
            final Request[] request = {new Request.Builder()
                    .url(PINTERFACE_URL)
                    .addHeader("content-type", "application/json")
                    .post(formBody)
                    .build()};

            Call call = client.newCall(request[0]);

            String json = call.execute().body().string();
            JSONObject object = JSON.parseObject(json.replaceAll("0\\(", "").replaceAll("\\)", "").replaceAll("\n", ""));
            results = object.getString("data");
        } catch (Exception ex) {
            logger.error(ExceptionUtil.getStackTraceAsString(ex));
            ex.printStackTrace();
            msg = ex.getMessage();
            code = "500";
        }

        resultMap.put("code",code);
        resultMap.put("msg",msg);
        resultMap.put("results",results);
        return resultMap;
    }

    // 异步请求
    public static final String invokeInf(String actionSet, String action, String[] args) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .build();
        final String[] results = {null};
        StringBuilder argsBuilder = new StringBuilder();
        argsBuilder.append("[");
        JSONObject jsonObject = new JSONObject();
        argsBuilder.append(StringUtils.join(args, ","));
        argsBuilder.append("]");
        logger.debug(argsBuilder.toString());
        logger.debug(JSON.toJSONString(argsBuilder.toString()));
        RequestBody formBody = new FormBody.Builder()
                .add("actionSet", actionSet)
                .add("action", action)
                .add("args", argsBuilder.toString())
                .build();

        final Request[] request = {new Request.Builder()
                .url(PINTERFACE_URL)
                .addHeader("content-type", "application/json")
                .post(formBody)
                .build()};

        client.newCall(request[0]).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    JSONObject object = JSON.parseObject(json.replaceAll("0\\(", "").replaceAll("\\)", "").replaceAll("\n", ""));
                    results[0] = object.getString("data");
                    logger.debug("data" + object.getString("data"));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                results[0] = "";
                logger.debug("okhttp3 call", e.getMessage());
            }

        });
        return results[0];
    }

    public static final String get(String url) {
		String result = "";
		HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        method.addRequestHeader("User-Agent", DEFAULT_USER_AGENT);
		try {
            client.executeMethod(method);
            result = method.getResponseBodyAsString();
		} catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
		} finally {
            method.releaseConnection();
		}
		return result;
	}

    public static final String post(String url, ArrayList<NameValuePair> list) {
		String result = "";
		HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
		try {
			NameValuePair[] params = new NameValuePair[list.size()];
			for (int i = 0; i < list.size(); i++) {
				params[i] = list.get(i);
			}
            method.addParameters(params);
            client.executeMethod(method);
            result = method.getResponseBodyAsString();
		} catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
		} finally {
            method.releaseConnection();
		}
		return result;
	}

	public static String post(String url, String params) {
		RequestBody body = RequestBody.create(CONTENT_TYPE_FORM, params);
		Request request = new Request.Builder().url(url).post(body).build();
		return exec(request);
	}

	private static String exec(okhttp3.Request request) {
		try {
			okhttp3.Response response = new OkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
				throw new RuntimeException("Unexpected code " + response);
			}
			return response.body().string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    public static final String httpClientPost(String url, ArrayList<NameValuePair> list) {
        String result = "";
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        try {
            NameValuePair[] params = new NameValuePair[list.size()];
            for (int i = 0; i < list.size(); i++) {
                params[i] = list.get(i);
            }
            postMethod.addParameters(params);
            client.executeMethod(postMethod);
            result = postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    public static final String httpClientPost(String url) {
        String result = "";
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        try {
            client.executeMethod(getMethod);
            result = getMethod.getResponseBodyAsString();
        } catch (Exception e) {
            logger.error(e);
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }

    public static String postSSL(String url, String data, String certPath, String certPass) {
        HttpsURLConnection conn = null;
        OutputStream out = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(new FileInputStream(certPath), certPass.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, certPass.toCharArray());
            KeyManager[] kms = kmf.getKeyManagers();
            SSLContext sslContext = SSLContext.getInstance("TLSv1");

            sslContext.init(kms, null, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            URL _url = new URL(url);
            conn = (HttpsURLConnection) _url.openConnection();

            conn.setConnectTimeout(25000);
            conn.setReadTimeout(25000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
            conn.connect();

            out = conn.getOutputStream();
            out.write(data.getBytes(Charsets.toCharset("UTF-8")));
            out.flush();

            inputStream = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charsets.toCharset("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStream);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String joinInterfaceParams(List list){
        StringBuilder params = new StringBuilder();
        if(list != null && list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                if(i != list.size() -1){
                    params.append("'").append(list.get(i).toString()).append("',");
                }else{
                    params.append("'").append(list.get(i).toString()).append("'");
                }
            }
        }
        return params.toString();
    }

}
