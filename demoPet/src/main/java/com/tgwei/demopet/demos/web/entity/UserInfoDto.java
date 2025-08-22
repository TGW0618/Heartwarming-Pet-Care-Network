package com.tgwei.demopet.demos.web.entity;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String realName;
    private String avatar;
    private String role;
    private Integer status;
    private String sex;

public UserInfoDto(SysUser sysUser) {
    this.id = sysUser.getId();
    this.username = sysUser.getUsername();
    this.phone = sysUser.getPhone();
    this.email = sysUser.getEmail();
    this.realName = sysUser.getRealName();
    this.avatar = sysUser.getAvatar();
    this.role = sysUser.getRole() != null ? sysUser.getRole().name() : null;
    this.status = sysUser.getStatus();
    this.sex = sysUser.getSex() != null ? sysUser.getSex().name() : null; // 添加空值检查
}


}
