package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    //   查询全部宠物主人信息
    public List<SysUser> getAllSysUserOwner() {

        return sysUserMapper.getAllSysUserOwner();
    }

    //    查询全部员工信息
    public List<SysUser> getAllSysUserEmployee(String roleSpecialty, String role, Integer status) {
        return sysUserMapper.getAllSysUserEmployee(roleSpecialty, role, status);
    }

    //根据id查询用户信息
    public SysUser getSysUserById(Integer id) {
        return sysUserMapper.getSysUserById(id);
    }

    // 修改用户 信息
    public void updateSysUsers(SysUser sysUser) {
        if (sysUser.getStatus() == null) {
            sysUser.setStatus(1);
        }
        sysUserMapper.updateSysUsers(sysUser);
    }


    // 修改用户 密码
    public void updateSysUserPwd(SysUser sysUser) {
        sysUserMapper.updateSysUserPwd(sysUser);
    }

    //新增用户
    public Result addSysUsers(SysUser sysUser) {
        SysUser existingUser = sysUserMapper.selectByUsername(sysUser.getUsername());
        SysUser existingPhone = sysUserMapper.selectByPhone(sysUser.getPhone());
        if (existingUser != null) {
            return Result.error(401, "该用户已存在");
        }
        if (existingPhone != null) {
            return Result.error(401, "该手机号已存在");
        }

        if (sysUser.getRealName() == null) {
            sysUser.setRealName(sysUser.getUsername());
        }
        if (sysUser.getRole() == null) {
            sysUser.setRole(SysUser.Role.valueOf("owner"));
        }
        if (sysUser.getStatus() == null) {
            sysUser.setStatus(1);
        }

        // 处理数据库插入操作的结果
        int result = sysUserMapper.addSysUsers(sysUser);
        if (result > 0) {
            return Result.success("用户添加成功");
        } else {
            return Result.error(401, "用户添加失败");
        }
    }

    //    删除用户
    public void deleteSysUsers(Integer id) {
        sysUserMapper.deleteSysUsers(id);
    }

    //    修改用户状态
    public void updateSysUserStatus(SysUser sysUser) {
        sysUserMapper.updateSysUserStatus(sysUser);
    }
}
