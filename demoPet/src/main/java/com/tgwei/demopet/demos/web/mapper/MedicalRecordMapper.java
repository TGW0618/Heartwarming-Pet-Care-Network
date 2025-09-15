package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.MedicalRecord;
import com.tgwei.demopet.demos.web.entity.MedicalRecordVO;

import java.util.List;

public interface MedicalRecordMapper {


    int addMedicalRecord(MedicalRecord medicalRecord);

    List<MedicalRecordVO> getMedicalRecordByOrderId(Integer orderId);
}
