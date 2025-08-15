package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.SysUser;
import com.tgwei.demopet.demos.web.mapper.ClientUserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientUserInfoService {

    @Resource
    private ClientUserInfoMapper clientUserInfoMapper;


    public SysUser getUserById(Integer id) {
        return clientUserInfoMapper.getUserById(id);
    }
}
