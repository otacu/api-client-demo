package com.sdt.api.client.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.sdt.api.client.demo.constant.SdtResultStatusConstant;
import com.sdt.api.client.demo.exception.SdtException;
import okhttp3.*;
import okio.ByteString;

import javax.net.ssl.*;
import java.io.File;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class SdtOkHttp3Util {

    private SdtOkHttp3Util() {
    }

    /**
     * OkHttpClient
     */
    private static OkHttpClient client;

    /**
     * 超时时间
     */
    public static final int TIMEOUT = 90;


    /**
     * 超时时间
     */
    public static final int TIMEOUT2 = 30;


    /**
     * application/x-www-form-urlencoded
     */
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    /**
     * application/x-www-form-urlencoded
     */
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    /**
     * application/octet-stream
     */
    public static final MediaType MEDIA_OCTET_STREAM = MediaType.parse("application/octet-stream");

    static {
        TrustAllManager trustAllManager = new TrustAllManager();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("101.89.78.26", 8080)))
                .build();
    }

    /**
     * 同步发送post请求 map为body
     *
     * @param url 请求url
     * @param map 请求map参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject post(String url, Map<String, Object> map) throws SdtException {
        return post(url, map, MEDIA_TYPE);
    }

    /**
     * 同步发送post请求 map为body
     *
     * @param url       请求url
     * @param map       请求map参数
     * @param mediaType mediaType
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject post(String url, Map<String, Object> map, MediaType mediaType) throws SdtException {

        String httpContent;
        if (MEDIA_TYPE.equals(mediaType)) {
            StringBuilder context = new StringBuilder();
            // 遍历map
            if (map != null) {
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    context.append(entry.getKey()).append("=").append(entry.getValue());
                    if (iterator.hasNext()) {
                        context.append("&");
                    }
                }
            }
            httpContent = context.toString();
        } else if (JSON_MEDIA_TYPE.equals(mediaType)) {
            httpContent = SdtJsonUtil.objectToJson(map);
        } else {
            throw new SdtException("不支持的MIME类型");
        }


        RequestBody body = RequestBody.create(mediaType, httpContent);
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }


    /**
     * 同步发送post请求 map为body
     * 返回字符串
     *
     * @param url 请求url
     * @param map 请求map参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static String postMap(String url, Map<String, Object> map) throws SdtException {
        FormBody.Builder builder = new FormBody.Builder();
        // 遍历map
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return data;
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }

    /**
     * 同步发送post请求 map为body
     *
     * @param url 请求url
     * @param map 请求map参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static String postXml(String url, Map<String, Object> map) throws SdtException {
        StringBuilder context = new StringBuilder();
        // 遍历map
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                context.append(entry.getKey()).append("=").append(entry.getValue());
                if (iterator.hasNext()) {
                    context.append("&");
                }
            }
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, context.toString());
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return data;
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }


    /**
     * 同步发送post请求 map为body
     *
     * @param url     请求url
     * @param context 请求参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static String postString(String url, String context) throws SdtException {
        return postString(url, MEDIA_TYPE, context);

    }

    /**
     * 同步发送post请求 map为body
     *
     * @param url       请求url
     * @param mediaType mediaType
     * @param context   请求参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static String postString(String url, MediaType mediaType, String context) throws SdtException {

        RequestBody body = RequestBody.create(mediaType, context);
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return data;
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }

    /**
     * 同步发送post请求 map为body
     *
     * @param url  请求url
     * @param json 请求参数
     * @return 返回回来的JSONObject
     * @throws Exception 异常
     */
    public static String postJson(String url, String json) throws Exception {
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String data = response.body().string();
            return data;
        }

        return "请求失败，请检查参数";
    }


    /**
     * 同步发送post请求 map为body
     *
     * @param url 请求url
     * @param map 请求map参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject postFormBody(String url, Map<String, Object> map) throws SdtException {
        FormBody.Builder builder = new FormBody.Builder();
        // 遍历map
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }


    /**
     * 同步发送post请求 文件byte[]
     *
     * @param url   请求url
     * @param map   请求map参数放入header中 (header中不能传中文，value 如果是字符串需要转码 URLEncoder.encode)
     * @param bytes 文件byte[]
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject postByteArr(String url, Map map, byte[] bytes) throws SdtException {
        if (map == null) {
            map = new HashMap();
        }
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MEDIA_OCTET_STREAM, ByteString.of(bytes)))
                    .headers(Headers.of(map))
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }

    /**
     * 同步发送post请求 文件
     *
     * @param url   请求url
     * @param map   请求map参数
     * @param files 文件集合
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject postFiles(String url, Map map, File... files) throws SdtException {
        if (map == null) {
            map = new HashMap();
        }
        if (files.length == 0) {
            // if (null == file || !file.exists() || !file.canRead() || !file.isFile()) {
            throw new SdtException("上传文件非法", SdtResultStatusConstant.STATUS_400);
            // }
        }

        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);

            // 遍历map
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                builder.addFormDataPart(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                builder.addFormDataPart("file" + i, file.getName(), RequestBody.create(MEDIA_OCTET_STREAM, file));
            }

            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }

    /**
     * 同步发送post请求 map为body，带请求头
     *
     * @param url      请求url
     * @param map      请求头
     * @param postJson 请求报文
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject postHeaderBody(String url, Map map, String postJson) throws SdtException {

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, postJson);
        Request request = new Request.Builder().url(url).headers(Headers.of(map)).post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SdtException(e);
        }
    }

    /**
     * 同步发送post表单请求
     *
     * @param url       请求url
     * @param headerMap 请求头
     * @param bodys     请求内容
     * @return 返回回来的JSONObject
     * @throws SdtException 自定义异常
     */
    public static JSONObject post(String url, Map<String, String> headerMap, Map<String, Object> bodys) throws SdtException {
        FormBody.Builder builder = new FormBody.Builder();
        // 遍历bodys
        if (bodys != null) {
            Iterator<Map.Entry<String, Object>> iterator = bodys.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).headers(Headers.of(headerMap)).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }
    }

    /**
     * 同步发送post请求 map为body 【洋桃同步洋葱专用】
     *
     * @param url     请求url
     * @param headMap 请求头map参数
     * @param map     请求map参数
     * @return 返回回来的JSONObject
     * @throws SdtException 异常
     */
    public static JSONObject postHeaderBody(String url, Map headMap, Map<String, Object> map) throws SdtException {
        FormBody.Builder builder = new FormBody.Builder();
        // 遍历map
        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(url).headers(Headers.of(headMap)).post(formBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            throw new SdtException(e);
        }

    }

    /**
     * 创建信任所有sslFactory
     *
     * @param trustAllManager TrustAllManager
     * @return SSLSocketFactory
     */
    protected static SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    /**
     * 发起get请求
     *
     * @param url url
     * @return 处理结果
     * @throws SdtException 异常
     */
    public static String get(String url) throws SdtException {
        Request request = new Request.Builder().url(url).get().build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SdtException(e);
        }
    }

    /**
     * 发起get请求
     *
     * @param url url
     * @return 处理结果
     * @throws SdtException 异常
     */
    public static JSONObject get(String url, Map headMap) throws SdtException {
        Request request = new Request.Builder().url(url).headers(Headers.of(headMap)).get().build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String data = response.body().string();
                return SdtJsonUtil.jsonToPojo(data, JSONObject.class);
            }
            throw new SdtException("服务器解析错误...", SdtResultStatusConstant.STATUS_400);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SdtException(e);
        }
    }
}

