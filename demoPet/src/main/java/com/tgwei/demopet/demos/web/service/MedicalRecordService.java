package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.MedicalRecord;
import com.tgwei.demopet.demos.web.entity.MedicalRecordVO;
import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.mapper.MedicalRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicalRecordService {

    @Resource
    private MedicalRecordMapper medicalRecordMapper;

    @Resource
    private SysOrderService sysOrderService;


    /*
     * 新增医疗记录(处理医疗订单)
     * 1、根据订单id修改订单状态
     * 2、添加医疗记录
     * */
    @Transactional
    public Result addMedicalRecord(MedicalRecord medicalRecord) {
        Long orderId = medicalRecord.getOrderId();
        System.out.println("订单id：" + orderId);

        // 先更新订单状态为已完成
        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(orderId); // 设置订单ID
        serviceOrder.setStatus("completed");
        boolean updateResult = sysOrderService.updateSysOrder(serviceOrder);

        if (!updateResult) {
            return Result.error(401, "更新订单状态失败");
        }

        medicalRecord.setCreateTime(LocalDateTime.now());
        medicalRecord.setUpdateTime(LocalDateTime.now());
        int result = medicalRecordMapper.addMedicalRecord(medicalRecord);
        // 根据 result 判断插入是否成功
        if (result > 0) {
            return Result.success("处理成功");
        } else {
            return Result.error(401, "处理失败");
        }
    }

    //    根据订单ID查询医疗记录
    public Result getMedicalRecordByOrderId(Integer orderId) {
        List<MedicalRecordVO> medicalRecord = medicalRecordMapper.getMedicalRecordByOrderId(orderId);
        return Result.success(medicalRecord);
    }
}
