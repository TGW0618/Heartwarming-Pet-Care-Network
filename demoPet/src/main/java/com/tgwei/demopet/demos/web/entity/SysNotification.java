package com.tgwei.demopet.demos.web.entity;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class SysNotification {
    private Long id; // 通知id
    private Long userId;// 接收通知的用户id
    private Type type;//通知类型
    private String title;// 标题
    private String content;// 内容
    private Long relatedOrderId;// 关联的订单id
    private Integer isRead;// 是否已读
    private LocalDateTime createTime;// 创建时间

    @Getter
    public enum Type {
        system("system"), // 系统通知
        order("order"),// 订单通知
        promotion("promotion");

        private final String enumValue;

        Type(String enumValue) {
            this.enumValue = enumValue;
        }

    }
}
