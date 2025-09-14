package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.GetStatistics;
import com.tgwei.demopet.demos.web.service.GetStatisticsAllService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/getStatistics")
public class GetStatisticsController {

    @Resource
    private GetStatisticsAllService getStatisticsAllService;


    @GetMapping("/getStatisticsAll")
    public Result getStatisticsAll() {
        List<GetStatistics> StatisticsAll = getStatisticsAllService.getStatisticsAll();
        if (StatisticsAll != null) {
            return Result.success(StatisticsAll);
        } else {
            return Result.error();
        }
    }

    @GetMapping("/getStatisticsRooms")
    public Result getStatisticsRooms() {
        List<GetStatistics> StatisticsRooms = getStatisticsAllService.getStatisticsRooms();

        return Result.success(StatisticsRooms);
    }


    //    运营总览
    /*
     * 实时数据与环比数据
     * 今日实时数据：今日订单数、今日营收、今日新增用户。
     * 环比数据：与昨日相比的增长率/下降率。
     * */
    @GetMapping("/getOverviewData")
    public Result getOverviewData() {
//        获取今日日期
        String today = LocalDateTime.now().toLocalDate().toString();
        Map<String, Object> OverviewData = new HashMap<>();

        // 今日订单数量、今日营收收入、今日新增用户、今日寄养订单数量、今日医疗订单数量
        OverviewData.put("orderCountToday", getStatisticsAllService.getTodayOrderCount());
        OverviewData.put("revenueToday", getStatisticsAllService.getTodayRevenue());
        OverviewData.put("newUsersToday", getStatisticsAllService.getTodayNewUsers());
        OverviewData.put("fosterOrderCountToday", getStatisticsAllService.getTotalFosterOrders());
        OverviewData.put("medicalOrderCountToday", getStatisticsAllService.getTotalMedicalOrders());
        // 日期
        OverviewData.put("date", today);

        // 昨日数据
        OverviewData.put("orderCountYesterday", getStatisticsAllService.getYesterdayOrderCount());
        OverviewData.put("revenueYesterday", getStatisticsAllService.getYesterdayRevenue());
        OverviewData.put("newUsersYesterday", getStatisticsAllService.getYesterdayNewUsers());


//        环比数据(与昨日数据对比)
        OverviewData.put("orderGrowthRate", getStatisticsAllService.calculateOrderGrowthRate());
        OverviewData.put("revenueGrowthRate", getStatisticsAllService.calculateRevenueGrowthRate());
        OverviewData.put("userGrowthRate", getStatisticsAllService.calculateUserGrowthRate());


        return Result.success(OverviewData);
    }

    /*
     * 累计数据
     * 总订单数、总营收、注册用户总数、宠物总数。
     * */
    @GetMapping("/getAccumulateData")
    public Result getStatisticsAccumulate() {
        Map<String, Object> AccumulateData = new HashMap<>();
        AccumulateData.put("orderCount", getStatisticsAllService.getTotalOrderCount());
        AccumulateData.put("revenue", getStatisticsAllService.getTotalRevenue());
        AccumulateData.put("userCount", getStatisticsAllService.getTotalUsers());
        AccumulateData.put("petCount", getStatisticsAllService.getTotalPets());
        return Result.success(AccumulateData);
    }


}
