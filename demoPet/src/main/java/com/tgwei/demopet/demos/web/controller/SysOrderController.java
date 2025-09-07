package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.ServiceItem;
import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.ServiceOrderVO;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import com.tgwei.demopet.demos.web.service.ServiceItemService;
import com.tgwei.demopet.demos.web.service.SysOrderService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sysOrder")
public class SysOrderController {

    @Resource
    private SysOrderService sysOrderService;

    @Resource
    private ServiceItemService serviceItemService;


    //    获取订单(根据角色，订单类型（可为空），角色id（可为空）获取订单信息)[ok]
    @GetMapping("/getOrderAll")
    public Result getOrderAll(HttpServletRequest request,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        try {
            // 创建分页对象
            com.github.pagehelper.PageInfo<ServiceOrderVO> pageInfoOrderList = sysOrderService.getOrderAll(request, pageNum, pageSize);
            return Result.success(pageInfoOrderList);
        } catch (Exception e) {
            return Result.error(500, "查询订单列表失败: " + e.getMessage());
        }
    }

    //    根据订单id获取记录相关信息(医疗记录、寄养记录)
    @GetMapping("/getRecordByOrderId/{orderId}")
    public Result getRecordByOrderId(HttpServletRequest request, @PathVariable Integer orderId) {
        return sysOrderService.getRecordByOrderId(request, orderId);
    }


    // 获取所有订单信息
    @GetMapping("/getAllSysOrder")
    public Result getAllSysOrder() {
        try {
            List<SysOrder> sysOrders = sysOrderService.getAllSysOrder();
            return Result.success(sysOrders);
        } catch (Exception e) {
            return Result.error(500, "查询订单列表失败: " + e.getMessage());
        }
    }


    //    获取预约订单（根据订单状态判断）
    @GetMapping("/getSysOrderByStatus")
    public Result getSysOrderByStatus() {
        try {
            List<ServiceOrderVO> sysOrders = sysOrderService.getSysOrderByStatus();
            return Result.success(sysOrders);
        } catch (Exception e) {
            return Result.error(500, "查询订单列表失败: " + e.getMessage());
        }

    }

    //    获取医疗预约订单（根据员工id查询订单后根据订单状态判断）
    @GetMapping("/getSysOrderByEmployeeId")
    public Result getSysOrderByEmployeeId(HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            Long tokenUserId = (Long) request.getAttribute("userId");

            System.out.print("claims: " + claims + " tokenUserId: " + tokenUserId);

            if (claims == null || tokenUserId == null) {
                return Result.error(401, "未授权访问");
            }

            List<ServiceOrderVO> sysOrders = sysOrderService.getSysOrderByEmployeeId(tokenUserId);
            return Result.success(sysOrders);
        } catch (Exception e) {
            return Result.error(500, "查询订单列表失败: " + e.getMessage());
        }
    }

    /*
     * 根据订单id获取订单相关信息（连表查询）
     * */
    @GetMapping("/getSysOrderById/{id}")
    public Result getSysOrderById(@PathVariable Integer id) {
        try {
            ServiceOrderVO order = sysOrderService.getSysOrderById(id);
            if (order == null) {
                return Result.error(404, "未找到订单信息");
            }
            return Result.success(order);
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            return Result.error(500, "查询订单详情失败: " + e.getMessage());
        }
    }

    /*
     * 根据用户id获取订单信息
     */
    @GetMapping("/getSysOrderByUserId")
    public Result getSysOrderByUserId(@RequestParam Integer userId,
                                      @RequestParam(required = false) String status,
                                      @RequestParam(required = false) String paymentStatus) {
        try {
            List<ServiceOrderVO> orders = sysOrderService.getSysOrderByUserId(userId, status, paymentStatus);
            return Result.success(orders);
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            return Result.error(500, "查询订单失败: " + e.getMessage());
        }
    }


    //  模糊搜索订单
    @GetMapping("/searchOrders")
    public Result searchOrders(@RequestParam Integer userId,
                               @RequestParam String keyword) {
        try {
            List<ServiceOrderVO> orders = sysOrderService.searchOrders(userId, keyword);
            return Result.success(orders);
        } catch (Exception e) {
            return Result.error(500, "搜索订单失败: " + e.getMessage());
        }
    }


    // 根据订单id删除订单
    @DeleteMapping("/deleteSysOrder/{id}")
    public Result deleteSysOrder(@PathVariable Integer id) {
        try {
            boolean result = sysOrderService.deleteSysOrder(id);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.error(500, "删除失败");
            }
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            return Result.error(500, "删除订单失败: " + e.getMessage());
        }
    }


    // 根据订单id修改订单信息
    @PutMapping("/updateSysOrder/{id}")
    public Result updateSysOrder(@PathVariable long id, @RequestBody ServiceOrder serviceOrder) {
        try {
            serviceOrder.setId(id);
            boolean result = sysOrderService.updateSysOrder(serviceOrder);
            if (result) {
                return Result.success("订单更新成功");
            } else {
                return Result.error(500, "订单更新失败");
            }
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        } catch (Exception e) {
            return Result.error(500, "更新订单失败: " + e.getMessage());
        }
    }


    //    根据订单id修改订单状态(订单处理)


    // 创建订单(新增)
    @PostMapping("/createSysOrder")
    public Result createSysOrder(HttpServletRequest request, @RequestBody ServiceOrder serviceOrder) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            Long tokenUserId = (Long) request.getAttribute("userId");

            System.out.print("claims: " + claims + " tokenUserId: " + tokenUserId);

            if (claims == null || tokenUserId == null) {
                return Result.error(401, "未授权访问");
            }

            serviceOrder.setUserId(tokenUserId);

            // 获取服务项目信息
            ServiceItem serviceItem = serviceItemService.getServiceItemById(serviceOrder.getServiceId());
            if (serviceItem == null) {
                return Result.error(400, "服务项目不存在");
            }

            serviceOrder.setServiceType(serviceItem.getServiceType().toString());
            // 设置订单金额为服务项目价格
            serviceOrder.setAmount(serviceItem.getPrice());

            boolean result = sysOrderService.createSysOrder(serviceOrder);
            if (result) {
                return Result.success("订单创建成功");
            } else {
                return Result.error(500, "订单创建失败");
            }
        } catch (Exception e) {
            return Result.error(500, "创建订单失败: " + e.getMessage());
        }
    }
}
