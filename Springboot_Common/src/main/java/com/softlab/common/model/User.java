package com.softlab.common.model;

import lombok.Data;

/**
 * @author : Ar1es
 * @date : 2019/11/7
 * @since : Java 8
 */

@Data
public class User {

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
    private String userPace;
}
