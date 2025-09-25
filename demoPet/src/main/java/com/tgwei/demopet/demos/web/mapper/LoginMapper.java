package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {


    @Select("select * from sys_user where (username = #{username} or phone = #{phone} or email = #{email}) and role!='owner'")
    SysUser getBySysUser(@Param("username") String username, @Param("phone") String phone, @Param("email") String email);

    //    @Select("select * from sys_user where (username = #{username} or phone = #{phone}) and role='owner'")
    @Select("select * from sys_user where (username = #{username} or phone = #{phone} or email = #{email})")
    SysUser getSysUserByPetUsername(@Param("username") String username, @Param("phone") String phone, @Param("email") String email);


}
