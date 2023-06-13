package com.vz.ws.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author visy.wang
 * @description: 错误码枚举
 * @date 2023/5/11 10:37
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    SUCCESS(1, "操作成功"),
    FAILURE(0, "操作失败");


    private final Integer code;
    private final String message;
}
