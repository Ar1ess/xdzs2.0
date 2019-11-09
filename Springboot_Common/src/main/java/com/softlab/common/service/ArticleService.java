package com.softlab.common.service;

import com.softlab.common.model.Article;
import com.softlab.common.model.Comment;


import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */
public interface ArticleService {


    List<Object> getAllArticle();


    Map<String, Object> getArticleDetail(Integer id);


    List<Map<String, Object>> getArticleByOpenId(String openId);


    List<Map<String, Object>> getArticleByKeyword(String keyword);


    int insertArticle(Article article);


    int insertComment(Comment comment);


    int deleteArticle(Integer id);


    int getArticleCount(String openId);



}
