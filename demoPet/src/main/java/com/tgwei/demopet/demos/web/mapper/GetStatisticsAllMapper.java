package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.GetStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
