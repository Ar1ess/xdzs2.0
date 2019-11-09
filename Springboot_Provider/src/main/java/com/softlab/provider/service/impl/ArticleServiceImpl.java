package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.softlab.common.ErrorMessage;
import com.softlab.common.GlobalConst;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Article;
import com.softlab.common.model.Comment;
import com.softlab.common.model.vo.ArticleVo;
import com.softlab.common.service.ArticleService;
import com.softlab.common.util.DateUtil;
import com.softlab.provider.mapper.ArticleMapper;
import com.softlab.provider.mapper.CommentMapper;
import com.softlab.provider.mapper.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Service
@org.springframework.stereotype.Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisMapper redisMapper;


    @Override
    public List<Object> getAllArticle() {
        if (redisMapper.hasKey(GlobalConst.ARTICLE_INTR)) {
            return redisMapper.lGet(GlobalConst.ARTICLE_INTR, 0, -1);
        }
        List<Object> al = new ArrayList<>();
        List<ArticleVo> rtv = articleMapper.selectAllArticle();
        if (null != rtv) {
            for (ArticleVo art : rtv) {
                Map<String, Object> map = new HashMap<>(8);
                map.put("time", DateUtil.timestampToString(art.getTime()));
                map.put("id", art.getArticleId());
                map.put("title", art.getTitle());
                if (null != art.getPic()) {
                    map.put("pic", art.getPic());
                }
                map.put("commentsNumber", art.getCommentNumber());
                map.put("icon", art.getIcon());

                map.put("writer", art.getUserName());
                if (null != art.getPaiwei()) {
                    map.put("userPaiwei", art.getPaiwei());
                } else {
                    map.put("userPaiwei", "");
                }
                map.put("userPaiweiImg", art.getImg());
                map.put("category", art.getCategory());
                al.add(map);
                redisMapper.lSet(GlobalConst.ARTICLE_INTR, map);
            }

        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return al;
    }

    @Override
    public Map<String, Object> getArticleDetail(Integer id) {
        List<ArticleVo> list = articleMapper.selectArticleDetail(id);
        Map<String, Object> map = new HashMap<>(8);
        if (null != list && 1 == list.size()) {
            ArticleVo art = list.get(0);
            map.put("systemId", art.getArticleId());
            map.put("commentNumber", art.getCommentNumber());
            map.put("title", art.getTitle());
            map.put("writer", art.getUserName());
            map.put("userPaiwei", art.getPaiwei());
            map.put("userPaiweiImg", art.getImg());
            map.put("icon", art.getIcon());
            map.put("time", DateUtil.timestampToString(art.getTime()));
            map.put("content", art.getContent());
            map.put("category", art.getCategory());
            if (null != art.getPic()) {
                map.put("pic", art.getPic());
            }
            map.put("viewsNumber", art.getViewNumber());
            map.put("likesNumber", art.getLikeNumber());
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getArticleByOpenId(String openId) {
        List<Map<String, Object>> al = new ArrayList<>();
        List<ArticleVo> rtv = articleMapper.selectArticleByOpenId(openId);
        if (null != rtv) {
            for (ArticleVo art : rtv) {
                Map<String, Object> map = new HashMap<>(8);
                map.put("writer", art.getUserName());
                map.put("id", art.getArticleId());
                map.put("title", art.getTitle());
                map.put("time", DateUtil.timestampToString(art.getTime()));
                map.put("icon", art.getIcon());
                if (null != art.getPic()) {
                    map.put("pic", art.getPic());
                }
                map.put("commentsNumber", art.getCommentNumber());
                map.put("userPaiweiImg", art.getImg());
                map.put("category", art.getCategory());
                if (null != art.getPaiwei()) {
                    map.put("userPaiwei", art.getPaiwei());
                } else {
                    map.put("userPaiwei", "");
                }
                al.add(map);
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return al;
    }

    @Override
    public List<Map<String, Object>> getArticleByKeyword(String keyword) {
        List<Map<String, Object>> arr = new ArrayList<>();
        List<ArticleVo> rt = articleMapper.selectArticleByKeyword(keyword);
        if (null != rt) {
            for (ArticleVo art : rt) {
                Map<String, Object> map = new HashMap<>(8);
                if (null != art.getPaiwei()) {
                    map.put("userPaiwei", art.getPaiwei());
                } else {
                    map.put("userPaiwei", "");
                }
                if (null != art.getPic()) {
                    map.put("pic", art.getPic());
                }
                map.put("commentsNumber", art.getCommentNumber());

                map.put("userPaiweiImg", art.getImg());
                map.put("category", art.getCategory());
                map.put("writer", art.getUserName());
                map.put("id", art.getArticleId());
                map.put("title", art.getTitle());
                map.put("time", DateUtil.timestampToString(art.getTime()));
                map.put("icon", art.getIcon());
                arr.add(map);
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return arr;
    }

    @Override
    public int insertArticle(Article article) {
        int flag = 0;
        if (null != article) {
            article.setTime(DateUtil.localTimeToTimestamp());
            article.setIsPass(0);
            article.setCommentNumber(0);
            article.setLikeNumber(0);
            article.setCommentNumber(0);

            int success = articleMapper.insertArticle(article);
            if (0 < success) {
                flag = 1;
            } else {
                throw new AppException(ErrorMessage.SYSTEM_ERROR);
            }
        }
        return flag;

    }

    @Override
    public int insertComment(Comment comment) {
        int flag = 0;
        if (null != comment) {
            comment.setCTime(DateUtil.localTimeToTimestamp());
            comment.setCIsPass(0);
            comment.setCLikeNumber(0);

            int success = commentMapper.insertComment(comment);
            if (0 < success) {
                flag = 1;
            } else {
                throw new AppException(ErrorMessage.SYSTEM_ERROR);
            }
        }
        return flag;
    }

    @Override
    public int deleteArticle(Integer id) {
        int flag = articleMapper.deleteArticle(id);
        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return flag;

    }

    @Override
    public int getArticleCount(String openId) {
        if (redisMapper.hasKey(GlobalConst.ARTICLE_COUNT + openId)) {
            return (int)(redisMapper.get(GlobalConst.ARTICLE_COUNT + openId));
        }
        int count = articleMapper.selectArticleCountByOpenId(openId);
        redisMapper.set(GlobalConst.ARTICLE_COUNT + openId, count);
        return count;
    }
}
