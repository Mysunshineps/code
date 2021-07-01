package com.website.core;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class AjaxResponse implements Serializable{

    private String code = HttpCode.SUCCESS;

    private String message;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if(code.equals(HttpCode.ERROR) && StringUtils.isNotBlank(this.getMessage())){
            this.setMessage("系统错误");
        }
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AjaxResponse gengerateMsgError(String msg) {
        this.code = HttpCode.ERROR;
        this.message = msg;
        return this;
    }

    public AjaxResponse success(Object data) {
        this.code = HttpCode.SUCCESS;
        this.data = data;
        return this;
    }

    public boolean isOK() {
        if(HttpCode.SUCCESS.equals(code)){
            return true;
        }
        return false;
    }

    public static AjaxResponse error(String message,String code){
        AjaxResponse response = new AjaxResponse();
        response.setMessage(message);
        response.setCode(code);
        return response;
    }

    public static AjaxResponse error(String message){
        return error(message,HttpCode.ERROR);
    }

    /**
     * token失效错误提示
     */
    public static AjaxResponse tokenInvalidError(String message){
        return error(message,HttpCode.TOKEN_INVALID);
    }

    public static AjaxResponse successData(Object data,String message){
        AjaxResponse response = new AjaxResponse();
        response.setCode(HttpCode.SUCCESS);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static AjaxResponse successData(Object data){
        AjaxResponse response = new AjaxResponse();
        response.setCode(HttpCode.SUCCESS);
        response.setData(data);
        return response;
    }
}
