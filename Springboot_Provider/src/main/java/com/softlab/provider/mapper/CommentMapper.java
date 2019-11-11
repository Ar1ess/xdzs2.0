package com.softlab.provider.mapper;

import com.softlab.common.model.Comment;
import com.softlab.common.model.vo.CommentVo;
import org.apache.ibatis.annotations.Delete;
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
     * 根据文章查找所有评论
     * @param id
     * @return List<CommentVo>
     */
    List<CommentVo> selectCommentByArticleId(Integer id);


    /**
     * 添加评论
     * @param comment
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int insertComment(Comment comment);


    /**
     * 删除评论
     * @param id
     * @return 0 -- 删除失败 1 --删除成功
     */
    @Delete("delete from comment where comment_id = #{arg0}")
    int deleteComment(Integer id);



}
