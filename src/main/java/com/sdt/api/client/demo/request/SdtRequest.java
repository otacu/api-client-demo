package com.sdt.api.client.demo.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求公共参数
 */
@Data
public class SdtRequest implements Serializable {

    /**
     * accessToken
     */
    @JSONField(serialize = false)
    private String accessToken;

}
