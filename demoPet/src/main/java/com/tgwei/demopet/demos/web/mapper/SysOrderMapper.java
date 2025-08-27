package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysOrderMapper {

    List<SysOrder> getAllSysOrder();

    @Select("select * from service_order where service_order.user_id = #{tokenUserId}")
    List<SysOrder> getSysOrderByUserId(Long tokenUserId);

    @Delete("delete from service_order where service_order.id = #{id}")
    boolean deleteSysOrder(Integer id);

    Boolean createSysOrder(ServiceOrder serviceOrder);


}
