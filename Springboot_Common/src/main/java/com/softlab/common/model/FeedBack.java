package com.softlab.common.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author LiXiwen
 * @date 2019/11/8 10:51
 */
@Data
public class FeedBack {
    /**
     * 系统ID
     */
    private Integer fId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学号
     */
    private String number;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 专业
     */
    private String collageMajor;

    /**
     * 反馈信息
     */
    private String detail;

    /**
     * 时间
     */
    private Timestamp time;

}
