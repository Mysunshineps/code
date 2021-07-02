package com.website.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description 聊天信息
 * @Author psq
 * @Date 2021/7/2/11:30
 */
@Table(name = "message")
public class Message extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}