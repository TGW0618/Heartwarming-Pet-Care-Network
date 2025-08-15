package com.tgwei.demopet.demos.web.service;


import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.exception.CustomException;
import com.tgwei.demopet.demos.web.mapper.LoginMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    // 从配置文件注入盐值
    @Value("${pet.security.salt}")
    private String salt;

    @Resource
    private LoginMapper loginMapper;

    public SysUser adminLogin(SysUser sysUser) {
        String username = sysUser.getUsername();
        SysUser dbSysUser = loginMapper.getSysUserByAdminUsername(username);

        // 验证用户是否存在
        if (dbSysUser == null) {
            throw new CustomException(400, "用户不存在");
        }

        // 验证用户是否被禁用
        if (dbSysUser.getStatus() != 1) {
            throw new CustomException(500, "账号已被禁用，请联系管理员");
        }

        String inputPassword = sysUser.getPassword();// 输入的明文密码
        String storedPassword = dbSysUser.getPassword();// 数据库中加密后的密码

        // 输入密码 + 盐值 加密（与数据库加密逻辑一致）
        String encryptedInputPassword = DigestUtils.sha256Hex(inputPassword + salt);

        // 比较加密后的密码
        if (!storedPassword.equals(encryptedInputPassword)) {
            throw new CustomException(400, "密码错误");
        }

        return dbSysUser;
    }

    public SysUser clientLogin(SysUser sysUser) {
        String username = sysUser.getUsername();
        String phone = sysUser.getPhone();
        SysUser dbSysUser = loginMapper.getSysUserByPetUsername(username, phone);


        // 验证用户是否存在
        if (dbSysUser == null) {
            throw new CustomException(400, "用户不存在");
        }

        // 验证用户是否被禁用
        if (dbSysUser.getStatus() != 1) {
            throw new CustomException(500, "账号已被禁用");
        }

        String inputPassword = sysUser.getPassword();// 输入的明文密码
        String storedPassword = dbSysUser.getPassword();// 数据库中加密后的密码

        // 输入密码 + 盐值 加密（与数据库加密逻辑一致）
        String encryptedInputPassword = DigestUtils.sha256Hex(inputPassword + salt);

        // 比较加密后的密码
        if (!storedPassword.equals(encryptedInputPassword)) {
            throw new CustomException(400, "密码错误");
        }

        return dbSysUser;
    }

}
