package com.tgwei.demopet.demos.web.entity;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServiceOrder {
    private Long id;                            // 订单ID
    private String orderNo;                     // 订单编号（唯一，格式：YYYYMMDD+8位随机数）
    private Long userId;                        // 用户ID（宠物主人）
    private Long employeeId;                    // 员工ID
    private Long petId;                         // 宠物ID
    private Long serviceId;                     // 服务项目ID
    private String serviceType;                 // 服务类型（foster, medical）
    private LocalDateTime appointmentStartTime; // 预约开始时间
    private LocalDateTime appointmentEndTime;   // 预约结束时间（寄养用）
    private LocalDateTime actualStartTime;      // 实际开始时间
    private LocalDateTime actualEndTime;        // 实际结束时间
    private BigDecimal amount;                  // 订单金额（元）
    private BigDecimal price;
    private String status;                      // 订单状态：pending, confirmed, processing, completed, canceled
    private Integer paymentStatus;              // 支付状态：0-未支付，1-已支付，2-已退款
    private String paymentMethod;               // 支付方式：wechat, alipay, cash
    private LocalDateTime paymentTime;          // 支付时间
    private String transactionId;               // 第三方支付流水号
    private String remark;                      // 用户备注（如宠物特殊需求）
    private LocalDateTime createTime;           // 创建时间
    private LocalDateTime updateTime;           // 更新时间




}
