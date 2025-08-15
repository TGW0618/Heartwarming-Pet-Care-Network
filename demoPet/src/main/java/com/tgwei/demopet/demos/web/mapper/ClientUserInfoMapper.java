package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClientUserInfoMapper {


    @Select("select * from sys_user where id = #{id}")
    SysUser getUserById(Integer id);
}
