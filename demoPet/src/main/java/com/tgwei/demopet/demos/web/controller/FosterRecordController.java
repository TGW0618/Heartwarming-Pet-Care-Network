package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.FosterRecord;
import com.tgwei.demopet.demos.web.service.FosterRecordService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class FosterRecordController {

    @Resource
    private FosterRecordService fosterRecordService;

    //    新增寄养记录(处理预约订单)
    @PostMapping("/addFosterRecord")
    public Result addFosterRecord(HttpServletRequest request, @RequestBody FosterRecord fosterRecord) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");

        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        fosterRecord.setStaffId(tokenUserId);

        fosterRecordService.addFosterRecord(fosterRecord);
        return Result.success();

    }

    //    根据订单id查询寄养相关记录(连表查询)
    @GetMapping("/getFosterRecordByOrderId/{orderId}")
    public Result getFosterRecordByOrderId(@PathVariable Integer orderId) {
        return fosterRecordService.getFosterRecordByOrderId(orderId);
    }
}
