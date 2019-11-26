package com.softlab.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Ar1es
 * @date : 2019/11/7
 * @since : Java 8
 */
@Data
public class User implements Serializable {

    /**
     * 主键ID
     */
    private Integer systemId;

    /**
     * openid
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 用户步数
     */
    private Integer userPace;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Integer getUserPace() {
        return userPace;
    }

    public void setUserPace(Integer userPace) {
        this.userPace = userPace;
    }
}
