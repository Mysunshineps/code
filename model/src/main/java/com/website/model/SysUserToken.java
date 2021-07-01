package com.website.model;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 系统用户token白名单
 * @Author psq
 * @Date 2021/7/1/15:22
 */
@Repository
@Table(name = "sys_user_token")
public class SysUserToken extends BaseModel{

    @Id
    @Column(name = "user_id")
    private Long userId;

    private String token;

    @Column(name = "expire_time")
    private Date expireTime;

    @Column(name = "update_time")
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
