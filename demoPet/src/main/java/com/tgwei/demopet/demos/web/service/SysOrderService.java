package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.SysOrder;
import com.tgwei.demopet.demos.web.mapper.SysOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysOrderService {

    @Autowired
    private SysOrderMapper sysOrderMapper;


    public List<SysOrder> getAllSysOrder() {
        return sysOrderMapper.getAllSysOrder();
    }

    public Boolean deleteSysOrder(Integer id) {
        return sysOrderMapper.deleteSysOrder(id);
    }
}
