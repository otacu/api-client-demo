package com.sdt.api.client.demo.response;

import lombok.Data;

@Data
public class PayResponse extends SdtResponse {
    private PayResponseData data;
}
