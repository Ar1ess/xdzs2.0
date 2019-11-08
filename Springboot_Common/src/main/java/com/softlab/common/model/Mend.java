package com.softlab.common.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:11
 */
@Data
public class Mend {

    private Integer areaId;

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
     * 报修位置
     */
    private String location;

    /**
     * 报修内容
     */
    private String context;

    /**
     * 报修时间
     */
    private Timestamp time;



}
