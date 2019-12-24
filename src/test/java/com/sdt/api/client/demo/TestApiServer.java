package com.sdt.api.client.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdt.api.client.demo.request.SdtGetTokenRequest;
import com.sdt.api.client.demo.request.SdtPayRequest;
import com.sdt.api.client.demo.response.GetAccessTokenResponse;
import com.sdt.api.client.demo.response.PayResponse;
import com.sdt.api.client.demo.util.SdtOkHttp3Util;
import com.sdt.api.client.demo.util.SdtSignUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestApiServer {

    @Test
    public void testGetAccessToken() throws Exception{
        SdtGetTokenRequest request = new SdtGetTokenRequest();
        Map<String, String> params = SdtSignUtil.sign(request);
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.putAll(params);
        JSONObject jsonObject = SdtOkHttp3Util.post("http://localhost:8081/auth/getAccessToken", requestParams);
        GetAccessTokenResponse response = JSONObject.parseObject(jsonObject.toJSONString(), GetAccessTokenResponse.class);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testPay() throws Exception{
        SdtPayRequest request = new SdtPayRequest();
        request.setTransationNo("transationNo");
        request.setName("Jack");
        request.setIdCard("idCard");
        request.setAmount("100.00");
        request.setAccessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiLnvo7nmoQiLCJpc3MiOiLpobrlvrfmsb3ov5AiLCJleHAiOjE1Nzc3ODAzNDksImlhdCI6MTU3NzE3NTU0OSwianRpIjoiNzVmNTMyZGQtNDNhNC00YTlmLTg4MjctYTBmNGE3MTA5NGEzIn0.uWxr0wYuvCC8NjPlREbFz7q_HSst21_THYnVea5Urmc");
        Map<String, String> params = SdtSignUtil.sign(request);
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.putAll(params);
        JSONObject jsonObject = SdtOkHttp3Util.post("http://localhost:8081/business/pay", requestParams);
        PayResponse response = JSONObject.parseObject(jsonObject.toJSONString(), PayResponse.class);
        System.out.println(JSON.toJSONString(response));
    }
}
