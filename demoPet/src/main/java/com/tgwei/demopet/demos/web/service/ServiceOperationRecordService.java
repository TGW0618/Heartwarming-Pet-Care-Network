package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.ServiceOperationRecord;
import com.tgwei.demopet.demos.web.mapper.ServiceOperationRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ServiceOperationRecordService {

    @Resource
    private ServiceOperationRecordMapper serviceOperationRecordMapper;

    //    插入记录
    public void addServiceOperationRecord(ServiceOperationRecord serviceOperationRecord) {
        serviceOperationRecordMapper.addServiceOperationRecord(serviceOperationRecord);
    }


}
