package com.tgwei.demopet.demos.web.entity;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServiceItem {
    /**
     * 服务ID
     */
    private Long id;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务类型：寄养/医疗/美容
     */
    private ServiceType serviceType;

    /**
     * 医疗服务类型：疫苗/诊疗
     */
    private MedicalType medicalType;

    /**
     * 服务分类（如"小型犬寄养""猫三联疫苗"）
     */
    private String category;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 服务价格（元）
     */
    private BigDecimal price;

    /**
     * 预计时长（分钟，医疗/美容用）
     */
    private Integer duration;

    /**
     * 服务图片URL
     */
    private String imageUrl;

    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    private int deleted;

    /**
     * 服务类型枚举
     */
    @Getter
    public enum ServiceType {
        foster("foster", "寄养"),
        medical("medical", "医疗");

        private final String code;
        private final String description;

        ServiceType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public static ServiceType fromCode(String code) {
            for (ServiceType type : ServiceType.values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown service type code: " + code);
        }
    }

    ;

    /**
     * 医疗服务类型枚举
     */
    @Getter
    public enum MedicalType {
        vacation("vacation", "疫苗"),
        diagnosis("diagnosis", "诊疗");

        private final String code;
        private final String description;

        MedicalType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public static MedicalType fromCode(String code) {
            for (MedicalType type : MedicalType.values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown medical type code: " + code);
        }

    }
}
