package com.website.admin.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:    用户控制层
 * @Author:         psq
 * @CreateDate:     2021/6/4 10:26
 * @Version:        1.0
 */
@RestController
@RequestMapping("/admin/user/")
@Api(value = "用户模块", tags = "用户模块", description = "接口")
public class UserController extends BaseCRUDController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 用户注册
     * @param username 用户账号
     * @param password 用户密码
     * @param firstName 姓
     * @param lastName 名
     * @param email 邮箱
     * @param phone 电话号码
     * @return
     */
    @ApiOperation(value = "用户注册", httpMethod = "POST", notes = "用户注册", response = AjaxResponse.class)
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public AjaxResponse login(@ApiParam(value = "username", name = "用户账号", required = true) @RequestParam String username,
                              @ApiParam(value = "password", name = "用户密码", required = true) @RequestParam String password,
                              @ApiParam(value = "firstName", name = "姓", required = false) @RequestParam(required = false) String firstName,
                              @ApiParam(value = "lastName", name = "名", required = false) @RequestParam(required = false) String lastName,
                              @ApiParam(value = "email", name = "邮箱", required = false) @RequestParam(required = false) String email,
                              @ApiParam(value = "phone", name = "电话号码", required = false) @RequestParam(required = false) String phone
                              ){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse response = new AjaxResponse();
        User user = new User();
        user.setUserName(username);
        List<User> dbUserList = userService.select(user);
        if (null != dbUserList && !dbUserList.isEmpty()){
            return response.gengerateMsgError("该用户名已使用，请重新输入!");
        }
        user.setPassword(password);
        if (StringUtils.isNotBlank(firstName)){
            user.setFirstName(firstName);
        }
        if (StringUtils.isNotBlank(lastName)){
            user.setLastName(lastName);
        }
        if (StringUtils.isNotBlank(email)){
            user.setEmail(email);
        }
        if (StringUtils.isNotBlank(phone)){
            user.setPhone(phone);
        }
        user.setStatus(1);
        user.setCreateTime(new Date());
        userService.insertSelective(user);
        return response;
    }

    /**
     * 用户登录
     * @param username 用户账号
     * @param password 用户密码
     * @return
     */
    @ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录", response = AjaxResponse.class)
    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    public AjaxResponse login(@ApiParam(value = "username", name = "用户账号", required = true) @RequestParam String username,
                              @ApiParam(value = "password", name = "用户密码", required = true) @RequestParam String password){
        AjaxResponse res = new AjaxResponse();
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setStatus(1);
        User dbUser = userService.selectOne(user);
        if(dbUser == null){
            return res.gengerateMsgError("账号或密码不正确");
        }
        try {
            String token = sysUserTokenService.createToken(dbUser);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", dbUser.getUserName());
            jsonObject.put("token", token);
            res.success(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            res.gengerateMsgError(e.getMessage());
        }
        return res;
    }

    /**
     * 接口调用认证
     * @param login
     * @return
     */
    @ApiOperation(value = "用户授权", httpMethod = "POST", notes = "用户授权", response = AjaxResponse.class)
    @RequestMapping(value = "userAuth", method = RequestMethod.POST)
    public AjaxResponse userAuth(LoginRequest login){
        AjaxResponse res = new AjaxResponse();
        User se = new User();
        se.setUserName(login.getUserName());
        se.setPassword(login.getPassword());
        se.setStatus(1);
        User user = userService.selectOne(se);
        if(user == null){
            return  res.gengerateMsgError("账号或密码不正确");
        }
        String token = JWTUtils.geneToken(user);
        res.success(token);
        return res;
    }

    /**
     * 获取用户信息
     * @param userName 用户名
     * @return
     */
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", notes = "获取用户信息", response = AjaxResponse.class)
    @RequestMapping(value = "info/{userName}", method = RequestMethod.GET)
    public AjaxResponse login(HttpServletRequest request, @ApiParam(value = "userName", name = "用户名", required = false) @PathVariable(required = false) String userName){
        Map<String,Object> params = getParams();
        System.out.println(JSON.toJSONString(params));
        AjaxResponse res = new AjaxResponse();
        if (StringUtils.isBlank(userName)){
            Object userId = request.getAttribute("userId");
            User user = userService.selectByKey(userId);
            if (null == user || StringUtils.isBlank(user.getUserName())){
                return res.gengerateMsgError("参数异常!");
            }
            userName = user.getUserName();
        }
        User user = new User();
        user.setUserName(userName);
        user.setStatus(1);
        User dbUser = userService.selectOne(user);
        if(dbUser == null){
            return res.gengerateMsgError("未查到该用户信息!");
        }
        dbUser.setPassword(null);
        res.success(dbUser);
        return res;
    }

}
