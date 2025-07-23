package com.tgwei.demopet.demos.web.common;

import lombok.Getter;

/**
 * 定义了API接口返回的通用结果代码枚举
 * 通过不同的状态码和消息，可以清晰地表示API操作的结果状态
 */
@Getter
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),
    /**
     * 参数校验失败
     */
    VALIDATE_FAILED(400, "参数校验失败"),
    /**
     * 未登录或token已过期
     */
    UNAUTHORIZED(401, "未登录或token已过期"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务不可用");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 对应的消息描述
     */
    private final String message;

    /**
     * 构造函数，初始化状态码和消息描述
     *
     * @param code    状态码
     * @param message 消息描述
     */
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
