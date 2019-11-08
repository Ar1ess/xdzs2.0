package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.softlab.common.model.Article;
import com.softlab.common.model.Comment;
import com.softlab.common.model.vo.ArticleVo;
import com.softlab.common.service.ArticleService;
import com.softlab.provider.mapper.ArticleMapper;
import com.softlab.provider.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<ArticleVo> getAllArticle() {
        return null;
    }

    @Override
    public Map<String, Object> getArticleDetail(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getArticleByOpenId(String openId) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getArticleByKeyword(String keyword) {
        return null;
    }

    @Override
    public int insertArticle(Article article) {
        return 0;
    }

    @Override
    public int insertComment(Comment comment) {
        return 0;
    }

    @Override
    public int deleteArticle(Integer id) {
        return 0;
    }

    @Override
    public int getArticleCount(String openId) {
        return 0;
    }
}
