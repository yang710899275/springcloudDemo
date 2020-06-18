package com.example.springclouddemo.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private static final int TIME_OUT = 10000;

    public static String doGet(String url, Map<String, Object> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        Date startTime = new Date();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    Object value = param.get(key);
                    builder.addParameter(
                            key, value == null ? "" : value.toString());
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpclient.execute(httpGet);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        Date startTime = new Date();
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .build();
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    Object value = param.get(key);
                    paramList.add(new BasicNameValuePair(
                            key, value == null ? "" : value.toString()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    private static String doPostJson(String url, String json) {
        return doPostJson(url, json, null);
    }
    private static String doPostJson(String url, String json, Map<String, String> header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        Date startTime = new Date();
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .build();
            httpPost.setConfig(requestConfig);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            if (header != null){
                for (Map.Entry<String, String> head : header.entrySet()){
                    httpPost.addHeader(head.getKey(), head.getValue());
                }
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPostJson(String url, JSONObject json) {
        return doPostJson(url, json.toJSONString());
    }

    public static String doPostJson(String url, JSONObject json, Map<String, String> header) {
        return doPostJson(url, json.toJSONString(), header);
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength < 0){
            return new byte[0];
        }
        byte[] buffer = new byte[contentLength];
        int i = 0;
        while (i < contentLength) {
            int readlen = request.getInputStream().read(
                    buffer,
                    i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    public static String sendGet(String url) throws Exception {
        Connection conn = Jsoup.connect(url);
        Connection.Response response = conn.timeout(10000).ignoreContentType(true).method(Connection.Method.GET).execute();
        return response.body();
    }


    public static String sendPost(String url, Map<String, String> param) throws Exception {
        Connection conn = Jsoup.connect(url);
        Connection.Response response = conn.timeout(1000000).ignoreContentType(true).method(Connection.Method.POST).data(param).execute();
        return response.body();
    }







	}
