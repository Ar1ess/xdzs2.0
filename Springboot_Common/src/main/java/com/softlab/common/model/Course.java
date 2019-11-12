package com.softlab.common.model;

import lombok.Data;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
@Data
public class Course {

    /**
     * 课程id
     */
    private String cId;

    /**
     * 课程名称
     */
    private String course;

    /**
     * 课程点赞数量
     */
    private Integer zan;
}
