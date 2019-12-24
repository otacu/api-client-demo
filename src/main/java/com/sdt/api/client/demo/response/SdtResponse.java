package com.sdt.api.client.demo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SdtResponse implements Serializable {
    /**
     * 状态码
     */
    private Integer status;

    /**
     * 提示信息
     */
    private String msg;
}
