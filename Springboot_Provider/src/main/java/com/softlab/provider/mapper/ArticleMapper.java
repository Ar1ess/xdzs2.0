package com.softlab.provider.mapper;

import com.softlab.common.model.Article;
import com.softlab.common.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Mapper
@Repository
public interface ArticleMapper {


    /**
     * 查询全部文章
     * @return List<ArticleVo>
     */
    List<ArticleVo> selectAllArticle();


    /**
     * 文章详细
     * @param id
     * @return List<ArticleVo>
     */
    List<ArticleVo> selectArticleDetail(Integer id);


    /**
     * 根据关键字查询相关文章
     * @return List<ArticleVo>
     */
    List<ArticleVo> selectArticleByKeyword(String keyword);


    /**
     * 查找某用户所有文章
     * @param openId
     * @return List<ArticleVo>
     */
    List<ArticleVo> selectArticleByOpenId(String openId);


    /**
     * 增加文章
     * @param article
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int insertArticle(Article article);


    /**
     * 删除文章
     * @param id
     * @return 0 -- 删除失败 1 -- 删除成功
     */
    int deleteArticle(Integer id);


    /**
     * 查找某用户文章总数
     * @param openId
     * @return Integer
     */
    @Select("select count(*) from article where open_id = #{arg0}")
    Integer selectArticleCountByOpenId(String openId);
}
