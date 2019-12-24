package com.sdt.api.client.demo.response;

import lombok.Data;

@Data
public class GetAccessTokenResponse extends SdtResponse {
    private GetAccessTokenResponseData data;
}
