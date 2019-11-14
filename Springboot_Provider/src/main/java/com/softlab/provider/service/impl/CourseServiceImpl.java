package com.softlab.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.softlab.common.ErrorMessage;
import com.softlab.common.GlobalConst;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Course;
import com.softlab.common.service.CourseService;
import com.softlab.provider.mapper.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author : Ar1es
 * @date : 2019/11/11
 * @since : Java 8
 */
@Service
//@org.springframework.stereotype.Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private RedisMapper redisMapper;

    @Override
    public List<Map<String, Object>> selectByOrder() {

        Set s = redisMapper.sortSetRange(GlobalConst.COURSE_RATE, 0 ,9);
        List<Map<String, Object>> al = new ArrayList<>();
        int i = 0;
        for (Object o : s) {
            Map<String, Object> map = new HashMap<>();
            map.put("course", o);
            map.put("rank", i++);
            double zan = redisMapper.getScore(GlobalConst.COURSE_RATE, o);
            map.put("zan", zan);
            al.add(map);
        }
        return al;
    }

    @Override
    public double updateZan(String course) {
        double flag = redisMapper.sortSetZincrby(GlobalConst.COURSE_RATE, course, 1);

        if (-1 == flag) {
            throw new AppException(ErrorMessage.SYSTEM_ERROR);
        }

        return flag;
    }

    @Override
    public boolean selectRedisIsExist(String openId) {
        boolean flag = redisMapper.hasKey(GlobalConst.COURSE_RATE + openId);
        if (flag) {
            return true;
        } else {
            redisMapper.set(GlobalConst.COURSE_RATE, 60 * 60 * 24000);
            return false;
        }
    }
}
