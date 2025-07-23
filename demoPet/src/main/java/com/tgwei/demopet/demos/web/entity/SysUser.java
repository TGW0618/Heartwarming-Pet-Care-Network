package com.tgwei.demopet.demos.web.entity;

import lombok.*;

import java.util.Date;


@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String realName;

    @Getter
    public enum Sex {
        male("male"),
        female("female"),
        other("other");

        private final String value;

        Sex(String value) {
            this.value = value;
        }

    }

    private Sex sex;
    private String phone;
    private String email;
    private String avatar;

    @Getter
    public enum Role {
        admin("admin"),
        owner("owner"),
        veterinarian("veterinarian"),
        foster_staff("foster_staff");

        private final String value;

        Role(String value) {
            this.value = value;
        }

    }

    private Role role;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
