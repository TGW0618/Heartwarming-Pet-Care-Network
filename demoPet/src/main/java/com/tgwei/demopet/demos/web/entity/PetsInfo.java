package com.tgwei.demopet.demos.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PetsInfo {
    private Long id;                // 宠物ID
    private Long ownerId;           // 所属主人ID（关联sys_user.id）
    private String petName;         // 宠物名称
    private String petType;         // 宠物类型: dog, cat, bird, reptile, other
    private String breed;           // 品种（如“金毛寻回犬”）
    private String gender;          // 性别: male, female, unknown


    private BigDecimal weight;      // 体重（单位：kg）
    private String healthStatus;    // 健康状态（如“健康”“需定期服药”）
    private String allergies;       // 过敏史（文本描述）
    private String avatar;          // 宠物照片URL

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;// 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;        // 更新时间
}
