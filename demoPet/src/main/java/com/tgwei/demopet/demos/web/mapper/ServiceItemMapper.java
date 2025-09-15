package com.tgwei.demopet.demos.web.mapper;


import com.tgwei.demopet.demos.web.entity.ServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceItemMapper {

    //    查询全部服务项目

    List<ServiceItem> getAllServiceItem(@Param("status") Integer status);


    int addServiceItem(ServiceItem serviceItem);

    int updateServiceItem(ServiceItem serviceItem);



    //    获取热门服务项
    List<ServiceItem> getHotServiceItem( @Param("limit") int limit);


    //    按服务类型查询对应的服务项目
    @Select("select * from service_item where service_type = #{serviceType}")
    List<ServiceItem> getServiceItemAllServiceType(String serviceType);


    //    按服务类型查询对应的服务项目(已上架的)
    @Select("select * from service_item where  status = 1 and  service_type = #{serviceType}")
    List<ServiceItem> getAllServiceItemServiceType(String serviceType);

    //    按医疗类型查询对应的服务项目(已上架的)
    @Select("select * from service_item where status = 1 and medical_type = #{medicalType}")
    List<ServiceItem> getAllServiceItemMedicalType(String medicalType);

    //根据服务id查询服务(已上架的)
    @Select("select * from service_item where status = 1 and id = #{id}")
    ServiceItem getServiceItemById(Long id);


    @Delete("delete from service_item where id = #{id}")
    int deleteServiceItem(Long id);

    //    根据id查询服务项信息
    @Select("select * from service_item where id = #{id}")
    ServiceItem getServiceItemId(Long id);

}
