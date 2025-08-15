package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.SysOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysOrderMapper {

    List<SysOrder> getAllSysOrder();


    @Delete("delete from service_order where service_order.id = #{id}")
    boolean deleteSysOrder(Integer id);
}
