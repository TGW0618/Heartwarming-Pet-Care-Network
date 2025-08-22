package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.PetsInfo;
import com.tgwei.demopet.demos.web.service.PetsInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/petsInfo")
public class PetsInfoController {

    @Resource
    private PetsInfoService petsInfoService;

    // 根据用户id查询该用户的所有宠物信息
    @GetMapping("/getAllPetsInfo")
    public Result getAllPetsInfo(HttpServletRequest request, @RequestParam(required = false) Integer ownerId) {
        // 从request attribute中获取用户信息
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        // 检查用户认证信息是否存在
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        // 如果没有传id参数，则查询当前用户信息
        if (ownerId == null) {
            ownerId = tokenUserId.intValue();
        }

        // 权限检查：用户只能查询自己的信息，或者管理员可以查询所有用户
        if (!tokenUserId.equals((long) ownerId) && !"admin".equals(role)) {
            return Result.error(403, "无权限访问该用户信息");
        }

        List<PetsInfo> petsInfo = petsInfoService.getAllPetsInfo(ownerId);

        if (petsInfo == null || petsInfo.isEmpty()) {
            return Result.error(404, "暂无数据");
        }
        return Result.success(petsInfo);
    }


    //    根据宠物id查询宠物信息
    @GetMapping("/getPetsInfoById")
    public Result getPetsInfoById(HttpServletRequest request, @RequestParam Integer id) {
        // 从request attribute中获取用户信息
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");

        // 检查用户认证信息是否存在
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        PetsInfo petsInfo = petsInfoService.getPetsInfoById(id);
        if (petsInfo == null) {
            return Result.error(404, "暂无数据");
        }
        return Result.success(petsInfo);
    }

    //    根据宠物id删除宠物信息
    @DeleteMapping("/deletePetsInfoById")
    public Result deletePetsInfoById(HttpServletRequest request, @RequestParam Integer id) {

        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        return petsInfoService.deletePetsInfoById(id) ? Result.success() : Result.error();

    }

    //    根据宠物id更新数据
    @PutMapping("/updatePetsInfoById")
    public Result updatePetsInfoById(HttpServletRequest request, @RequestBody PetsInfo petsInfo) {

        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        if (petsInfo.getUpdateTime() == null) {
            petsInfo.setUpdateTime(LocalDateTime.now());
        }

        return petsInfoService.updatePetsInfoById(petsInfo) ? Result.success() : Result.error();

    }

    //    添加宠物数据
    @PostMapping("/addPetsInfo")
    public Result addPetsInfo(@RequestBody PetsInfo petsInfo, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");
        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        if (petsInfo.getUpdateTime() == null && petsInfo.getCreateTime() == null) {
            petsInfo.setUpdateTime(LocalDateTime.now());
            petsInfo.setCreateTime(LocalDateTime.now());

        }
        petsInfo.setOwnerId(tokenUserId);
        return petsInfoService.addPetsInfo(petsInfo) ? Result.success() : Result.error();

    }
}
