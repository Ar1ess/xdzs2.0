package com.softlab.provider.mapper;

import com.softlab.common.model.Comment;
import com.softlab.common.model.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Ar1es
 * @date : 2019/11/8
 * @since : Java 8
 */

@Mapper
@Repository
public interface CommentMapper {

    /**
     * 添加评论
     * @param comment
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int insertComment(Comment comment);


    /**
     * 根据文章查找所有评论
     * @param id
     * @return List<CommentVo>
     */
    List<CommentVo> selectCommentByArticleId(Integer id);

}
