package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysNotification;
import com.tgwei.demopet.demos.web.service.SysNotificationService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class SysNotificationController {

    @Resource
    private SysNotificationService sysNotificationService;

    /*
     * 创建通知信息
     * */
    @PostMapping("/createSysNotification")
    public Result createSysNotification(SysNotification sysNotification) {
        Result sysNotificationItem = sysNotificationService.createSysNotification(sysNotification);
        return Result.success(sysNotificationItem);
    }

    /*
     * 根据登录的用户获取对应的通知信息
     * */
    @GetMapping("/getSysNotification")
    public Result getSysNotification(HttpServletRequest request,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false) Integer limit


    ) {
        // 从请求中获取用户ID
        Long userId = (Long) request.getAttribute("userId");
        System.out.println("userId:" + userId);
        if (userId == null) {
            return Result.error(401, "请先登录");
        }
        com.github.pagehelper.PageInfo<SysNotification> sysNotificationList = sysNotificationService.getSysNotification(userId, pageNum, pageSize, limit);
        return Result.success(sysNotificationList);
    }
}
