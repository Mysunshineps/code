package com.website.service.room;

import com.website.model.Room;
import com.website.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author psq
 * @Date 2021/7/1/15:26
 */
public interface RoomService extends IService<Room> {

    /**
     * 根据房间id查询该房间的用户信息
     * @param roomId
     * @return
     */
    List<Map<String,Object>> selectUsersByRoomId(String roomId);
}
