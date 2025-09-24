package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.GetStatistics;
import com.tgwei.demopet.demos.web.entity.OrderAmountDTO;
import com.tgwei.demopet.demos.web.entity.OrderStatusDTO;
import com.tgwei.demopet.demos.web.entity.ServiceItemHotDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GetStatisticsAllMapper {


    List<GetStatistics> getStatisticsAll();

    List<GetStatistics> getStatisticsRooms();

    Integer countTodayOrders();

    Integer sumTodayRevenue();

    Integer countTodayNewUsers();

    Integer countYesterdayOrders();

    Integer countYesterdayRevenue();

    Integer countYesterdayNewUsers();

    Integer countOrders();

    Integer sumRevenue();

    Integer countUsers();

    Integer countPets();

    Integer  countFosterOrders();

    Integer  countMedicalOrders();

    List<OrderStatusDTO> getOrderStatus();

    List<ServiceItemHotDTO> getHotServiceItem(int i);

    List<OrderAmountDTO> getOrderAmount(@Param("days") int days,@Param("status") String status);
}
