package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.ServiceItem;
import com.tgwei.demopet.demos.web.entity.ServiceOperationRecord;
import com.tgwei.demopet.demos.web.mapper.ServiceItemMapper;
import com.tgwei.demopet.demos.web.utils.ObjectDiffUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceItemService {


    @Resource
    private ServiceItemMapper serviceItemMapper;

    @Resource
    private ServiceOperationRecordService serviceOperationRecordService;


    /*获取所有服务项(根据权限获取对应的服务)
     * 1、管理员角色获取所有服务项
     * 2、兽医角色获取医疗服务项
     * 3、饲养员角色获取寄养服务项
     * 4、其他角色获取所有服务项（已上架的）
     * */
    public com.github.pagehelper.PageInfo<ServiceItem> getServiceItemAll(String userRole, Integer pageNum, Integer pageSize) {

        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);

        List<ServiceItem> serviceItems;

        if ("admin".equals(userRole)) {
            serviceItems = serviceItemMapper.getAllServiceItem(null);
        } else if ("veterinarian".equals(userRole)) {
            serviceItems = serviceItemMapper.getServiceItemAllServiceType("medical");
        } else if ("foster_staff".equals(userRole)) {
            serviceItems = serviceItemMapper.getServiceItemAllServiceType("foster");
        } else {
            serviceItems = serviceItemMapper.getAllServiceItem(1);
        }
        return new com.github.pagehelper.PageInfo<>(serviceItems);

    }

    /*
     * 新增服务（根据对应的角色权限）
     * 1、兽医角色新增医疗服务项
     * 2、饲养员角色新增寄养服务项
     * 3、管理员新增所有服务项
     * 4、员工新增的服务默认是下架的，需要管理员进行审批上架
     *
     * */
    @Transactional
    public Result addServiceItem(String userRole, Long tokenUserId, ServiceItem serviceItem) {

//       判断角色新增的服务(管理员可以新增所有服务，医生只能新增医疗服务，寄养员只能新增寄养服务)
        // 根据用户角色设置服务类型
        if ("veterinarian".equals(userRole)) {
            if (!ServiceItem.ServiceType.valueOf("medical").equals(serviceItem.getServiceType())) {
                return Result.error(500, "权限不足");
            }
            serviceItem.setServiceType(ServiceItem.ServiceType.valueOf("medical"));
        } else if ("foster_staff".equals(userRole)) {
            if (!ServiceItem.ServiceType.valueOf("foster").equals(serviceItem.getServiceType())) {
                return Result.error(500, "权限不足");
            }
            serviceItem.setServiceType(ServiceItem.ServiceType.valueOf("foster"));

        } else if (!"admin".equals(userRole)) {
            return Result.error(500, "权限不足");
        }

        // 设置时间字段
        LocalDateTime now = LocalDateTime.now();
        serviceItem.setCreateTime(now);
        serviceItem.setUpdateTime(now);

        // 设置状态
        if ("admin".equals(userRole)) {
            serviceItem.setStatus(1); // 管理员直接上架
        } else {
            serviceItem.setStatus(0); // 员工新增默认下架
        }

        int result = serviceItemMapper.addServiceItem(serviceItem);

        if (result > 0) {
            ServiceOperationRecord serviceRecord = new ServiceOperationRecord();
            serviceRecord.setUserId(tokenUserId);
            serviceRecord.setServiceId(serviceItem.getId());
            serviceRecord.setOperationType(ServiceOperationRecord.OperationType.CREATE);
            serviceRecord.setOperationDescription("新增服务项" + serviceItem.getServiceName());
            serviceRecord.setCreateTime(now);
            try {
                serviceOperationRecordService.addServiceOperationRecord(serviceRecord);
            } catch (Exception e) {
                // 日志记录失败不影响主要业务逻辑
                System.err.println("记录服务操作日志失败: " + e.getMessage());

            }
            return Result.success("服务项添加成功");
        } else {
            return Result.error(500, "服务项添加失败");
        }


    }

    /*
     * 修改服务项
     * */
    public Result updateServiceItem(String userRole, Long tokenUserId, ServiceItem serviceItem) {
        // 检查服务项是否存在
        ServiceItem oldServiceItem = serviceItemMapper.getServiceItemId(serviceItem.getId());
        if (oldServiceItem == null) {
            return Result.error(500, "服务项不存在");
        }

        switch (userRole) {
            case "admin":
                break;
            case "veterinarian":
            case "foster_staff":
                if (oldServiceItem.getStatus() == 0) {
                    return Result.error(500, "服务项未上架，不允许修改");
                }
                if (!oldServiceItem.getServiceType().equals(serviceItem.getServiceType())) {
                    return Result.error(500, "服务项类型不允许修改");
                }
                if (!oldServiceItem.getServiceName().equals(serviceItem.getServiceName())) {
                    return Result.error(500, "服务项名称不允许修改");
                }
                break;
        }


        // 设置时间字段
        LocalDateTime now = LocalDateTime.now();
        serviceItem.setUpdateTime(now);

        int result = serviceItemMapper.updateServiceItem(serviceItem);
        if (result > 0) {
            ServiceOperationRecord serviceRecord = new ServiceOperationRecord();
            serviceRecord.setUserId(tokenUserId);
            serviceRecord.setServiceId(serviceItem.getId());
            serviceRecord.setOperationType(ServiceOperationRecord.OperationType.UPDATE);
            serviceRecord.setCreateTime(now);
            // 使用工具类生成差异描述
            String diffDescription = ObjectDiffUtil.getDiffDescription(oldServiceItem, serviceItem);
            serviceRecord.setOperationDescription("修改服务项[" + serviceItem.getServiceName() + "] " + diffDescription);

            try {
                serviceOperationRecordService.addServiceOperationRecord(serviceRecord);
            } catch (Exception e) {
                // 日志记录失败不影响主要业务逻辑
                System.err.println("记录服务操作日志失败: " + e.getMessage());
            }
        } else {
            return Result.error(500, "服务修改失败");
        }
        return Result.success("服务修改成功");
    }


    //    删除服务项
    @Transactional
    public Result deleteServiceItem(String userRole, Long tokenUserId, Long id) {
        if (!"admin".equals(userRole)) {
            return Result.error(403, "权限不足");
        }

//        检查服务项是否存在
        ServiceItem serviceItem = serviceItemMapper.getServiceItemId(id);
        if (serviceItem == null) {
            return Result.error(500, "服务项不存在");
        }

        // 执行软删除（标记为已删除）
        ServiceItem updateItem = new ServiceItem();
        updateItem.setId(id);
        updateItem.setDeleted(1); // 标记为已删除
        updateItem.setUpdateTime(LocalDateTime.now());

        int result = serviceItemMapper.updateServiceItem(updateItem);
        if (result > 0) {
            ServiceOperationRecord serviceRecord = new ServiceOperationRecord();
            serviceRecord.setUserId(tokenUserId);
            serviceRecord.setServiceId(id);
            serviceRecord.setOperationType(ServiceOperationRecord.OperationType.DELETE);
            serviceRecord.setOperationDescription("删除服务项:" + serviceItem.getServiceName());
            serviceRecord.setCreateTime(LocalDateTime.now());
            try {
                serviceOperationRecordService.addServiceOperationRecord(serviceRecord);
            } catch (Exception e) {
                // 日志记录失败不影响主要业务逻辑
                System.err.println("记录服务操作日志失败: " + e.getMessage());
            }

        } else {
            return Result.error(500, "删除失败");
        }
        return Result.success("删除成功");
    }

    //    获取所有服务项
    public List<ServiceItem> getAllServiceItem(String serviceType, String medicalType) {
        if (serviceType != null && !serviceType.isEmpty()) {
            return serviceItemMapper.getAllServiceItemServiceType(serviceType);
        } else if (medicalType != null && !medicalType.isEmpty()) {
            return serviceItemMapper.getAllServiceItemMedicalType(medicalType);
        } else {
            return serviceItemMapper.getAllServiceItem(1);
        }
    }


//    根据id获取服务项
    public ServiceItem getServiceItemById(Long id) {
        return serviceItemMapper.getServiceItemById(id);
    }


    //    获取热门服务项
    public List<ServiceItem> getHotServiceItem(int limit) {
        return serviceItemMapper.getHotServiceItem(limit);
    }
}
