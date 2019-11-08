package com.softlab.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by LiXiwen on 2019/7/3 17:56.
 **/
public class RestData {
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public RestData(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RestData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestData(Object data) {
        this.code = 0;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
}
