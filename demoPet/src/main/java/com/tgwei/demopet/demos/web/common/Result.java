package com.tgwei.demopet.demos.web.common;

import lombok.Data;

/*
  通用返回结果类，用于封装接口返回数据
 */

/**
 * 用于封装返回给前端的结果数据
 */
@Data
public class Result {
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 创建一个成功的响应结果
     *
     * @return 成功的响应结果对象
     */
    public static Result success() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        return result;
    }

    /**
     * 创建一个携带数据的成功的响应结果
     *
     * @param data 响应数据
     * @return 成功的响应结果对象
     */
    public static Result success(Object data) {
        Result result = success();
        result.setData(data);
        return result;
    }

    /**
     * 创建一个错误的响应结果
     *
     * @return 错误的响应结果对象
     */
    public static Result error() {
        Result result = new Result();
        result.setCode(500);
        result.setMessage("系统错误");
        return result;
    }

    /**
     * 创建一个自定义错误信息的错误响应结果
     *
     * @param code 错误状态码
     * @param msg  错误消息
     * @return 错误的响应结果对象
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
}
