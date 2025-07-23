package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {


    @Select("select * from sys_user where username=#{username} and role!='owner'")
    SysUser getSysUserByAdminUsername(String username);
}
