package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.ServiceItem;
import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import com.tgwei.demopet.demos.web.service.ServiceItemService;
import com.tgwei.demopet.demos.web.service.SysOrderService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sysOrder")
public class SysOrderController {

    @Autowired
    private SysOrderService sysOrderService;

    @Resource
    private ServiceItemService serviceItemService;

    //    获取所有订单信息(管理端用)
    @GetMapping("/getAllSysOrder")
    public Result getAllSysOrder() {
        List<SysOrder> sysOrders = sysOrderService.getAllSysOrder();
        return Result.success(sysOrders);

    }

    /*
     * 根据用户id获取订单信息（供客户端用户使用）
     *
     * */
    @GetMapping("/getSysOrderByUserId")
    public Result getSysOrderByUserId(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }
        return Result.success(sysOrderService.getSysOrderByUserId(tokenUserId));

    }

    //    根据订单id删除订单
    @DeleteMapping("/deleteSysOrder/{id}")
    public Result deleteSysOrder(@PathVariable Integer id) {
        boolean result = sysOrderService.deleteSysOrder(id);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.error();
        }
    }

    //    创建订单(新增)
    @PostMapping("/createSysOrder")
    public Result createSysOrder(HttpServletRequest request, @RequestBody ServiceOrder serviceOrder) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");

        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        serviceOrder.setUserId(tokenUserId);

        ServiceItem serviceItem = serviceItemService.getServiceItemById(serviceOrder.getServiceId());
        serviceOrder.setServiceType(String.valueOf(serviceItem.getServiceType()));

        boolean result = sysOrderService.createSysOrder(serviceOrder);
        if (result) {
            return Result.success("订单创建成功");
        } else {
            return Result.error();
        }
    }


//    模拟支付接口(根据订单id修改订单支付方式及支付状态)[正式开发是调用各方的支付接口]
}
