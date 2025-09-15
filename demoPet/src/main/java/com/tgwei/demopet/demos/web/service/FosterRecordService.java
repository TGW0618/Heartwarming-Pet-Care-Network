package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.common.Result;
import com.tgwei.demopet.demos.web.entity.FosterRecord;
import com.tgwei.demopet.demos.web.entity.FosterRecordVO;
import com.tgwei.demopet.demos.web.mapper.FosterRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FosterRecordService {

    @Resource
    private FosterRecordMapper fosterRecordMapper;


    public Result addFosterRecord(FosterRecord fosterRecord) {

        fosterRecord.setCreateTime(new Date());
        fosterRecord.setUpdateTime(new Date());
        int result = fosterRecordMapper.addFosterRecord(fosterRecord);
        return Result.success(result);


    }

    public Result getFosterRecordByOrderId(Integer orderId) {

        List<FosterRecordVO> fosterRecord = fosterRecordMapper.getFosterRecordByOrderId(orderId);
        return Result.success(fosterRecord);
    }
}
