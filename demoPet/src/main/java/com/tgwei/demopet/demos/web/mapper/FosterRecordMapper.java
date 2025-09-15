package com.tgwei.demopet.demos.web.mapper;


import com.tgwei.demopet.demos.web.entity.FosterRecord;
import com.tgwei.demopet.demos.web.entity.FosterRecordVO;

import java.util.List;


public interface FosterRecordMapper {


    int addFosterRecord(FosterRecord fosterRecord);

    List<FosterRecordVO>  getFosterRecordByOrderId(Integer orderId);
}
