package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.ServiceItem;
import com.tgwei.demopet.demos.web.service.ServiceItemService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/serviceItems")
public class ServiceItemController {

    @Resource
    private ServiceItemService serviceItemService;

    //    获取所有服务项(根据权限获取对应的服务)[🆗]
    /* 获取所有服务项(根据权限获取对应的服务)
     *
     * 1、管理员角色获取所有服务项
     * 2、兽医角色获取医疗服务项
     * 3、饲养员角色获取寄养服务项
     * 4、其他角色获取所有服务项（已上架的）
     * */
    @GetMapping("/getServiceItemAll")
    public Result getServiceItemAll(HttpServletRequest request,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            Long tokenUserId = (Long) request.getAttribute("userId");
            String userRole = (String) request.getAttribute("role");
            if (claims == null || tokenUserId == null || userRole == null) {
                return Result.error(500, "获取服务项失败: 请检查token是否正确");
            }

            com.github.pagehelper.PageInfo<ServiceItem> pageInfoServiceItemList = serviceItemService.getServiceItemAll(userRole, pageNum, pageSize);
            return Result.success(pageInfoServiceItemList);
        } catch (Exception e) {
            return Result.error(500, "获取服务项失败: " + e.getMessage());
        }

    }

    //    新增服务（根据对应的角色权限）
    /*
     * 新增服务（根据对应的角色权限）
     * 1、兽医角色新增医疗服务项
     * 2、饲养员角色新增寄养服务项
     * 3、管理员新增所有服务项
     * 4、员工新增的服务默认是下架的，需要管理员进行审批上架
     *
     * */
    @PostMapping("/addServiceItem")
    public Result addServiceItem(HttpServletRequest request, @RequestBody ServiceItem serviceItem) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("role");
        if (claims == null || tokenUserId == null || userRole == null) {
            return Result.error(500, "获取服务项失败: 请检查token是否正确");
        }

        return serviceItemService.addServiceItem(userRole, tokenUserId, serviceItem);
    }


    //修改服务信息[🆗]
    @PutMapping("/updateServiceItem")
    public Result updateServiceItem(HttpServletRequest request, @RequestBody ServiceItem serviceItem) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("role");
        if (claims == null || tokenUserId == null || userRole == null) {
            return Result.error(500, "获取服务项失败: 请检查token是否正确");
        }

        return serviceItemService.updateServiceItem(userRole, tokenUserId, serviceItem);
    }


    //    删除服务项[🆗]
    @DeleteMapping("/deleteServiceItem/{id}")
    public Result deleteServiceItem(HttpServletRequest request, @PathVariable Long id) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("role");
        if (claims == null || tokenUserId == null || userRole == null) {
            return Result.error(500, "获取服务项失败: 请检查token是否正确");
        }
        Result result = serviceItemService.deleteServiceItem(userRole, tokenUserId, id);
        return Result.success(result);
    }


//    获取热门服务项
    @GetMapping("/getHotServiceItem")
    public Result getHotServiceItem(@RequestParam(defaultValue = "5") int limit) {
        List<ServiceItem> serviceItems = serviceItemService.getHotServiceItem(limit);
        return Result.success(serviceItems);
    }













    //    获取所有服务项
    @GetMapping("/getAllServiceItem")
    public Result getAllServiceItem(@RequestParam(required = false) String serviceType,
                                    @RequestParam(required = false) String medicalType
    ) {
        List<ServiceItem> serviceItems = serviceItemService.getAllServiceItem(serviceType, medicalType);
        return Result.success(serviceItems);
    }

    //    根据服务id查询服务项信息
    @GetMapping("/getServiceItemById")
    public Result getServiceItemById(@RequestParam("id") Long id) {
        ServiceItem serviceItem = serviceItemService.getServiceItemById(id);
        return Result.success(serviceItem);
    }


}
