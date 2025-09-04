package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {


    @Select("select * from sys_user where username = #{username} and role!='owner'")
    SysUser getSysUserByAdminUsername(String username);

//    @Select("select * from sys_user where (username = #{username} or phone = #{phone}) and role='owner'")
@Select("select * from sys_user where (username = #{username} or phone = #{phone})")
SysUser getSysUserByPetUsername(@Param("username") String username, @Param("phone") String phone);


}
