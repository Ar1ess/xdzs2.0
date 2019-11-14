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
     * 根据条件获取paceVo
     * @param pace
     * @return
     */
    List<PaceVo> selectPaceVo(Pace pace);

    List<PaceVo> selectPaceDescRank();

    void init1();
}
