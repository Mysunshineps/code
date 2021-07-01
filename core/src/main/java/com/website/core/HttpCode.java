package com.website.core;

/**
 * @Description 状态码
 * @Author psq
 * @Date 2021/6/1/15:22
 */
public interface HttpCode {

    String SUCCESS = "200";

    String ERROR = "400";

    /**
     * 业务异常
     */
    String BUSINESS_ERROR = "509";


    /**
     * 未获取到用户信息
     */
    String NO_USER_INFO = "505";

     int NO_FOUND = 404;

    /**
     * 没有对应权限
     */
    String NO_AUTH = "506";

    /**
     * token失效状态码
     */
    String TOKEN_INVALID = "50014";
}
