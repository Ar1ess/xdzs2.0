package com.softlab.common.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : Ar1es
 * @date : 2019/11/7
 * @since : Java 8
 */

@Data
public class Comment {

    /**
     * 评论主键ID
     */
    private Integer commentId;

    /**
     * 评论文章ID
     */
    private Integer cArticleId;

    /**
     * openid
     */
    private String openId;

    /**
     * 评论发布时间
     */
    private Timestamp cTime;

    /**
     * 评论内容
     */
    private String cContent;

    /**
     * 评论审核 0 -- 未审核 1 -- 审核通过
     */
    private Integer cIsPass;

    /**
     * 评论点赞人数
     */
    private Integer cLikeNumber;


}
