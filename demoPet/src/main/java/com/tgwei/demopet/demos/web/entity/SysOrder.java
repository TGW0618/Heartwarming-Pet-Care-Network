package com.tgwei.demopet.demos.web.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "订单详情信息")
public class SysOrder {
    // ================= 核心标识信息 =================
    @Schema(description = "订单id")
    private Long id;

    @Schema(description = "订单编号", example = "ORD20230001")
    private String orderNo;

    @Schema(description = "订单状态", example = "已完成")
    private String status;

    @Schema(description = "订单金额", example = "299.00")
    private BigDecimal amount;

    // ================= 客户信息 =================
    @Schema(description = "客户姓名", example = "张三")
    private String realName;

    @Schema(description = "客户账号", example = "user123")
    private String username;

    // ================= 宠物服务信息 =================
    @Schema(description = "宠物名称", example = "豆豆")
    private String petName;

    @Schema(description = "宠物类型", example = "狗")
    private String petType;

    @Schema(description = "服务项目", example = "基础洗澡")
    private String serviceName;
    @Schema(description = "服务类型", example = "foster")
    private String serviceType;

    // ================= 时间信息 =================
    @Schema(description = "预约时间", example = "2023-08-15 14:00:00")
    private LocalDateTime appointmentStartTime;

    @Schema(description = "服务开始时间", example = "2023-08-15 14:05:00")
    private LocalDateTime actualStartTime;

    @Schema(description = "服务结束时间", example = "2023-08-15 15:30:00")
    private LocalDateTime actualEndTime;

    @Schema(description = "支付时间", example = "2023-08-15 13:55:00")
    private LocalDateTime paymentTime;

    @Schema(description = "下单时间", example = "2023-08-14 10:00:00")
    private LocalDateTime createTime;

    // ================= 支付信息 =================
    @Schema(description = "支付状态", example = "已支付")
    private String paymentStatus;

    @Schema(description = "支付方式", example = "微信支付")
    private String paymentMethod;
}
