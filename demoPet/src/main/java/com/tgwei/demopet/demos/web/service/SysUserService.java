package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    //   查询全部宠物主人信息
    public List<SysUser> getAllSysUserOwner() {
        return sysUserMapper.getAllSysUserOwner();
    }

    //    查询全部员工信息
    public List<SysUser> getAllSysUserEmployee() {
        return sysUserMapper.getAllSysUserEmployee();
    }

    //根据id查询用户信息
    public SysUser getSysUserById(Integer id) {
        return sysUserMapper.getSysUserById(id);
    }

    // 修改用户 信息
    public void updateSysUsers(SysUser sysUser) {
        sysUserMapper.updateSysUsers(sysUser);
    }


    // 修改用户 密码
    public void updateSysUserPwd(SysUser sysUser) {
        sysUserMapper.updateSysUserPwd(sysUser);
    }

    //新增用户
    public void addSysUsers(SysUser sysUser) {
        sysUserMapper.addSysUsers(sysUser);
    }

    //    删除用户
    public void deleteSysUsers(Integer id) {
        sysUserMapper.deleteSysUsers(id);
    }
}
