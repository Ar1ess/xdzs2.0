package com.softlab.common.exception;

/**
 * @author LiXiwen
 * @date 2019/11/8 12:16
 */
public class AppException extends RuntimeException{

    private int code;
    private String message;

    public AppException() {
        super();
    }

    public AppException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
