package com.softlab.common.service;

import com.softlab.common.model.vo.PaceVo;

import java.util.List;
import java.util.Map;

/**
 * @author LiXiwen
 * @date 2019/11/8 20:14
 */
public interface RedisService {
    /**
     * 查找user表中数据个数
     * @return
     */
    int getUserSize();

    /**
     * 设置用户数量
     * @return
     */
    int incrUserSize();


    /**
     * 设置paceVoList到redis
     * @param list
     * @return
     */
    boolean setList(List<PaceVo> list);

    /**
     * 从redis里面查询paceVoList
     * @return
     */
    List<PaceVo> getList();

    /**
     * 删除该list
     */
    void delList();

    /**
     * 设置paceBean
     * @param openId
     * @param map
     * @return
     */
    boolean setPaceVoBean(String openId, Map<String,Object> map);

    /**
     * 获取paceBean
     * @param openId
     * @return
     */
    Object getPaceVoBean(String openId);

    /**
     * 往zset里边添加数据
     * @param openId
     * @param userPace
     * @return
     */
    int zAdd(String openId, Integer userPace);

    /**
     * 获取单个排名
     * @param openId
     * @return
     */
    Object zRank(String openId);

    /**
     * 计算集合数量
     * @return
     */
    Object zCard();

    /**
     * 获取value的分数值
     * @param value
     * @return
     */
    Object zScore(String value);
}
