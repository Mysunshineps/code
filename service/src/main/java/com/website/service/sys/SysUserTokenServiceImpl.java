package com.website.service.sys;

import com.website.mapper.SysUserTokenMapper;
import com.website.model.SysUserToken;
import com.website.model.User;
import com.website.service.BaseService;
import com.website.service.user.UserService;
import com.website.service.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author psq
 * @Date 2021/6/18/17:10
 */
@Service
public class SysUserTokenServiceImpl extends BaseService<SysUserToken> implements SysUserTokenService {

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Autowired
    private UserService userService;
    /**
     * 检验该token是否失效
     * @param token
     * @return
     */
    @Override
    public boolean checkTokenValid(String token) {
        SysUserToken temp = new SysUserToken();
        temp.setToken(token);
        SysUserToken userToken = this.selectOne(temp);
        //token失效
        if(null == userToken || null == userToken.getExpireTime() || userToken.getExpireTime().getTime() < System.currentTimeMillis()){
            System.out.println("该token："+ token +"失效！");
            return false;
        }

        //查询用户信息
        User userTemp = new User();
        userTemp.setId(userToken.getUserId());
        User user = userService.selectOne(userTemp);
        //账号不存在或被冻结
        if(null == user || null == user.getStatus() ||user.getStatus() == 2){
            System.out.println("该token："+ token +"对应的账号费正常状态！");
            return false;
        }
        return true;
    }

    @Override
    public String createToken(User user) {
        //18个小时过期
        final long EXPIRE = 60000*60*18;
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE);
        //生成一个token
        String token = JWTUtils.geneToken(user);

        //判断是否生成过token
        SysUserToken sysUserToken = this.selectByKey(user.getId());
        if(sysUserToken == null){
            sysUserToken = new SysUserToken();
            sysUserToken.setUserId(user.getId());
            sysUserToken.setToken(token);
            sysUserToken.setUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);

            //保存token
            this.insertSelective(sysUserToken);
        }else{
            sysUserToken.setToken(token);
            sysUserToken.setUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);

            //更新token
            this.updateByPrimaryKeySelective(sysUserToken);
        }
        return token;
    }
}
