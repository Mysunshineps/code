package com.website.admin.controller.user;

import com.alibaba.fastjson.JSON;
import com.website.admin.controller.web.BaseCRUDController;
import com.website.core.AjaxResponse;
import com.website.core.LoginRequest;
import com.website.model.User;
import com.website.service.sys.SysUserTokenService;
import com.website.service.user.UserService;
import com.website.admin.utils.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:    swagger
 * @Author:         psq
 * @CreateDate:     2021/6/4 10:26
 * @Version:        1.0
 */
@RestController
@RequestMapping("api/login")
@Api(value = "用户登录", tags = "用户登录", description = "用户登录")
public class LoginController extends BaseCRUDController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录", response = AjaxResponse.class)
    @PostMapping
    public AjaxResponse login(@ApiParam(value = "account", name = "会员卡号", required = true) @RequestParam String account,
                              @ApiParam(value = "password", name = "会员卡号", required = true) @RequestParam String password){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse res = new AjaxResponse();
        User se = new User();
        se.setAccount(account);
        se.setPassword(password);
        se.setStatus(1);
        se.setType(1);
        User user = userService.selectOne(se);
        if(user == null){
            return  res.gengerateMsgError("账号或密码不正确");
        }
        String token = sysUserTokenService.createToken(user);
        res.success(token);
        return res;
    }

    /**
     * 接口调用认证
     * @param login
     * @return
     */
    @PostMapping("auth")
    public AjaxResponse loginAuth(LoginRequest login){
        AjaxResponse res = new AjaxResponse();
        User se = new User();
        se.setAccount(login.getAccount());
        se.setPassword(login.getPassword());
        se.setStatus(1);
        se.setType(2);
        User user = userService.selectOne(se);
        if(user == null){
            return  res.gengerateMsgError("账号或密码不正确");
        }
        String token = JWTUtils.geneToken(user);
        res.success(token);
        return res;
    }
}
