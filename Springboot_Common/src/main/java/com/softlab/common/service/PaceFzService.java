package com.softlab.common.service;

import com.softlab.common.model.Pace;
import com.softlab.common.model.vo.PaceVo;

import java.util.List;

/**
 * 辅助paceServiceImpl实现事务操作
 * @author LiXiwen
 * @date 2019/11/13 22:29
 */
public interface PaceFzService {
    /**
     * 添加步数bean
     * @param pace
     * @return
     */
    boolean addPace(Pace pace);

    /**
     * 更新步数bean
     * @param pace
     * @return
     */
    boolean updatePace(Pace pace);

    /**
     * 更新部分用户信息
     * @param list
     * @return
     */
    boolean updatePartPace(List<PaceVo> list);

    /**
     * 根据条件获取paceVo
     * @param pace
     * @return
     */
    List<PaceVo> selectPaceVo(Pace pace);

    /**
     * 查询所有用户
     * @return
     */
    List<PaceVo> selectPaceByRank();

    /**
     * 初始化redis，mysql
     */
    void init1();

    /**
     * 查询不包含当前用户的所有用户
     * @param pace
     * @return
     */
    List<PaceVo> selectPartPaceByRank(Pace pace);
}
