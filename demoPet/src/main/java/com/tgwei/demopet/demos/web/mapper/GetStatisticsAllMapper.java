package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.GetStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GetStatisticsAllMapper {


    List<GetStatistics> getStatisticsAll();
}
