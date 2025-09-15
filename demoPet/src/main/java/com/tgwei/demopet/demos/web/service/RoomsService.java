package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.Rooms;
import com.tgwei.demopet.demos.web.entity.ServiceOperationRecord;
import com.tgwei.demopet.demos.web.mapper.RoomsMapper;
import com.tgwei.demopet.demos.web.utils.ObjectDiffUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomsService {


    @Resource
    private RoomsMapper roomsMapper;

    @Resource
    private ServiceOperationRecordService serviceOperationRecordService;

    public List<Rooms> getAllRooms() {
        return roomsMapper.getAllRooms();
    }

    public List<Rooms> getRoomsStatistics() {
        return roomsMapper.getRoomsStatistics();
    }

    public List<Rooms> getAvailableRooms() {
        return roomsMapper.getAvailableRooms();
    }


    //    删除房间
    public Result deleteRooms(Long tokenUserId, Long id) {
        Rooms rooms = roomsMapper.getRoomsById(id);
        if (rooms == null) {
            return Result.error(500, "房间不存在");
        }

        int result = roomsMapper.deleteRooms(id);

        if (result > 0) {
            ServiceOperationRecord serviceOperationRecord = new ServiceOperationRecord();
            serviceOperationRecord.setUserId(tokenUserId);
            serviceOperationRecord.setServiceId(id);
            serviceOperationRecord.setOperationType(ServiceOperationRecord.OperationType.DELETE);
            serviceOperationRecord.setOperationDescription("删除房间:" + rooms.getRoomNumber());
            serviceOperationRecord.setCreateTime(LocalDateTime.now());

            try {
                serviceOperationRecordService.addServiceOperationRecord(serviceOperationRecord);
            } catch (Exception e) {
                // 日志记录失败不影响主要业务逻辑
                System.err.println("记录服务操作日志失败: " + e.getMessage());
            }
            return Result.success("删除成功");
        }
        return Result.error(500, "删除失败");
    }

    //    修改房间信息
    public Result updateRooms(Long tokenUserId, Rooms rooms) {
        Rooms oldRooms = roomsMapper.getRoomsById(rooms.getRoomId());
        if (oldRooms == null) {
            return Result.error(500, "房间不存在");
        }

        int result = roomsMapper.updateRooms(rooms);
        if (result > 0) {
            ServiceOperationRecord serviceOperationRecord = new ServiceOperationRecord();
            serviceOperationRecord.setUserId(tokenUserId);
            serviceOperationRecord.setServiceId(rooms.getRoomId());
            serviceOperationRecord.setOperationType(ServiceOperationRecord.OperationType.UPDATE);
            // 使用工具类生成差异描述
            String diffDescription = ObjectDiffUtil.generateOperationDescription(oldRooms, rooms, "UPDATE");
            serviceOperationRecord.setOperationDescription(diffDescription);
            serviceOperationRecord.setCreateTime(LocalDateTime.now());

            try {
                serviceOperationRecordService.addServiceOperationRecord(serviceOperationRecord);
            } catch (Exception e) {
                // 日志记录失败不影响主要业务逻辑
                System.err.println("记录服务操作日志失败: " + e.getMessage());
            }
            return Result.success("修改成功");
        }
        return Result.error(500, "修改失败");
    }

//
}
