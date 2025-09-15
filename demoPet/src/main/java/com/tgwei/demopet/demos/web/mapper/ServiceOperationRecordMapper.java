package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.ServiceOperationRecord;
import org.apache.ibatis.annotations.Insert;

public interface ServiceOperationRecordMapper {

    @Insert("insert into service_operation_record(user_id, service_id, " +
            "operation_type, operation_description, create_time)" +
            " values(#{userId}, #{serviceId}, " +
            "#{operationType}, #{operationDescription}, #{createTime})")
    void addServiceOperationRecord(ServiceOperationRecord serviceOperationRecord);
}
