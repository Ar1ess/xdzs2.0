package com.softlab.common.service;

import com.softlab.common.RestData;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.Pace;
import com.softlab.common.model.Run;
import com.softlab.common.model.vo.DecryptVo;
import com.softlab.common.model.vo.PaceVo;

import java.util.List;

/**
 * @author LiXiwen
 * @date 2019/11/11 7:59
 */
public interface PaceService {
    /**
     * 解密数据
     * @param decryptVo
     * @return
     * @throws AppException
     */
    RestData decrypt(DecryptVo decryptVo) throws AppException;

    /**
     * 查询个人和团体步数信息
     * @param openId
     * @return
     */
    RestData selectPace(String openId);

    /**
     * 添加跑步数据
     * @param run
     * @return
     */
    RestData addRunData(Run run);

    /**
     * 查询所有人的跑步数据
     * @return
     */
    RestData selectRunData();


}
