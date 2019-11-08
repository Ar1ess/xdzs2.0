package com.softlab.common.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:19
 */
@Data
public class Time {
    /**
     * 系统Id
     */
    private Integer tId;

    /**
     * 用户唯一ID
     */
    private String openId;

    /**
     * 用户投票时间
     */
    private Timestamp time;
}
