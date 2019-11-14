package com.softlab.common.service;

import com.softlab.common.model.Course;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
public interface CourseService {

    /**
     * 查找前十排名的课程
     * @return List<Course>
     */
    List<Map<String, Object>> selectByOrder();


    double updateZan(String course);


    boolean selectRedisIsExist(String openId);
}
