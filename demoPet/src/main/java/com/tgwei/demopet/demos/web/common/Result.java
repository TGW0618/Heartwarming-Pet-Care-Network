package com.tgwei.demopet.demos.web.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果类，用于封装接口返回数据
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    // 状态码
    private int code;
    // 消息
    private String message;
    // 数据（不再使用泛型，统一为 Object）
    private Object data;
    // 时间戳
    private long timestamp = System.currentTimeMillis();

    // 私有构造方法，用于创建Result对象
    private Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // 公共静态方法，用于创建成功的Result对象，不包含数据
    public static Result success() {
        return success(null);
    }

    // 公共静态方法，用于创建包含数据的成功Result对象
    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    // 公共静态方法，用于创建失败的Result对象
    public static Result failed() {
        return failed(ResultCode.FAILED);
    }

    // 公共静态方法，用于创建包含特定错误码的失败Result对象
    public static Result failed(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), null);
    }

    // 公共静态方法，用于创建包含自定义错误消息的失败Result对象
    public static Result failed(String message) {
        return new Result(ResultCode.FAILED.getCode(), message, null);
    }

    // 公共静态方法，用于创建包含异常信息的失败Result对象
    public static Result failed(Throwable e) {
        return new Result(ResultCode.FAILED.getCode(), e.getMessage(), null);
    }

    // 公共静态方法，用于创建验证失败的Result对象
    public static Result validateFailed() {
        return new Result(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null);
    }
}
