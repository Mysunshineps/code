package com.website.service.room;

import com.website.mapper.RoomMapper;
import com.website.model.Room;
import com.website.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author psq
 * @Date 2021/7/1/15:27
 */
@Service
public class RoomServiceImpl extends BaseService<Room> implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Map<String, Object>> selectUsersByRoomId(String roomId) {
        return roomMapper.selectUsersByRoomId(roomId);
    }
}
