package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.mapper.CsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cs")
public class CsController {

    @Autowired
    private CsMapper csMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取所有用户信息
     * 该方法首先从Redis缓存中获取用户列表，如果缓存中不存在则从数据库查询，
     * 并将查询结果存入Redis缓存1小时
     *
     * @return Result 包含用户列表的统一返回结果
     */
    @GetMapping("/getAllUser")
    public Result getAllUser() {
        // 从Redis缓存中获取用户列表
        List<SysUser> users = (List<SysUser>) redisTemplate.opsForValue().get("userList");

        // 缓存未命中时，从数据库查询并设置缓存
        if (users == null) {
            users = csMapper.getAllUser();
            redisTemplate.opsForValue().set("userList", users, 1, TimeUnit.HOURS);

        }

        return Result.success(users);
    }


}
