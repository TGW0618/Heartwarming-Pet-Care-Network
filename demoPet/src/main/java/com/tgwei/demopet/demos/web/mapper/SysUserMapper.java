package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SysUserMapper {

    //   查询全部宠物主人信息
    @Select("select * from sys_user where role='owner'")
    List<SysUser> getAllSysUserOwner();

    //    查询全部员工信息
    @Select("select * from sys_user where role='foster_staff' or role='veterinarian'")
    List<SysUser> getAllSysUserEmployee();
}
