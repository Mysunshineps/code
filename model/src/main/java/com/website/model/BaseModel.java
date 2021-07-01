package com.website.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 *
 * @author psq
 */
public class BaseModel implements Serializable {

    private static final long  serialVersionUID =1093391207572875136L;

    /**
     * 重写toString 方法，返回JSON串
     * @return
     */
    @Override
    public String toString() {
        return this == null ? "{}" : JSON.toJSONString(this);
    }
}
