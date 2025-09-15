package com.tgwei.demopet.demos.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情VO实体类
 * 包含订单信息、用户信息、宠物信息和服务信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceOrderVO {

    // ==================== 订单信息 ====================
    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 医疗分类
     */
    private String medicalType;

    /**
     * 预约开始时间
     */
    private LocalDateTime appointmentStartTime;

    /**
     * 预约结束时间
     */
    private LocalDateTime appointmentEndTime;

    /**
     * 实际开始时间
     */
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 支付状态
     */
    private Integer paymentStatus;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 第三方支付流水号
     */
    private String transactionId;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // ==================== 用户信息 ====================
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 员工
     */
    private String employeeUsername;
    private String employeeRealName;
    private String employeeAvatar;

    // ==================== 宠物信息 ====================
    /**
     * 宠物ID
     */
    private Long petId;

    /**
     * 宠物名称
     */
    private String petName;

    /**
     * 宠物类型
     */
    private String petType;

    /**
     * 宠物品种
     */
    private String petBreed;

    /**
     * 宠物图片
     */
    private String petImage;

    /**
     * 宠物年龄
     */
    private Integer petAge;

    /**
     * 宠物性别
     */
    private String petGender;

    /**
     * 宠物出生日期
     */
    private LocalDateTime petBirthDate;

    /**
     * 宠物体重
     */
    private BigDecimal petWeight;

    /**
     * 宠物健康状态
     */
    private String petHealthStatus;

    /**
     * 宠物过敏史
     */
    private String petAllergies;

    // ==================== 服务信息 ====================
    /**
     * 服务ID
     */
    private Long serviceId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务分类
     */
    private String serviceCategory;

    /**
     * 服务描述
     */
    private String serviceDescription;

    /**
     * 服务图片
     */
    private String serviceImage;

    /**
     * 服务价格
     */
    private BigDecimal servicePrice;

    /**
     * 服务时长（分钟）
     */
    private Integer serviceDuration;

    /**
     * 服务状态
     */
    private Integer serviceStatus;
}
