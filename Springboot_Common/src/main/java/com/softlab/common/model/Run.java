package com.softlab.common.model;

import lombok.Data;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:18
 */
@Data
public class Run {
    /**
     * 系统ID
     */
    private Integer systemId;

    /**
     * 用户唯一ID
     */
    private String openId;

    /**
     * 每次跑步数据
     */
    private String runData;

    /**
     * 每次跑步时间
     */
    private String runTime;
}
