package com.sdt.api.client.demo.request;

import lombok.Data;

/**
 * 支付请求
 */
@Data
public class SdtPayRequest extends SdtRequest {
    private String transationNo;

    private String name;

    private String idCard;

    private String amount;
}
