package com.softlab.common.model.vo;

import com.softlab.common.model.User;
import lombok.Data;

/**
 * Created by LiXiwen on 2019/3/25.
 **/
public class DecryptVo extends User {

    /**
     * 微信步数的加密数据
     */
    private String encryptedData;
    /**
     * 微信用户的iv
     */
    private String iv;
    /**
     * onlogin接口返回的sessionKey
     */
    private String sessionKey;

    private Integer days;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
