package com.softlab.provider.service.impl;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.vo.PaceVo;
import com.softlab.common.service.RedisService;
import com.softlab.provider.mapper.PaceMapper;
import com.softlab.provider.mapper.RedisMapper;
import com.softlab.provider.mapper.UserMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.softlab.common.GlobalConst.*;
import java.util.List;
import java.util.Map;

/**
 * @author LiXiwen
 * @date 2019/11/8 20:17
 */
@Service
public class RedisServiceImpl implements RedisService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisMapper redisMapper;

    @Autowired
    public RedisServiceImpl(RedisMapper redisMapper) {
        this.redisMapper = redisMapper;
    }

    @Override
    public boolean setList(List<PaceVo> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = null;
        try {
            value = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        if (redisMapper.set(PACE_LIST, value)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PaceVo> getList() {
        Object obj = redisMapper.get(PACE_LIST);
        if (null == obj) {
            return null;
        }
        return JSON.parseArray((String) obj, PaceVo.class);
    }

    @Override
    public void delList() {
        redisMapper.del(PACE_LIST);
    }

    @Override
    public int getUserSize() {
        if (redisMapper.hasKey(USER_SIZE)) {
            logger.info("redis has this key : " + USER_SIZE);
            return (int) redisMapper.get(USER_SIZE);
        } else {
            logger.info("redis has not this key : " + USER_SIZE);
            return 0;
        }
    }

    @Override
    public int incrUserSize() {
        try {
            return (int) redisMapper.incr(USER_SIZE, 1);
        } catch (AppException e) {
            logger.error("setUsersSize error,redis not has this key!");
            return 0;
        }
    }


    @Override
    public boolean setPaceVoBean(String openId, Map<String,Object> map) {
        try {
            return redisMapper.set((PACE_PREFIX + openId), map);
        } catch (AppException e){
            logger.error("setPaceBean exists error : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object getPaceVoBean(String openId) {

        if (redisMapper.hasKey((PACE_PREFIX + openId))) {
            return redisMapper.get((PACE_PREFIX + openId));
        } else {
            logger.warn("getPaceBean exists error : ");
            return null;
        }
    }

    @Override
    public int zAdd(String openId, Integer userPace) {
        if (redisMapper.zAdd(PACE_SORT, openId, userPace)) {
            logger.info("1");
            return 1;
        } else {
            logger.info("0");
            return 0;
        }
        //return redisMapper.zAdd(PACE_SORT, openId, userPace);
    }

    @Override
    public Object zRank(String openId) {
        Long l = redisMapper.zRank(PACE_SORT, openId);
        if (l == null) {
            return 0;
        }
        long ll = l;
        return (int) ll;
    }

    @Override
    public Object zCard() {
        Long l = redisMapper.zCard();
        if (l == null) {
            return 0;
        }
        long ll = l;
        return (int) ll;
    }

    @Override
    public Object zScore(String value) {
        Double d = redisMapper.zScore(value);
        if (null == d) {
            return null;
        }
        double dd = d;
        return d;
    }


}
