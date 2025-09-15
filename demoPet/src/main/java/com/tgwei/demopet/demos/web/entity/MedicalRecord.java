package com.tgwei.demopet.demos.web.entity;


import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class MedicalRecord {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 订单ID（唯一）
     */
    private Long orderId;

    /**
     * 兽医ID（关联sys_user.id）
     */
    private Long veterinarianId;

    /**
     * 症状描述
     */
    private String symptoms;

    /**
     * 诊断结果
     */
    private String diagnosis;

    /**
     * 治疗方案
     */
    private String treatment;

    /**
     * 处方（JSON格式：[{"name":"药名","dosage":"剂量","frequency":"频率"},...]）
     */
    private String prescription;

    /**
     * 下次复诊日期
     */
    private Instant nextVisitDate;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
