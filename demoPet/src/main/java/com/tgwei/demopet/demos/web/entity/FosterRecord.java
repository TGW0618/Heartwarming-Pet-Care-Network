package com.tgwei.demopet.demos.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FosterRecord {
    private Long id;
    private Long orderId;
    private Long staffId;
    private Integer roomId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkInTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkOutTime;
    private String dailyLog;
    private String foodPreference;
    private String notes;
    private Date createTime;
    private Date updateTime;
}
