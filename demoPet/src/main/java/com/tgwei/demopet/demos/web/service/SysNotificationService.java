package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysNotification;
import org.springframework.stereotype.Service;
import com.tgwei.demopet.demos.web.mapper.SysNotificationMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SysNotificationService {

    @Resource
    private SysNotificationMapper sysNotificationMapper;


    //    创建通知信息
    public Result createSysNotification(SysNotification sysNotification) {
        sysNotification.setCreateTime(LocalDateTime.now());
        sysNotification.setIsRead(0);
        int result = sysNotificationMapper.createSysNotification(sysNotification);
        if (result > 0) {
            return Result.success("创建通知成功");
        }
        return Result.error(500, "系统错误创建失败");

    }

    /*
     * 根据登录的用户获取对应的通知信息
     * */
    public com.github.pagehelper.PageInfo<SysNotification> getSysNotification(Long userId,
                                                                              Integer pageNum,
                                                                              Integer pageSize,
                                                                              Integer limit
    ) {

        // 根据用户ID查询所有系统通知
        List<SysNotification> sysNotificationList = sysNotificationMapper.getSysNotification(userId, limit);
        // 设置分页参数
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        // 返回分页结果
        return new com.github.pagehelper.PageInfo<>(sysNotificationList);
    }
}

