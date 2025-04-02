package com.wuying.exception;

import com.wuying.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，用于捕获并处理应用程序中抛出的特定异常。
 * 通过 {@link RestControllerAdvice} 注解标记为全局异常处理类，
 * 所有Controller层抛出的异常都会被该类捕获并处理。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有未被其他异常处理器捕获的异常。
     * 判断异常信息是否为空或空字符串：
     * - 如果异常信息非空且长度大于 0，则返回异常信息；
     * - 否则返回默认的错误信息 "fail"。
     * @param ex 捕获到的异常对象
     * @return 返回统一的错误响应 {@link Result}，包含错误信息 "fail"
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        ex.printStackTrace(); // 打印异常堆栈信息
        return Result.failure(StringUtils.hasLength(ex.getMessage())? ex.getMessage() : "fail"); // 返回错误响应
    }

    /**
     * 处理自定义异常 {@link BigEventException}。
     *
     * @param ex 捕获到的 {@link BigEventException} 异常对象
     * @return 返回统一的错误响应 {@link Result}，包含错误码和错误信息
     */
    @ExceptionHandler(BigEventException.class)
    public Result handleException(BigEventException ex) {
        ex.printStackTrace(); // 打印异常堆栈信息
        return Result.failure(ex.getCode(), ex.getMessage()); // 返回错误响应
    }
}
