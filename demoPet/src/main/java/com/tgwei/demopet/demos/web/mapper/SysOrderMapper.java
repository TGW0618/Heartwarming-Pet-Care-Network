package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.ServiceOrder;
import com.tgwei.demopet.demos.web.entity.ServiceOrderVO;
import com.tgwei.demopet.demos.web.entity.SysOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysOrderMapper {

    //获取所有订单信息
    List<SysOrder> getAllSysOrder();

    //根据用户id获取订单信息
    List<ServiceOrderVO> getSysOrderByUserId(@Param("userId") Integer userId,
                                             @Param("status") String status,

                                             @Param("paymentStatus") String paymentStatus);

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















}
