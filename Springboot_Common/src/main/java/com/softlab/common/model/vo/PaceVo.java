package com.softlab.common.model.vo;

import com.softlab.common.model.Pace;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LiXiwen
 * @date 2019/11/8 14:28
 */
@Data
public class PaceVo extends Pace{

    private Integer id;

    private String name;

    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaceVo(Integer id) {
        this.id = id;
    }
}
