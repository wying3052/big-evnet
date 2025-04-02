package com.wuying.exception;

import lombok.Data;

/**
 * 自定义异常类，用于处理业务逻辑中的异常情况。
 * 继承自 {@link RuntimeException}，表示这是一个运行时异常。
 */
@Data
public class BigEventException extends RuntimeException {

    /**
     * 错误码，用于标识异常的类型或来源。
     */
    private Integer code;

    /**
     * 构造方法，用于创建一个带有错误码和错误信息的异常实例。
     *
     * @param code    错误码，用于标识异常的类型或来源。
     * @param message 错误信息，描述异常的详细信息。
     */
    public BigEventException(Integer code, String message) {
        super(message); // 调用父类构造方法，设置错误信息
        this.code = code; // 设置错误码
    }
}
