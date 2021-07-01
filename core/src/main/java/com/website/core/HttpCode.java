package com.website.core;

public interface HttpCode {

    String SUCCESS = "200";

    String ERROR = "500";

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
     * 活动结束，有已经支付的订单
     */
    String HAVA_ORDER = "507";

    /**
     * token失效状态码
     */
    String TOKEN_INVALID = "50014";
}
