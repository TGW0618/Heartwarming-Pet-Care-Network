package com.tgwei.demopet.demos.web.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常类，用于在系统中抛出带有特定错误代码和消息的异常
 * 这个类继承自RuntimeException，使得它成为一个unchecked exception，可以在不必显式声明或捕获的情况下使用
 */
@Setter
@Getter
public class CustomException extends RuntimeException {
    /**
     * 错误代码，用于标识异常的类型或原因
     */
    private Integer code;

    /**
     * 错误消息，提供关于异常的详细信息
     */
    private String msg;

    /**
     * 构造函数，用于创建带有特定错误代码和消息的CustomException实例
     *
     * @param code 错误代码
     * @param msg  错误消息
     */
    public CustomException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
