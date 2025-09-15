package com.tgwei.demopet.demos.web.entity;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class ServiceOperationRecord {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 服务项ID
     */
    private Long serviceId;

    /**
     * 操作类型：create, update, delete, on_shelf, off_shelf
     */
    private OperationType operationType;

    /**
     * 操作说明
     */
    private String operationDescription;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

    /**
     * 操作类型枚举
     */
    @Getter
    public enum OperationType {
        CREATE("create"),
        UPDATE("update"),
        DELETE("delete"),
        ON_SHELF("on_shelf"),
        OFF_SHELF("off_shelf");

        private final String value;

        OperationType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
