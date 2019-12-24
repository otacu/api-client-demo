package com.sdt.api.client.demo.util;

import com.alibaba.fastjson.JSON;
import com.sdt.api.client.demo.constant.CustomConstant;
import com.sdt.api.client.demo.constant.SdtParamNameConstant;
import com.sdt.api.client.demo.exception.SdtException;
import com.sdt.api.client.demo.request.SdtRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SdtSignUtil {
    private SdtSignUtil(){

    }

    /**
     * 签名
     * @param request
     * @return
     * @throws SdtException
     */
    public static Map<String, String> sign(SdtRequest request) throws SdtException {
        try {
            Map<String, String> params = new HashMap<>();
            params.put(SdtParamNameConstant.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
            params.put(SdtParamNameConstant.APP_KEY, CustomConstant.APP_KEY);
            params.put(SdtParamNameConstant.VERSION, CustomConstant.VERSION);
            params.put(SdtParamNameConstant.NONCE, UUID.randomUUID().toString());
            if (SdtStringUtil.isNotBlank(request.getAccessToken())) {
                params.put(SdtParamNameConstant.ACCESS_TOKEN, request.getAccessToken());
            }
            String json = JSON.toJSONString(request);
            String data = SdtSignUtil.convertToSortStr(params) + json;
            String sign = HmacUtil.byte2hex(HmacUtil.encryptHMAC(data, CustomConstant.APP_SECRECT));
            params.put(SdtParamNameConstant.SIGN, sign);
            params.put(SdtParamNameConstant.REQUEST, json);
            return params;
        } catch (Exception e) {
            throw new SdtException("签名异常");
        }
    }

    /**
     * 将Map转化为排序后的组字符串
     *
     * @param params
     * @return
     */
    private static String convertToSortStr(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }

        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();

        for (String key : keys) {
            String value = params.get(key);
            if (isNotEmpty(key, value)) {
                query.append(key).append(value);
            }
        }

        return query.toString();
    }

    private static boolean isNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !StringUtils.isEmpty(value);
            }
        }
        return result;
    }
}
