package com.tgwei.demopet.demos.web.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetStatistics implements Serializable {
    //    系统用户统计
    private Long totalSysUser;//用户总数
    private Long totalCustomers;//客户总数
    private Long totalVeterinarian;//兽医总数
    private Long totalFosterStaff;//寄养员总数
    private Long totalStaff;//员工总数

    //    系统订单统计
    private Long totalOrder;//订单总数
    private Long totalMedicalOrders;//医疗订单总数
    private Long totalFosterOrders;//寄养订单总数

    //    系统收入
    private Double totalIncome;//系统总收入
    private Double totalMedicalIncome;//医疗收入
    private Double totalFosterIncome;//寄养收入

//    房间统计

    /**
     * 总房间数
     */
    private Integer totalRooms;

    /**
     * 可用房间数
     */
    private Integer availableRooms;

    /**
     * 已用房间数
     */
    private Integer occupiedRooms;


}
