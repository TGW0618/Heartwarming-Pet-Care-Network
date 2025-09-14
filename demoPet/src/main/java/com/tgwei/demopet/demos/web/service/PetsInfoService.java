package com.tgwei.demopet.demos.web.service;

import com.tgwei.demopet.demos.web.entity.PetsInfo;
import com.tgwei.demopet.demos.web.mapper.PetsInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PetsInfoService {

    @Resource
    private PetsInfoMapper petsInfoMapper;


    public List<PetsInfo> getAllPetsInfo(Long ownerId) {
        return petsInfoMapper.getAllPetsInfo(ownerId);
    }

    public PetsInfo getPetsInfoById(Integer id) {
        return petsInfoMapper.getPetsInfoById(id);
    }

    public boolean deletePetsInfoById(Integer id) {
        return petsInfoMapper.deletePetsInfoById(id) > 0;
    }

    public boolean updatePetsInfoById(PetsInfo petsInfo) {
        return petsInfoMapper.updatePetsInfoById(petsInfo) > 0;
    }

    public boolean addPetsInfo(PetsInfo petsInfo) {
        return petsInfoMapper.addPetsInfo(petsInfo) > 0;
    }
}
