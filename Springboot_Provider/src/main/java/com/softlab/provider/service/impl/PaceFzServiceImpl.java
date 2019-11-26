package com.softlab.provider.service.impl;

import com.softlab.common.GlobalConst;
import com.softlab.common.model.Pace;
import com.softlab.common.model.vo.PaceVo;
import com.softlab.common.service.PaceFzService;
import com.softlab.provider.mapper.PaceMapper;
import com.softlab.provider.mapper.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiXiwen
 * @date 2019/11/13 22:29
 */
@Service
public class PaceFzServiceImpl implements PaceFzService {

    @Autowired
    private PaceMapper paceMapper;
    @Autowired
    private RedisMapper redisMapper;


    @Override
    public boolean addPace(Pace pace) {
        return paceMapper.addPace(pace);
    }

    @Override
    public boolean updatePace(Pace pace) {
        return paceMapper.updatePace(pace);
    }

    @Override
    public List<PaceVo> selectPaceVo(Pace pace) {
        return paceMapper.selectPaceVo(pace);
    }

    @Override
    public List<PaceVo> selectPaceDescRank() {
        return paceMapper.selectPaceDescRank();
    }

    @Override
    public void init1() {
        List<PaceVo> paceVos = paceMapper.init1();
        paceVos.parallelStream().forEach(paceVo
                -> redisMapper.zAdd(GlobalConst.PACE_SORT, paceVo.getOpenId(), paceVo.getPace()));
        System.out.println("zcard : " + redisMapper.zCard());
    }




}
