package com.website.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.website.core.AjaxResponse;
import com.website.service.sys.SysUserTokenService;
import com.website.admin.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserTokenService sysUserTokenService;

    private static final String LOGIN_ERR = "登录过期，请重新登录";
    /**
     * 进入controller 之前执行的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("X-Token");
            if(StringUtils.isBlank(token)){
                token = request.getParameter("X-Token");
            }
            if(StringUtils.isBlank(token)){
                sendRes(response, AjaxResponse.tokenInvalidError(LOGIN_ERR));
                return false;
            }
            Claims claims = JWTUtils.checkToken(token);
            if(null == claims){
                sendRes(response,AjaxResponse.tokenInvalidError(LOGIN_ERR));
                return false;
            }
            boolean flag = sysUserTokenService.checkTokenValid(token);
            if (!flag){
                sendRes(response,AjaxResponse.tokenInvalidError(LOGIN_ERR));
                return false;
            }
            request.setAttribute("userId",claims.get("userId"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sendRes(response,AjaxResponse.tokenInvalidError(LOGIN_ERR));
        }
        return false;
    }

    //返回错误信息
    public void sendRes(HttpServletResponse response, AjaxResponse res){
        try {
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(res));
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
