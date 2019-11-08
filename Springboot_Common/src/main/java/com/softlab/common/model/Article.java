package com.softlab.common.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : Ar1es
 * @date : 2019/11/7
 * @since : Java 8
 */


@Data
public class Article {

    /**
     * 文章主键ID
     */
    private Integer articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * openid
     */
    private String openId;

    /**
     * 文章创建时间
     */
    private Timestamp time;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 浏览人数
     */
    private Integer viewNumber;

    /**
     * 点赞人数
     */
    private Integer likeNumber;

    /**
     * 评论人数
     */
    private Integer commentNumber;

    /**
     * 是否审核 0 -- 未审核 1 -- 审核通过
     */
    private Integer isPass;

    /**
     * 文章标签
     */
    private String category;
}
