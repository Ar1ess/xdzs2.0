package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qiniu.api.auth.AuthException;
import com.softlab.common.ErrorMessage;
import com.softlab.common.GlobalConst;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Article;
import com.softlab.common.model.Comment;
import com.softlab.common.model.vo.ArticleVo;
import com.softlab.common.model.vo.CommentVo;
import com.softlab.common.service.ArticleService;
import com.softlab.common.util.DateUtil;
import com.softlab.common.util.ExecuteResult;
import com.softlab.provider.CommonUtil;
import com.softlab.provider.QiniuUtil;
import com.softlab.provider.mapper.ArticleMapper;
import com.softlab.provider.mapper.CommentMapper;
import com.softlab.provider.mapper.ManageMapper;
import com.softlab.provider.mapper.RedisMapper;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Service
@org.springframework.stereotype.Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final ManageMapper manageMapper;
    private final RedisMapper redisMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, CommentMapper commentMapper, ManageMapper manageMapper, RedisMapper redisMapper) {
        this.redisMapper = redisMapper;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
        this.manageMapper = manageMapper;
    }


    @Override
    public List<Object> getAllArticle() {
        //查询缓存是否命中
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
    public List<Map<String, Object>> getCommentByArtcileId(Integer id) {
        List<Map<String, Object>> array = new ArrayList<>();
        List<CommentVo> list = commentMapper.selectCommentByArticleId(id);
        if (null != list) {
            for (CommentVo comment1 : list) {
                Map<String, Object> map = new HashMap<>(8);
                map.put("writer", comment1.getUserName());
                map.put("content", comment1.getCContent());
                map.put("time", DateUtil.timestampToString(comment1.getCTime()));
                map.put("likesNumber", comment1.getCLikeNumber());
                map.put("userPaiwei", comment1.getPaiwei());
                map.put("userPaiweiImg", comment1.getImg());
                map.put("icon", comment1.getIcon());
                array.add(map);
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return array;
    }

    @Override
    public int insertArticle(Article article) {
        int flag = 0;
        if (null != article) {
            article.setTime(DateUtil.localTimeToTimestamp());
            article.setIsPass(0);
            article.setViewNumber(0);
            article.setCommentNumber(0);
            article.setLikeNumber(0);

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
        if (0 < flag) {
            if (redisMapper.hasKey(GlobalConst.ARTICLE_INTR)) {
                logger.info("删除文章id = " + id + "成功, 将删除缓存");
                redisMapper.del(GlobalConst.ARTICLE_INTR);
                logger.info("缓存删除成功");
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return flag;

    }

    @Override
    public int getArticleCount(String openId) {
        //查询缓存是否命中
        if (redisMapper.hasKey(GlobalConst.ARTICLE_COUNT + openId)) {
            return (int)(redisMapper.get(GlobalConst.ARTICLE_COUNT + openId));
        }
        int count = articleMapper.selectArticleCountByOpenId(openId);
        redisMapper.set(GlobalConst.ARTICLE_COUNT + openId, count);
        return count;
    }

    @Override
    public List<Map<String, Object>> getAllNotPassArticle() {
        List<Map<String, Object>> array = new ArrayList<>();
        List<ArticleVo> rtt = manageMapper.selectAllNotPassArticle();
        if (null != rtt) {
            for (ArticleVo art : rtt) {
                Map<String, Object> ma = new HashMap<>(8);
                ma.put("time", DateUtil.timestampToString(art.getTime()));
                if (null != art.getPaiwei()) {
                    ma.put("userPaiwei", art.getPaiwei());
                } else {
                    ma.put("userPaiwei", "");
                }
                ma.put("writer", art.getUserName());
                ma.put("id", art.getArticleId());
                if (null != art.getPic()) {
                    ma.put("pic", art.getPic());
                }
                ma.put("commentsNumber", art.getCommentNumber());
                ma.put("userPaiweiImg", art.getImg());
                ma.put("category", art.getCategory());
                ma.put("title", art.getTitle());
                ma.put("icon", art.getIcon());
                array.add(ma);
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return array;
    }

    @Override
    public int updateArticlePass(Integer id) {
        int flag = manageMapper.updatePassArticle(id);
        if (0 < flag) {
            if(redisMapper.hasKey(GlobalConst.ARTICLE_INTR)){
                logger.info("审核文章id = " + id + "成功, 将删除缓存");
                redisMapper.del(GlobalConst.ARTICLE_INTR);
                logger.info("缓存删除成功");
            }
        } else {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return flag;
    }

    @Override
    public int updateCommentPass(Integer id) {
        int flag = manageMapper.updatePassComment(id);
        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return flag;
    }

    @Override
    public int deleteComment(Integer id) {
        int flag = commentMapper.deleteComment(id);
        if (0 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }
        return flag;
    }

    @Override
    public String qiniuUpload(MultipartFile file) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        QiniuUtil qiniuUtil = new QiniuUtil();
        CommonUtil commonUtil = new CommonUtil();
        try {
            File file_up = commonUtil.multipartToFile(file);

            String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

            executeResult = qiniuUtil.uploadFile(file_up, filenameExtension);

            if (!executeResult.isSuccess()) {
                return "失败" + executeResult.getErrorMessages();
            }

        } catch (AuthException | JSONException e) {
            logger.error("AuthException", e);
        }
        return executeResult.getResult();
    }
}
