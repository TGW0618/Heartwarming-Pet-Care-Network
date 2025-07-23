package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    //   查询全部宠物主人信息
    @GetMapping("/getAllSysUserOwner")
    public Result getAllSysUserOwner() {
        List<SysUser> sysUser = sysUserService.getAllSysUserOwner();
        return Result.success(sysUser);
    }

    //    查询全部员工信息
    @GetMapping("/getAllSysUserEmployee")
    public Result getAllSysUserEmployee() {
        List<SysUser> sysUser = sysUserService.getAllSysUserEmployee();
        return Result.success(sysUser);
    }


}
