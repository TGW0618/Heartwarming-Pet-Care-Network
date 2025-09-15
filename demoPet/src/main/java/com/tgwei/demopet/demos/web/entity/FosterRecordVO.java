package com.tgwei.demopet.demos.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FosterRecordVO {

    /**
     * 处理业务的员工信息
     */
    private String employeeID;//员工id
    private String employeeRealName;//姓名
    private String employeeRole;//角色

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkInTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkOutTime;
    private String dailyLog;
    private String foodPreference;
    private String notes;


    /**
     * 房间编号（如：101、A102等）
     */
    private String roomNumber;

    /**
     * 房间类型：small-小型, medium-中型, large-大型, luxury-豪华, vip-贵宾
     */
    private RoomType roomType;

    /**
     * 房间类型枚举
     */
    public enum RoomType {
        small, medium, large, luxury, vip
    }

}
