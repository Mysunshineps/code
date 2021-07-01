package com.website.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 聊天室实体类
 * @Author psq
 * @Date 2021/7/1/15:22
 */
@Table(name = "room")
public class Room extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 聊天室名
     */
    private String name;

    /**
     * 用户创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
