package com.tgwei.demopet.demos.web.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data

public class Rooms {

    /**
     * 房间唯一标识ID
     */
    private Long roomId;

    /**
     * 房间编号（如：101、A102等）
     */
    private String roomNumber;

    /**
     * 房间类型：small-小型, medium-中型, large-大型, luxury-豪华, vip-贵宾
     */
    private String roomType;

    /**
     * 房间名称（如：阳光房、豪华套间等）
     */
    private String roomName;

    /**
     * 可容纳宠物数量
     */
    private Integer capacity;

    /**
     * 房间面积（平方米）
     */
    private BigDecimal size;

    /**
     * 每日寄养价格
     */
    private BigDecimal pricePerDay;

    /**
     * 房间状态：available-可用, occupied-已占用, maintenance-维护中, cleaning-清洁中
     */
    private String status;

    /**
     * 特色设施
     */
    private String features;

    /**
     * 房间详细描述
     */
    private String description;

    /**
     * 备注信息
     */
    private String notes;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 最后更新时间
     */
    private Timestamp updatedAt;

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
