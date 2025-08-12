package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.service.SysUserService;
import org.springframework.web.bind.annotation.*;

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

    //    根据id查询用户信息
    @GetMapping("/getSysUserById")
    public Result getSysUserById(@RequestParam Integer id) {
        SysUser sysUser = sysUserService.getSysUserById(id);
        return Result.success(sysUser);
    }

    //    修改用户信息
    @PutMapping("/updateSysUsers")
    public Result updateSysUsers(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUsers(sysUser);
        return Result.success();
    }

    //    修改用户密码
    @PutMapping("/updateSysUserPwd")
    public Result updateSysUserPwd(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUserPwd(sysUser);
        return Result.success();
    }

    //    新增用户
    @PostMapping("/addSysUsers")
    public Result addSysUsers(@RequestBody SysUser sysUser) {
        sysUserService.addSysUsers(sysUser);
        return Result.success();
    }

//    删除 用户

    @DeleteMapping("/deleteSysUsers")
    public Result deleteSysUsers(@RequestParam Integer id) {
        sysUserService.deleteSysUsers(id);
        return Result.success();
    }

//    修改 状态
    @PutMapping("/updateSysUserStatus")
    public Result updateSysUserStatus(@RequestBody  SysUser sysUser) {
        sysUserService.updateSysUserStatus(sysUser);
        return Result.success();
    }


}
