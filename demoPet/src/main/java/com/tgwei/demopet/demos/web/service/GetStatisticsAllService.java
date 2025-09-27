package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.GetStatistics;
import com.tgwei.demopet.demos.web.entity.OrderAmountDTO;
import com.tgwei.demopet.demos.web.entity.OrderStatusDTO;
import com.tgwei.demopet.demos.web.entity.ServiceItemHotDTO;
import com.tgwei.demopet.demos.web.mapper.GetStatisticsAllMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class GetStatisticsAllService {

    @Autowired
    GetStatisticsAllMapper getStatisticsAllMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public List<GetStatistics> getStatisticsAll() {
        Object StatisticsAll = redisTemplate.opsForValue().get("StatisticsAll");
        System.out.println("StatisticsAll: " + StatisticsAll);
        if (StatisticsAll == null) {
            redisTemplate.opsForValue().set("StatisticsAll", StatisticsAll);
        }
        // 设置缓存过期时间，避免缓存雪崩
        redisTemplate.opsForValue().set("StatisticsAll", StatisticsAll, Duration.ofMinutes(5));
        return getStatisticsAllMapper.getStatisticsAll();
    }

    public List<GetStatistics> getStatisticsRooms() {
        return getStatisticsAllMapper.getStatisticsRooms();
    }


    /*
     * 今日数据
     * 今日订单数、今日营收、今日新增用户数。
     * */
    //    今日订单数
    public int getTodayOrderCount() {
        Integer todayOrderCount = getStatisticsAllMapper.countTodayOrders();
        return todayOrderCount == null ? 0 : todayOrderCount;
    }

    //    今日营收
    public int getTodayRevenue() {
        Integer todayRevenue = getStatisticsAllMapper.sumTodayRevenue();
        return todayRevenue == null ? 0 : todayRevenue;
    }

    //    今日新增用户
    public Object getTodayNewUsers() {
        Integer todayNewUsers = getStatisticsAllMapper.countTodayNewUsers();
        return todayNewUsers == null ? 0 : todayNewUsers;
    }

    public Object getTotalFosterOrders() {
        Integer totalFosterOrders = getStatisticsAllMapper.countFosterOrders();
        return totalFosterOrders == null ? 0 : totalFosterOrders;
    }

    public Object getTotalMedicalOrders() {
        Integer totalMedicalOrders = getStatisticsAllMapper.countMedicalOrders();
        return totalMedicalOrders == null ? 0 : totalMedicalOrders;
    }

    /*
     * 环比数据：与昨日相比的增长率/下降率。
     * (今日数据 - 昨日数据) / 昨日数据 * 100%
     * */
//   昨日订单数据
    public Object getYesterdayOrderCount() {
        Integer yesterdayOrderCount = getStatisticsAllMapper.countYesterdayOrders();

        return yesterdayOrderCount == null ? 0 : yesterdayOrderCount;
    }

    //昨日收入数据
    public Object getYesterdayRevenue() {
        Integer yesterdayRevenue = getStatisticsAllMapper.countYesterdayRevenue();
        return yesterdayRevenue == null ? 0 : yesterdayRevenue;
    }

    //昨日用户数据
    public Object getYesterdayNewUsers() {
        Integer yesterdayNewUsers = getStatisticsAllMapper.countYesterdayNewUsers();
        return yesterdayNewUsers == null ? 0 : yesterdayNewUsers;
    }

    //  订单增长率
    public BigDecimal calculateOrderGrowthRate() {
        try {
            int todayOrders = getStatisticsAllMapper.countTodayOrders();
            int yesterdayOrders = getStatisticsAllMapper.countYesterdayOrders();

            if (yesterdayOrders == 0) {
                return BigDecimal.ZERO; // 避免除以零
            }
            return BigDecimal.valueOf(todayOrders - yesterdayOrders)
                    .divide(BigDecimal.valueOf(yesterdayOrders), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } catch (Exception e) {
            log.error("计算订单增长率失败", e);
            return BigDecimal.ZERO;
        }
    }

    //     营收增长率(今日与昨日对比)
    public BigDecimal calculateRevenueGrowthRate() {
        try {
            int TodayRevenues = getStatisticsAllMapper.sumTodayRevenue();
            int yesterdayRevenues = getStatisticsAllMapper.countYesterdayRevenue();

            if (yesterdayRevenues == 0) {
                return BigDecimal.ZERO; // 避免除以零
            }
            return BigDecimal.valueOf(TodayRevenues - yesterdayRevenues)
                    .divide(BigDecimal.valueOf(yesterdayRevenues), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } catch (Exception e) {
            log.error("计算营收增长率失败", e);
            return BigDecimal.ZERO;
        }
    }

    //      用户增长率(今日与昨日对比)
    public BigDecimal calculateUserGrowthRate() {
        try {
            int TodayNewUsers = getStatisticsAllMapper.countTodayNewUsers();
            int yesterdayNewUsers = getStatisticsAllMapper.countYesterdayNewUsers();

            if (yesterdayNewUsers == 0) {
                return BigDecimal.ZERO; // 避免除以零
            }
            return BigDecimal.valueOf(TodayNewUsers - yesterdayNewUsers)
                    .divide(BigDecimal.valueOf(yesterdayNewUsers), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        } catch (Exception e) {
            log.error("计算用户增长率失败", e);
            return BigDecimal.ZERO;
        }
    }


    /*
     * 累计数据
     * 总订单数、总营收、注册用户总数、宠物总数。
     * */
    //    统计订单数
    public int getTotalOrderCount() {
        return getStatisticsAllMapper.countOrders();
    }

    //    统计营收
    public int getTotalRevenue() {
        return getStatisticsAllMapper.sumRevenue();
    }

    //    统计用户数
    public int getTotalUsers() {
        return getStatisticsAllMapper.countUsers();
    }

    //    统计宠物数
    public int getTotalPets() {
        return getStatisticsAllMapper.countPets();
    }


    //    订单状态占比
    public List<OrderStatusDTO> getOrderStatus() {
        return getStatisticsAllMapper.getOrderStatus();
    }

    //    热门服务
    public List<ServiceItemHotDTO> getHotServiceItem(int i) {
        List<ServiceItemHotDTO> serviceItems = getStatisticsAllMapper.getHotServiceItem(i);
        for (ServiceItemHotDTO serviceItem : serviceItems) {
            if (serviceItem.getServiceName() == null) {
                serviceItem.setServiceName("服务不存在或以下架");
            }
        }


        return serviceItems;
    }

    /*
     * 动态查询 订单每日交易金额趋势。
     *  status{pending，confirmed，processing，completed，canceled}
     * */
    public List<OrderAmountDTO> getOrderAmount(int days, String status) {
        return getStatisticsAllMapper.getOrderAmount(days, status);
    }


}

