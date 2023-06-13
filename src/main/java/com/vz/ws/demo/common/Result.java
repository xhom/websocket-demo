package com.vz.ws.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author visy.wang
 * @description: 通用返回体
 * @date 2023/5/11 10:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    public static Result success(){
        return success(null);
    }
    public static Result success(Object data){
        return new Result(true, ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage(), data);
    }

    public static Result failure(){
        return failure(ErrorCodeEnum.FAILURE, null);
    }
    public static Result failure(Object data){
        return failure(ErrorCodeEnum.FAILURE, data);
    }
    public static Result failure(ErrorCodeEnum errorCode){
        return failure(errorCode, null);
    }
    public static Result failure(ErrorCodeEnum errorCode, Object data){
        return failure(errorCode.getCode(), errorCode.getMessage(), data);
    }
    public static Result failure(Integer code, String message){
        return failure(code, message, null);
    }
    public static Result failure(Integer code, String message, Object data){
        return new Result(false, code, message, data);
    }
}
