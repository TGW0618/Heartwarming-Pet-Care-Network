package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.Rooms;
import com.tgwei.demopet.demos.web.service.RoomsService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RoomsController {

    @Resource
    private RoomsService roomsService;


    //    获取全部房间信息
    @GetMapping("/getAllRooms")
    public Result getAllRooms() {
        List<Rooms> rooms = roomsService.getAllRooms();
        return Result.success(rooms);
    }

    //    获取可用房间信息
    @GetMapping("/getAvailableRooms")
    public Result getAvailableRooms() {
        List<Rooms> rooms = roomsService.getAvailableRooms();
        return Result.success(rooms);
    }


    //    房间数统计
    @GetMapping("/getRoomsStatistics")
    public Result getRoomsStatistics() {
        List<Rooms> rooms = roomsService.getRoomsStatistics();
        return Result.success(rooms);
    }

    //删除房间
    @DeleteMapping("/deleteRooms/{id}")
    public Result deleteRooms(HttpServletRequest request, @PathVariable Long id) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("role");
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        System.out.println("角色:" + userRole);
        if ("admin".equals(userRole) || "foster_staff".equals(userRole)) {
            Result result = roomsService.deleteRooms(tokenUserId, id);
            return Result.success(result);

        } else {
            return Result.error(403, "无权限访问");
        }


    }

    //    修改房间信息
    @PutMapping("/updateRooms")
    public Result updateRooms(HttpServletRequest request, @RequestBody Rooms rooms) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String userRole = (String) request.getAttribute("role");
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }
        if ("admin".equals(userRole) || "foster_staff".equals(userRole)) {
            Result result = roomsService.updateRooms(tokenUserId, rooms);
            return Result.success(result);

        } else {
            return Result.error(403, "无权限访问");
        }
    }

}
