package com.softlab.common.model.vo;

import com.softlab.common.model.Article;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Data
public class ArticleVo extends Article implements Serializable {

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
