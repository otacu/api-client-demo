package com.sdt.api.client.demo.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetAccessTokenResponseData implements Serializable {
    private String accessToken;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expiresTime;
}
