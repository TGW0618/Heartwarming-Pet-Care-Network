// CsMapper.java
package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CsMapper {
//    @Select("select * from sys_user")
    List<SysUser> getAllUser();
}
