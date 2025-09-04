package com.tgwei.demopet.demos.web.controller;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.GetStatistics;
import com.tgwei.demopet.demos.web.service.GetStatisticsAllService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/getStatistics")
public class GetStatisticsController {

    @Resource
    private GetStatisticsAllService getStatisticsAllService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/getStatisticsAll")
    public Result getStatisticsAll() {
        List<GetStatistics> StatisticsAll = (List<GetStatistics>) redisTemplate.opsForValue().get("StatisticsAll");
        if (StatisticsAll == null) {
            StatisticsAll = getStatisticsAllService.getStatisticsAll();
            redisTemplate.opsForValue().set("StatisticsAll", StatisticsAll);
        }
        return Result.success(StatisticsAll);

    }

    @GetMapping("/getStatisticsRooms")
    public Result getStatisticsRooms() {
        List<GetStatistics> StatisticsRooms = getStatisticsAllService.getStatisticsRooms();

        return Result.success(StatisticsRooms);
    }
}
