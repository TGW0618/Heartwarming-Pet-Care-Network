package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.GetStatistics;
import com.tgwei.demopet.demos.web.mapper.GetStatisticsAllMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetStatisticsAllService {

    @Autowired
    GetStatisticsAllMapper getStatisticsAllMapper;

    public List<GetStatistics> getStatisticsAll() {
        return getStatisticsAllMapper.getStatisticsAll();
    }

    public List<GetStatistics> getStatisticsRooms() {
        return getStatisticsAllMapper.getStatisticsRooms();
    }
}
