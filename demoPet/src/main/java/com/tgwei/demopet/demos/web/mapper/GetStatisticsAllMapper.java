package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.GetStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GetStatisticsAllMapper {


    List<GetStatistics> getStatisticsAll();

    List<GetStatistics> getStatisticsRooms();

    int countTodayOrders();

    int sumTodayRevenue();

    int countTodayNewUsers();

    int countYesterdayOrders();

    int countYesterdayRevenue();

    int countYesterdayNewUsers();

    int countOrders();

    int sumRevenue();

    int countUsers();

    int countPets();

    Object countFosterOrders();

    Object countMedicalOrders();
}
