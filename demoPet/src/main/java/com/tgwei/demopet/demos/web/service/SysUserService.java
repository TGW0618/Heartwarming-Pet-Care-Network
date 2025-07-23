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


}
