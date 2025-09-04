package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.entity.UserInfoDto;
import com.tgwei.demopet.demos.web.service.LoginService;
import com.tgwei.demopet.demos.web.utils.JwtUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private JwtUtil jwtUtil;


    //    后台管理系统登录接口
    @PostMapping("/admin/login")
    public Result adminLogin(@RequestBody SysUser sysUser) {
//        SysUser dbSysUser = loginService.adminLogin(sysUser);
//        return Result.success(dbSysUser);

        return this.clientLogin(sysUser);

    }

    //    客户端登录接口
    @PostMapping("/client/login")
    @CrossOrigin(origins = "*")
    public Result clientLogin(@RequestBody SysUser sysUser) {

        SysUser userLoginInfo = loginService.clientLogin(sysUser);

        // 生成Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userLoginInfo.getId());
        claims.put("username", userLoginInfo.getUsername());
        claims.put("role", userLoginInfo.getRole());

        String token = jwtUtil.generateToken(claims, userLoginInfo.getUsername());

        // 使用DTO只返回必要信息
        UserInfoDto userInfoDto = new UserInfoDto(userLoginInfo);

        // 创建返回结果，包含Token
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("user", userInfoDto);
        resultData.put("token", token);

        return Result.success(resultData);
    }
}



