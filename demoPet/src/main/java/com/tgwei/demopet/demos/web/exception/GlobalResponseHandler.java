package com.tgwei.demopet.demos.web.exception;

import com.tgwei.demopet.demos.web.common.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局响应结果处理
 * 用于统一处理所有控制器的响应体，除非特别标记不进行包装
 */
@RestControllerAdvice(basePackages = "com.your.package.controller")
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否需要处理响应体
     *
     * @param returnType 返回值类型
     * @param converterType 转换器类型
     * @return 如果需要处理返回true，否则返回false
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 不处理已经包装过的结果或特殊注解标记的方法
        return !returnType.getParameterType().isAssignableFrom(Result.class)
                && returnType.getMethodAnnotation(NoWrap.class) == null;
    }

    /**
     * 在写入响应体之前进行处理
     *
     * @param body 原始响应体
     * @param returnType 返回值类型
     * @param selectedContentType 选定的内容类型
     * @param selectedConverterType 选定的转换器类型
     * @param request 请求
     * @param response 响应
     * @return 处理后的响应体
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // String类型需要特殊处理
        if (body instanceof String) {
            return Result.success(body);
        }
        // 已经包装过的不再处理
        if (body instanceof Result) {
            return body;
        }
        // 默认成功响应
        return Result.success(body);
    }

    /**
     * 不需要包装的注解标记
     * 用于标记不需要进行统一响应包装的方法
     */
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NoWrap {
    }
}
