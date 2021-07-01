package com.website.service.user;

import com.website.mapper.SysUserTokenMapper;
import com.website.model.User;
import com.website.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author psq
 * @Date 2021/6/18/16:59
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {
    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;
}
