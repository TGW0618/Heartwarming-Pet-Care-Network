package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysNotification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysNotificationMapper {


    int createSysNotification(SysNotification sysNotification);

    List<SysNotification> getSysNotification(@Param("userId") Long userId, @Param("limit") Integer limit);
}
