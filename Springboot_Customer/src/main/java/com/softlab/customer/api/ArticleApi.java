package com.softlab.customer.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.softlab.common.RestData;
import com.softlab.common.model.Article;
import com.softlab.common.model.Comment;
import com.softlab.common.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
public class ArticleApi {

    private static final Logger logger = LoggerFactory.getLogger(ArticleApi.class);

    @Reference
    @Resource
    private ArticleService articleService;


    /**
     * 所有文章展示
     *
     * @return RestData
     */
    @RequestMapping(value = "/community", method = RequestMethod.GET)
    public RestData getAllArticle() {
        logger.info("getAllArticle : ");

        List<Object> list = articleService.getAllArticle();
        return new RestData(list);
    }


    /**
     * 一个问题的信息，内容，评论
     * @param systemId
     * @return RestData
     */
    @RequestMapping(value = "/detail/{systemId}", method = RequestMethod.GET)
    public RestData getArticleDetail(@PathVariable(value = "systemId") Integer systemId) {
        logger.info("getArticleDetail ： id = " + systemId);

        Map<String, Object> map = articleService.getArticleDetail(systemId);
        List<Map<String, Object>> al = articleService.getCommentByArtcileId(systemId);
        al.add(0, map);

        return new RestData(al);
    }


    /**
     * 通过关键字搜索文章
     * @param keyword
     * @return RestData
     */
    @RequestMapping(value = "/community/search", method = RequestMethod.GET)
    public RestData getCommunityByKeyword(@RequestParam(value = "keyword") String keyword) {
        logger.info("getCommunityByKeyword ： keyword = " + keyword);

        List<Map<String, Object>> al = articleService.getArticleByKeyword(keyword);

        return new RestData(al);
    }


    /**
     * 发帖
     * @param file
     * @param title
     * @param oppidA
     * @param content
     * @param category
     * @return RestData
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public RestData insertArticle(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "title") String title, @RequestParam(value = "oppidA") String oppidA, @RequestParam("content") String content, @RequestParam(value = "category") String category) {
        logger.info("insert article: " + oppidA + " , " + title + " , " + category + "");

        Article article = new Article();
        article.setCategory(category);
        article.setContent(content);
        article.setTitle(title);

        String msg = "";
        if (null != file) {
            msg = articleService.qiniuUpload(file);
        }

        article.setPic(msg);

        int flag = articleService.insertArticle(article);

        return new RestData(flag);
    }


    /**
     * 发评论
     * @param comment
     * @return RestData
     */
    @RequestMapping(value = "/detail/comment", method = RequestMethod.POST)
    public RestData insertComment(@RequestBody Comment comment) {
        logger.info(" insertComment: " + comment.getCContent());

        int flag = articleService.insertComment(comment);

        return new RestData(flag);
    }


    /**
     * 查询某一用户所有文章
     * @param oppid
     * @return RestData
     */
    @RequestMapping(value = "/my/{oppid}", method = RequestMethod.GET)
    public RestData selectMyCommunity(@PathVariable(value = "oppid") String oppid) {
        logger.info("selectMyCommunity : openId = " + oppid);

        List<Map<String, Object>> data = articleService.getArticleByOpenId(oppid);

        return new RestData(data);
    }


    /**
     * 删除帖子
     * @param systemId
     * @return RestData
     */
    @RequestMapping(value = "/my/delete/{systemId}", method = RequestMethod.POST)
    public RestData deleteMyCommunity(@PathVariable(value = "systemId") Integer systemId) {
        logger.info("deleteMyCommunity : systemId = " + systemId);

        int flag = articleService.deleteArticle(systemId);

        return new RestData(flag);
    }


    /**
     * 获取审核文章列表
     * @return RestData
     */
    @RequestMapping(value = "/getPassCommunity", method = RequestMethod.GET)
    public RestData getNotPassArticle() {
        logger.info("getNotPassArticle : ");

        List<Map<String, Object>> data = articleService.getAllNotPassArticle();

        return new RestData(data);
    }


    /**
     * 审核文章通过
     * @param systemId
     * @return RestData
     */
    @RequestMapping(value = "/passCommunity", method = RequestMethod.GET)
    public RestData passArticle(@RequestParam(value = "id") Integer systemId) {
        logger.info("passArticle : systemId = " + systemId);

        int flag = articleService.updateArticlePass(systemId);

        return new RestData(flag);
    }


    /**
     * 审核评论通过
     * @param systemId
     * @return RestData
     */
    @RequestMapping(value = "/passComment", method = RequestMethod.GET)
    public RestData passComment(@RequestParam(value = "id") Integer systemId) {
        logger.info("passComment : systemId = " + systemId);

        int flag = articleService.updateCommentPass(systemId);

        return new RestData(flag);
    }


    /**
     * 删除评论
     * @param systemId
     * @return
     */
    @RequestMapping(value = "/delete/comment", method = RequestMethod.GET)
    public RestData deleteComment(@RequestParam(value = "id") Integer systemId) {
        logger.info("deleteComment : systemId = " + systemId);

        int flag = articleService.deleteComment(systemId);

        return new RestData(flag);
    }




















}
