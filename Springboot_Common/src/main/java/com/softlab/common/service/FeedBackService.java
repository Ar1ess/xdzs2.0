package com.softlab.common.service;

import com.softlab.common.model.FeedBack;

import java.util.List;
import java.util.Map;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
public interface FeedBackService {


    /**
     * 添加反馈数据
     * @param feedBack
     * @return 0 -- 添加失败 1 -- 添加成功
     */
    int addFeedBack(FeedBack feedBack);


    /**
     * 查询所有反馈信息
     * @return
     */
    List<Map<String, Object>> selectAllFeedBack();


    /**
     * 删除反馈信息
     * @param id
     * @return 0 -- 删除失败 1 -- 删除成功
     */
    int deleteFeedBack(Integer id);

}
