package com.tgwei.demopet.demos.web.common;

import com.tgwei.demopet.demos.web.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器类，用于验证HTTP请求中的JWT令牌有效性
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求处理之前进行拦截验证JWT令牌
     *
     * @param request  HTTP请求对象，用于获取请求头信息
     * @param response HTTP响应对象，用于设置响应状态码
     * @param handler  处理器对象
     * @return boolean 返回true表示验证通过，继续执行后续操作；返回false表示验证失败，中断请求处理
     * @throws Exception 处理过程中可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取Authorization令牌
        String token = request.getHeader("Authorization");

        // 验证令牌格式是否正确（以Bearer开头）
        if (token != null && token.startsWith("Bearer ")) {
            // 提取实际的JWT令牌（去除Bearer前缀）
            token = token.substring(7);
            try {
                // 检查令牌是否已过期
                if (jwtUtil.isTokenExpired(token)) {
                    // 令牌过期，设置401未授权状态码并拒绝请求
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"code\":401,\"message\":\"token已过期\"}");
                    return false;
                }

                // 解析token并将用户信息存入request attribute
                Claims claims = jwtUtil.getClaimsFromToken(token);
                request.setAttribute("claims", claims);
                request.setAttribute("userId", Long.valueOf(claims.get("userId").toString()));
                request.setAttribute("role", claims.get("role"));
                return true;
            } catch (Exception e) {
                // 令牌解析异常，设置401未授权状态码并拒绝请求
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"token解析失败\"}");
                return false;
            }
        } else {
            // 没有有效的token
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未授权访问\"}");
            return false;
        }
    }
}
