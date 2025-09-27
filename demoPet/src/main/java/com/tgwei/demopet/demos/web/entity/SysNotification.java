package com.tgwei.demopet.demos.web.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysNotification {
    private Long id; // 通知id
    private Long userId;// 接收通知的用户id
    private Type type;//通知类型
    private String title;// 标题
    private String content;// 内容
    private Integer isRead;// 是否已读
    private LocalDateTime createTime;// 创建时间

    public enum Type {
        SYSTEM("system"), // 系统通知
        ORDER("order"),// 订单通知
        PROMOTION("promotion");

        private final String enumValue;

        Type(String enumValue) {
            this.enumValue = enumValue;
        }

    }
}
