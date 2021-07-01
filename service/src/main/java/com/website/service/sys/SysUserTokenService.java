package com.website.service.sys;

import com.website.model.SysUserToken;
import com.website.model.User;
import com.website.service.IService;

/**
 * @Description
 * @Author psq
 * @Date 2021/6/18/17:09
 */
public interface SysUserTokenService extends IService<SysUserToken> {

    String createToken(User user);

    boolean checkTokenValid(String token);

}
