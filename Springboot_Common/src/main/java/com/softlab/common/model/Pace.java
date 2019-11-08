package com.softlab.common.model;

import lombok.Data;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:15
 */
@Data
public class Pace {
    /**
     * 系统ID
     */
    private Integer systemId;

    /**
     * 用户唯一ID
     */
    private String openId;

    /**
     * 段位名称
     */
    private String paiwei;

    /**
     * 步数
     */
    private Integer pace;

    /**
     * 该段位对应图标
     */
    private String img;

    /**
     * 该段位对应颜色
     */
    private String color;

}
