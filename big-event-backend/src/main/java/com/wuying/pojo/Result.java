package com.wuying.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private Integer code;//状态码 200成功
    private String message;//提示信息
    private T data;//返回数据

    //带数据成功响应
    public static <E> Result<E> success(E data) {
        return new Result<>(200, "success", data);
    }

    //无数据成功响应
    public static Result success() {
        return new Result<>(200, "success", null);
    }

    //失败响应
    public static Result failure(String message) {
        return new Result<>(200, message, null);
    }

    public static Result failure(Integer code, String message) {
        return new Result<>(code, message, null);
    }

}
