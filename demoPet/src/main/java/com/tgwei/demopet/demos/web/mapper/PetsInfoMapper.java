package com.tgwei.demopet.demos.web.mapper;


import com.tgwei.demopet.demos.web.entity.PetsInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface PetsInfoMapper {


    List<PetsInfo> getAllPetsInfo(Long ownerId);

    @Select("select * from pet_info where id = #{id}")
    PetsInfo getPetsInfoById(Integer id);

    @Delete("delete from pet_info where id = #{id}")
    int deletePetsInfoById(Integer id);

    @Update("update pet_info set pet_name = #{petName}, " +
            "pet_type = #{petType}," +
            " breed = #{breed}," +
            " gender = #{gender}," +
            " birth_date = #{birthDate}," +
            " weight = #{weight}," +
            " health_status = #{healthStatus}," +
            " allergies = #{allergies}, " +
            " avatar = #{avatar}," +
            " update_time = #{updateTime} where id = #{id}")
    int updatePetsInfoById(PetsInfo petsInfo);

    @Insert(
            "insert into pet_info(owner_id, pet_name, pet_type, " +
                    "breed, gender, birth_date, weight, health_status, " +
                    "allergies, avatar, create_time, update_time) " +
                    "values(#{ownerId}, #{petName}, #{petType}," +
                    "#{breed}, #{gender}, #{birthDate}, #{weight}, " +
                    "#{healthStatus}, #{allergies}, #{avatar}, #{createTime}, " +
                    "#{updateTime})"
    )
    int addPetsInfo(PetsInfo petsInfo);
}
