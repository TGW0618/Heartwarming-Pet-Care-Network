package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import com.tgwei.demopet.demos.web.service.SysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysOrder")
public class SysOrderController {

    @Autowired
    private SysOrderService sysOrderService;

    //    获取所有订单信息
    @GetMapping("/getAllSysOrder")
    public Result getAllSysOrder() {
        List<SysOrder> sysOrders = sysOrderService.getAllSysOrder();
        return Result.success(sysOrders);

    }

    //    根据id删除订单
    @DeleteMapping("/deleteSysOrder/{id}")
    public Result deleteSysOrder(@PathVariable Integer id) {
        boolean result = sysOrderService.deleteSysOrder(id);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.error();
        }
    }


}
