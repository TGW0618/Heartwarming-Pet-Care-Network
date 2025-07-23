package com.tgwei.demopet.demos.web.exception;


import com.tgwei.demopet.demos.web.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * 用于统一处理控制器层抛出的异常
 */
@ControllerAdvice("com.tgwei.demopet.demos.web.controller")
public class GlobalResponseHandler {

    /**
     * 处理所有异常
     * 当控制器层抛出Exception类及其子类的异常时，该方法会被调用
     *
     * @param e 异常对象
     * @return 返回一个表示错误的结果对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error();
    }

    /**
     * 处理自定义异常
     * 当控制器层抛出CustomException类及其子类的异常时，该方法会被调用
     *
     * @param e 自定义异常对象，包含错误代码和错误信息
     * @return 返回一个包含错误代码和错误信息的结果对象
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e) {

        return Result.error(e.getCode(),e.getMsg());
    }
}