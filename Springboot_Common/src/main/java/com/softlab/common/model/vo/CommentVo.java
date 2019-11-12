package com.softlab.common.model.vo;

import com.softlab.common.model.Comment;
import lombok.Data;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Data
public class CommentVo extends Comment {

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 排位标识
     */
    private String paiwei;

    /**
     * 排位图片颜色
     */
    private String img;

    /**
     * 作者
     */
    private String userName;
}
