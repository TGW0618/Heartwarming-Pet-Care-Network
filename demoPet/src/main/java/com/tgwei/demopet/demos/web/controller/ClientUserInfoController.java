package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.service.ClientUserInfoService;
import com.tgwei.demopet.demos.web.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/ClientUserInfo")
public class ClientUserInfoController {

    @Resource
    private ClientUserInfoService clientUserInfoService;

    @Resource
    private JwtUtil jwtUtil;

    // 根据id查询用户信息
    @GetMapping("/getUserById")
    public Result getUserById(HttpServletRequest request, @RequestParam(required = false) Integer id) {
        // 从JWT token中获取当前用户信息
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.error(401, "未授权访问");
        }

        try {
            String token = authHeader.substring(7);
            // 验证token是否过期
            if (jwtUtil.isTokenExpired(token)) {
                return Result.error(401, "token已过期");
            }

            // 从token中获取用户信息
            Claims claims = jwtUtil.getClaimsFromToken(token);
            Long tokenUserId = Long.valueOf(claims.get("userId").toString());

            // 如果没有传id参数，则查询当前用户信息
            if (id == null) {
                id = tokenUserId.intValue();
            }

            // 权限检查：用户只能查询自己的信息，或者管理员可以查询所有用户
            String role = (String) claims.get("role");
            if (!tokenUserId.equals((long)id) && !"admin".equals(role)) {
                return Result.error(403, "无权限访问该用户信息");
            }

            SysUser sysUser = clientUserInfoService.getUserById(id);
            if (sysUser == null) {
                return Result.error(404, "用户不存在");
            }

            return Result.success(sysUser);
        } catch (Exception e) {
            return Result.error(500, "token解析失败");
        }
    }
}
