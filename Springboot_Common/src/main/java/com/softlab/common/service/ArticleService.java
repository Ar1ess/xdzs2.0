package com.softlab.common.service;

import com.softlab.common.model.Article;
import com.softlab.common.model.Comment;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */
public interface ArticleService {


    /**
     * 所有通过审核文章列表
     * 存入redis
     * @return List<Object>
     */
    List<Object> getAllArticle();


    /**
     * 文章详细内容
     * @param id
     * @return Map<String, Object>
     */
    Map<String, Object> getArticleDetail(Integer id);


    /**
     * 每人文章列表
     * @param openId
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getArticleByOpenId(String openId);


    /**
     * 根据关键字查询文章列表
     * @param keyword
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getArticleByKeyword(String keyword);


    /**
     * 根据文章ID查询所有评论
     * @param id
     * @return List<Map<String, Object>>
     */

    List<Map<String, Object>> getCommentByArtcileId(Integer id);


    /**
     * 查询所有已审核文章总数
     * @return
     */
    int getArticleCount();

    /**
     * 添加文章
     * @param article
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int insertArticle(Article article);


    /**
     * 添加评论
     * @param comment
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int insertComment(Comment comment);


    /**
     * 删除文章
     * 删除redis
     * @param id
     * @return 0 -- 删除失败 1 -- 删除成功
     */
    int deleteArticle(Integer id);


    /**
     * 获取每人文章总数
     * redis
     * @param openId
     * @return int
     */
    int getArticleCountByOpenId(String openId);


    /**
     * 获取文章审核列表
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getAllNotPassArticle();


    /**
     * 审核文章
     * 删除redis
     * @param id
     * @return 0 -- 修改失败 1 -- 修改成功
     */
    int updateArticlePass(Integer id);


    /**
     * 审核评论
     * @param id
     * @return 0 -- 修改失败 1 -- 修改成功
     */
    int updateCommentPass(Integer id);


    /**
     * 删除评论
     * @param id
     * @return 0 -- 删除失败 1 -- 删除成功
     */
    int deleteComment(Integer id);


    /**
     * 上传文件
     * @param file
     * @return String
     */
    String qiniuUpload(MultipartFile file);



}
