package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.SysNotification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Transactional
public class SysNotificationService {
    @Resource
    SysNotificationService sysNotificationMapper;


    //    创建通知信息
    public SysNotification createSysNotification(SysNotification sysNotification) {
        sysNotification.setCreateTime(LocalDateTime.now());
        return sysNotificationMapper.createSysNotification(sysNotification);
    }

}
