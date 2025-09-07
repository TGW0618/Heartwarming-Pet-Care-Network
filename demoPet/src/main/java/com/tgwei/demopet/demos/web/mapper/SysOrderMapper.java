package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.ServiceOrderVO;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysOrderMapper {

    //获取所有订单信息
    List<SysOrder> getAllSysOrder();

    //    获取预约订单（根据订单状态判断）
    List<ServiceOrderVO> getSysOrderByStatus(@Param("status") String confirmed);

    //根据用户id获取订单信息
    List<ServiceOrderVO> getSysOrderByUserId(@Param("userId") Integer userId,
                                             @Param("status") String status,

                                             @Param("paymentStatus") String paymentStatus);

    @Select("select service_order.service_type from service_order where id = #{orderId}")
    String getOrderTypeById(Integer orderId);







    // 根据订单id删除订单
    boolean deleteSysOrder(Integer id);

    // 用户创建订单
    boolean createSysOrder(ServiceOrder serviceOrder);


    // 根据订单id获取订单相关信息（连表查询）
    ServiceOrderVO getSysOrderById(Integer id);

    //    根据订单id修改订单信息
    boolean updateSysOrder(ServiceOrder serviceOrder);

    // 模糊搜索订单
    List<ServiceOrderVO> searchOrders(@Param("userId") Integer userId, @Param("keyword") String keyword);

    //    获取医疗预约订单（根据员工id查询订单后根据订单状态判断）
    List<ServiceOrderVO> getSysOrderByEmployeeId(Long employeeId);

    //    获取订单(根据角色，订单类型（可为空），角色id（可为空）获取订单信息)
    List<ServiceOrderVO> getOrderAll(@Param("userId") Long userId, @Param("orderType") String orderType, @Param("employeeId") Long employeeId);


}
