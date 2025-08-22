package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.service.ClientUserInfoService;
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

    // 根据id查询用户信息
    @GetMapping("/getUserById")
    public Result getUserById(HttpServletRequest request, @RequestParam(required = false) Integer id) {
        // 从request attribute中获取用户信息
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        // 检查用户认证信息是否存在
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        // 如果没有传id参数，则查询当前用户信息
        if (id == null) {
            id = tokenUserId.intValue();
        }

        // 权限检查：用户只能查询自己的信息，或者管理员可以查询所有用户
        if (!tokenUserId.equals((long) id) && !"admin".equals(role)) {
            return Result.error(403, "无权限访问该用户信息");
        }

        SysUser sysUser = clientUserInfoService.getUserById(id);
        if (sysUser == null) {
            return Result.error(404, "用户不存在");
        }

        return Result.success(sysUser);
    }
}
