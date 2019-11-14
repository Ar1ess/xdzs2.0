package com.softlab.provider.mapper;

import com.softlab.common.model.FeedBack;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */

@Mapper
@Repository
public interface FeedBackMapper {

    /**
     * 添加反馈信息
     * @param feedBack
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int insertFeedBack(FeedBack feedBack);


    /**
     * 查询所有反馈信息
     * @return List<FeedBack>
     */
    List<FeedBack> selectAllFeedBack();


    /**
     * 删除反馈信息
     * @param id
     * @return 0 -- 删除失败 1 -- 删除成功
     */
    int deleteFeedBack(Integer id);
}
