package com.tgwei.demopet.demos.web.mapper;

import com.tgwei.demopet.demos.web.entity.Rooms;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomsMapper {

    @Select("select * from rooms")
    List<Rooms> getAllRooms();

    List<Rooms> getRoomsStatistics();

    @Select("select * from rooms where status = 'available'")
    List<Rooms> getAvailableRooms();



    @Select("select * from rooms where rooms.room_id = #{id}")
    Rooms getRoomsById(Long id);

    @Delete("delete from rooms where rooms.room_id = #{id}")
    int deleteRooms(Long id);

//    修改房间信息
    int updateRooms(Rooms rooms);
}
