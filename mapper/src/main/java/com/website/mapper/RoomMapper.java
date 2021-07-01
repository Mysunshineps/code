package com.website.mapper;

import com.website.core.BaseMapper;
import com.website.model.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author psq
 * @Date 2021/7/1/15:29
 */
@Repository
public interface RoomMapper extends BaseMapper<Room> {

    List<Map<String,Object>> selectUsersByRoomId(String roomId);

}
