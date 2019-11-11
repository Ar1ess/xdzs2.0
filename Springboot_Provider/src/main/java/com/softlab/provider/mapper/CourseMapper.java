package com.softlab.provider.mapper;

import com.softlab.common.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */

@Mapper
@Repository
public interface CourseMapper {

    /**
     * 查询是否有某个课程
     */
    @Select("SELECT c_id as cId, course, zan FROM course where course=#{course}")
    Course selectByCourse(String course);

    @Select("SELECT c_id as cId, course, zan FROM course")
    List<Course> selectByOrder();

    /**
     * 实现让点赞数增加
     */
    @Update("update course set zan = zan + 1 where course=#{course}")
    int updateCourse(String course);
}
