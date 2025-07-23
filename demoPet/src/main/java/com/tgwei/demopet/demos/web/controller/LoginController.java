package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    //    后台管理系统登录接口
    @PostMapping("/admin/login")
    public Result adminLogin(@RequestBody SysUser sysUser) {
        SysUser dbSysUser = loginService.adminLogin(sysUser);
        return Result.success(dbSysUser);
    }

//    客户端登录接口
    @PostMapping("/client/login")
    public Result clientLogin(@RequestBody SysUser sysUser) {
        SysUser dbSysUser = loginService.adminLogin(sysUser);
        return Result.success(dbSysUser);
    }


}
