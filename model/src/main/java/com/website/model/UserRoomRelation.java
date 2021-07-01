package com.website.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 用户聊天室关系表
 * @Author psq
 * @Date 2021/7/1/15:43
 */
@Table(name = "user_room_relation")
public class UserRoomRelation extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 聊天室id
     */
    @Column(name = "room_id")
    private Long roomId;

    /**
     * 用户进入聊天室的时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
