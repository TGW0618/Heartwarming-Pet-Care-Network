package com.tgwei.demopet.demos.web.utils;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectDiffUtil {

    /**
     * 比较两个对象的字段差异
     * @param oldObj 旧对象
     * @param newObj 新对象
     * @return 差异描述字符串
     */
    public static String getDiffDescription(Object oldObj, Object newObj) {
        if (oldObj == null || newObj == null) {
            return "对象为空";
        }

        StringBuilder diffDesc = new StringBuilder();
        diffDesc.append("修改了: ");

        try {
            Class<?> clazz = oldObj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            boolean hasDiff = false;
            List<String> diffFields = new ArrayList<>();

            for (Field field : fields) {
                field.setAccessible(true);

                Object oldValue = field.get(oldObj);
                Object newValue = field.get(newObj);

                // 比较字段值是否不同
                if (!Objects.equals(oldValue, newValue)) {
                    String fieldName = getFieldDisplayName(clazz.getSimpleName(), field.getName());
                    diffFields.add(fieldName + "[" + formatValue(oldValue) + " → " + formatValue(newValue) + "]");
                    hasDiff = true;
                }
            }

            if (hasDiff) {
                diffDesc.append(String.join(", ", diffFields));
            } else {
                diffDesc.append("无变化");
            }

        } catch (Exception e) {
            diffDesc.append("比较失败: ").append(e.getMessage());
        }

        return diffDesc.toString();
    }

    /**
     * 根据类名获取字段的显示名称
     */
    private static String getFieldDisplayName(String className, String fieldName) {
        // 服务项字段映射
        if ("ServiceItem".equals(className)) {
            Map<String, String> serviceFieldNames = new HashMap<>();
            serviceFieldNames.put("serviceName", "服务名称");
            serviceFieldNames.put("serviceType", "服务类型");
            serviceFieldNames.put("medicalType", "医疗类型");
            serviceFieldNames.put("category", "服务分类");
            serviceFieldNames.put("description", "服务描述");
            serviceFieldNames.put("price", "服务价格");
            serviceFieldNames.put("duration", "预计时长");
            serviceFieldNames.put("imageUrl", "图片URL");
            serviceFieldNames.put("status", "服务状态");

            return serviceFieldNames.getOrDefault(fieldName, fieldName);
        }

        // 房间字段映射
        else if ("Rooms".equals(className)) {
            Map<String, String> roomFieldNames = new HashMap<>();
            roomFieldNames.put("roomNumber", "房间编号");
            roomFieldNames.put("roomType", "房间类型");
            roomFieldNames.put("roomName", "房间名称");
            roomFieldNames.put("capacity", "容纳数量");
            roomFieldNames.put("size", "房间面积");
            roomFieldNames.put("pricePerDay", "每日价格");
            roomFieldNames.put("status", "房间状态");
            roomFieldNames.put("features", "特色设施");
            roomFieldNames.put("description", "房间描述");
            roomFieldNames.put("notes", "备注信息");

            return roomFieldNames.getOrDefault(fieldName, fieldName);
        }

        // 默认返回字段名
        return fieldName;
    }

    /**
     * 格式化值显示
     */
    private static String formatValue(Object value) {
        if (value == null) {
            return "空";
        }
        // 对于空字符串显示为"空"
        if (value instanceof String && ((String) value).isEmpty()) {
            return "空";
        }
        return value.toString();
    }

    /**
     * 生成操作描述（用于不同实体类型）
     */
    public static String generateOperationDescription(Object oldObj, Object newObj, String operationType) {
        if (oldObj == null || newObj == null) {
            return "对象为空";
        }

        String className = oldObj.getClass().getSimpleName();
        String entityName = getEntityDisplayName(className);

        if ("UPDATE".equals(operationType)) {
            return entityName + " " + getDiffDescription(oldObj, newObj);
        } else if ("CREATE".equals(operationType)) {
            return "新增" + entityName;
        } else if ("DELETE".equals(operationType)) {
            return "删除" + entityName;
        }

        return operationType + " " + entityName;
    }

    /**
     * 获取实体类显示名称
     */
    private static String getEntityDisplayName(String className) {
        Map<String, String> entityNames = new HashMap<>();
        entityNames.put("ServiceItem", "服务项");
        entityNames.put("Rooms", "房间");
        entityNames.put("User", "用户");
        entityNames.put("Order", "订单");

        return entityNames.getOrDefault(className, className);
    }
}
