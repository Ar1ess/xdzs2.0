package com.softlab.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiXiwen
 * @date 2019/11/8 11:21
 */
@Data
public class Version implements Serializable {
    /**
     * 系统id
     */
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 版本说明
     */
    private String data;
}
