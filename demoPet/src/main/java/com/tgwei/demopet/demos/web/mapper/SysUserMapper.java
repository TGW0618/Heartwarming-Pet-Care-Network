package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SysUserMapper {

    //   查询全部宠物主人信息
    @Select("select * from sys_user where role = 'owner' order by id desc")
    List<SysUser> getAllSysUserOwner();

    // 查询全部员工信息（支持筛选）
    List<SysUser> getAllSysUserEmployee(@Param("roleSpecialty") String roleSpecialty, @Param("role") String role);


    //    根据id查询用户信息
    @Select("select * from sys_user where id=#{id}")
    SysUser getSysUserById(Integer id);

    //    根据id修改用户信息
    @Select("update sys_user set username=#{username},real_name=#{realName},sex=#{sex},phone=#{phone},email=#{email},avatar=#{avatar},status=#{status},role=#{role} where id=#{id}")
    void updateSysUsers(SysUser sysUser);

    //    修改用户密码
    @Select("update sys_user set password=sha2(concat(#{password},'Pet@2025Sys'),256) where id=#{id}")
    void updateSysUserPwd(SysUser sysUser);

    //新增用户
    @Insert("insert into sys_user(username,password,real_name,sex,phone,email,avatar,role,status) values(#{username},sha2(concat(#{password},'Pet@2025Sys'),256),#{realName},#{sex},#{phone},#{email},#{avatar},#{role},#{status})")
    void addSysUsers(SysUser sysUser);

    //    删除用户
    @Delete("delete from sys_user where id=#{id}")
    void deleteSysUsers(Integer id);

    //    修改状态
    @Select("update sys_user set status=#{status} where id=#{id}")
    void updateSysUserStatus(SysUser sysUser);
}
