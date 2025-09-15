package com.tgwei.demopet.demos.web.controller;


import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.MedicalRecord;
import com.tgwei.demopet.demos.web.service.MedicalRecordService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class MedicalRecordController {

    @Resource
    private MedicalRecordService medicalRecordService;


    /*
     * 新增医疗记录(处理医疗订单)
     * */
    @PostMapping("/addMedicalRecord")
    public Result addMedicalRecord(HttpServletRequest request, @RequestBody MedicalRecord medicalRecord) {
        Claims claims = (Claims) request.getAttribute("claims");
        Long tokenUserId = (Long) request.getAttribute("userId");

        if (claims == null || tokenUserId == null) {
            return Result.error(401, "未授权访问");
        }

        medicalRecord.setVeterinarianId(tokenUserId);
        return medicalRecordService.addMedicalRecord(medicalRecord);
    }

    //    根据订单ID查询医疗记录
    @GetMapping("/getMedicalRecordByOrderId/{orderId}")
    public Result getMedicalRecordByOrderId(@PathVariable Integer orderId) {
        return medicalRecordService.getMedicalRecordByOrderId(orderId);
    }

}
