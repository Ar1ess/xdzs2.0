package com.softlab.provider.mapper;

import com.softlab.common.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Ar1es
 * @date : 2019/11/9
 * @since : Java 8
 */
@Mapper
@Repository
public interface ManageMapper {


    /**
     * 审核文章列表
     * @return List<ArticleVo>
     */
    List<ArticleVo> selectAllNotPassArticle();


    /**
     * 审核文章
     * @param id
     * @return 0 -- 修改失败 1 -- 修改成功
     */
    int updatePassArticle(Integer id);


    /**
     * 审核评论
     * @param id
     * @return 0 -- 修改失败 1 -- 修改成功
     */
    int updatePassComment(Integer id);

}
