package com.softlab.common.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:19
 */
@Data
public class Time implements Serializable {
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
